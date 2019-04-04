package com.macro.mall.portal.service;

import com.macro.mall.portal.config.WxAppletProperties;
import com.macro.mall.portal.util.JedisLock;
import com.macro.mall.portal.util.JsonUtils;
import com.macro.mall.portal.util.MyX509TrustManager;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

/**
 * 对接微信接口服务
 * Created by fei on 2017/4/24.
 */
@Service
public class WechatApiService {
    private static final String WECHAT_API = "https://api.weixin.qq.com/cgi-bin";
    private static final String WECHAT_API_TOKEN = WECHAT_API + "/token";
    private static final String WECHAT_API_TICKET = WECHAT_API + "/ticket/getticket?type=jsapi&access_token=";
    public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    // 存放：1.token，2：获取token的时间,3.过期时间
    public final static Map<String,Object> accessTokenMap = new HashMap<String,Object>();
    @Resource
    private WxAppletProperties wxAppletProperties;

    private Jedis jedis;
    @Resource
    private JedisPool jedisPool;
    @Resource
    private RedisService redisService;
    private final HttpClient httpclient;

    public WechatApiService() {
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(5000)
                .setSocketTimeout(20000)
                .setConnectionRequestTimeout(3000)
                .build();
        httpclient = HttpClients.custom().setDefaultRequestConfig(config).build();
    }

    /**
     * 获取默认公众号的 access_token
     *
     * @return access_token
     * @throws Exception
     */
    public String getAccessToken() throws Exception {
        return getAccessToken(wxAppletProperties.getAppId(), wxAppletProperties.getSecret());
    }



    /**
     * 获取  access_token
     * https://mp.weixin.qq.com/wiki?action=doc&id=mp1421140183
     *
     * @return access_token
     * @throws Exception
     */
    public String getAccessToken(String appid, String appSecret) throws Exception {

        String key = "access_token:" + appid;
        jedis=jedisPool.getResource();
        if (jedis.ttl(key) > 30) {
        	try {
        		return jedis.get(key);
        	} finally {
        		jedis.close();
        	}
            
        }

        //https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
        String lockKey = "lock_" + key;
        JedisLock lock = new JedisLock(jedis, lockKey);
        boolean acquired = lock.acquire();
        if (!acquired) {
        	jedis.close();
            throw new Exception("acquired lock: " + lockKey + " timeout");
        }
        try {
            if (jedis.ttl(key) > 30) {
            	try {
            		return jedis.get(key);
            	} finally {
            		jedis.close();
            	}
            }

            HttpGet get = new HttpGet(WECHAT_API_TOKEN + "?grant_type=client_credential&appid=" + appid + "&secret=" + appSecret);
            HttpResponse response = httpclient.execute(get);
            String text = EntityUtils.toString(response.getEntity());
            Map<String, Object> resultMap = JsonUtils.readJsonToMap(text);
            String accessToken = (String) resultMap.get("access_token");
            int expiresIn = (int) resultMap.get("expires_in");

            jedis.set(key, accessToken);
            jedis.expire(key, expiresIn);
            return accessToken;
        } finally {
            lock.release();
            jedis.close();
        }
    }




    /**
     * 发起https请求并获取结果
     *
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr 提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject handleRequest(String requestUrl,String requestMethod,String outputStr) {
        JSONObject jsonObject = null;

        try {
            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            SSLContext ctx = SSLContext.getInstance("SSL", "SunJSSE");
            TrustManager[] tm = {new MyX509TrustManager()};
            ctx.init(null, tm, new SecureRandom());
            SSLSocketFactory sf = ctx.getSocketFactory();
            conn.setSSLSocketFactory(sf);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod(requestMethod);
            conn.setUseCaches(false);

            if ("GET".equalsIgnoreCase(requestMethod)) {
                conn.connect();
            }

            if (StringUtils.isNotEmpty(outputStr)) {
                OutputStream out = conn.getOutputStream();
                out.write(outputStr.getBytes("utf-8"));
                out.close();
            }

            InputStream in = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in,"utf-8"));
            StringBuffer buffer = new StringBuffer();
            String line = null;

            while ((line = br.readLine()) != null) {
                buffer.append(line);
            }

            in.close();
            conn.disconnect();

            jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    /**
     * 获取默认公众号 jsapi_ticket
     *
     * @return jsapi_ticket
     * @throws Exception
     */

    public String getJsTicket() throws Exception {

        return getJsTicket(wxAppletProperties.getAppId(), wxAppletProperties.getSecret());
    }



    /**
     * 获取 jsapi_ticket
     * https://mp.weixin.qq.com/wiki?action=doc&id=mp1421141115
     *
     * @param appid
     * @param appSecret
     * @return ticket
     * @throws Exception
     */
    public String getJsTicket(String appid, String appSecret) throws Exception {
        jedis=jedisPool.getResource();
        String key = "jsapi_ticket:" + appid;

        if (jedis.ttl(key) > 30) {
        	try {
        		return jedis.get(key);
        	} finally {
        		jedis.close();
        	}
        }

        String lockKey = "lock_" + key;
        JedisLock lock = new JedisLock(jedis, lockKey);
        boolean acquired = lock.acquire();
        if (!acquired) {
        	jedis.close();
            throw new Exception("acquired lock: " + lockKey + " timeout");
        }

        try {
            if (jedis.ttl(key) > 30) {
                return jedis.get(key);
            }

            HttpGet get = new HttpGet(WECHAT_API_TICKET + getAccessToken(appid, appSecret));
            HttpResponse response = httpclient.execute(get);
            String text = EntityUtils.toString(response.getEntity());
            Map<String, Object> resultMap = JsonUtils.readJsonToMap(text);
            String ticket = (String) resultMap.get("ticket");
            int expiresIn = (int) resultMap.get("expires_in");

            jedis.set(key, ticket);
            jedis.expire(key, expiresIn);
            return ticket;
        } finally {
            lock.release();
            jedis.close();
        }
    }
}
