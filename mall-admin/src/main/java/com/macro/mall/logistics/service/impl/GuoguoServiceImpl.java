package com.macro.mall.logistics.service.impl;


import com.macro.mall.logistics.constant.LogisticsConstant;
import com.macro.mall.logistics.constant.LogisticsUtil;
import com.macro.mall.logistics.constant.WayBillConstant;
import com.macro.mall.logistics.model.WaybillGrabResult;
import com.macro.mall.logistics.service.LogisticsGrabInterface;
import com.macro.mall.util.JsonUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("guoguoServiceImpl")
public class GuoguoServiceImpl implements LogisticsGrabInterface {
    private static final String DATA_TEMPLATE = "{\"mailNo\":\"%1$s\",\"cpCode\":\"%2$s\"}";
    private static final String API = "http://api.wap.guoguo-app.com/h5/mtop.cnwireless.cnlogisticdetailservice.wapquerylogisticpackagebymailno/1.0/";

    private Logger log = LoggerFactory.getLogger(getClass());

    private final BasicCookieStore cookieStore = new BasicCookieStore();
    private final RequestConfig requestConfig = RequestConfig.custom()
            .setConnectTimeout(5000).setConnectionRequestTimeout(1000)  
            .setSocketTimeout(10000).build();  
    private final HttpClient httpClient = HttpClients.custom()
    		.setDefaultRequestConfig(requestConfig)
            .setDefaultCookieStore(cookieStore)
            .build();

    @Override
    public WaybillGrabResult queryWayBillDetail(String logisticsCompanyCode, String waybillNum) throws Exception {
        log.info("queryWayBillDetail() called with: logisticsCompanyCode = [" + logisticsCompanyCode + "], waybillNum = [" + waybillNum + "]");

        try {
            Thread.sleep(new Random().nextInt(10) * 1000L + 5000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        WaybillGrabResult result = new WaybillGrabResult();

        String ggLogisticsCompanyCode = mappingCompanyCode(logisticsCompanyCode);
        if (StringUtils.isEmpty(logisticsCompanyCode)) {
            result.setGrabStatus(LogisticsConstant.GRAB_RESULT_SUCCESS);
            log.error("express corp not support:" + ggLogisticsCompanyCode);
            return result;
        }

        String token = getToken();
        if (StringUtils.isEmpty(token)) {
            //请求一次获取 Cookie ，Cookie 内包含下面需要的 token
            HttpGet httpGet = new HttpGet("http://www.guoguo-app.com/");
            httpGet.setConfig(requestConfig);
            HttpResponse res = httpClient.execute(httpGet);
            res.getEntity().getContent().close(); // close connection
            token = getToken();
        }

        List<NameValuePair> params = new ArrayList<>();

        String data = String.format(DATA_TEMPLATE, waybillNum, ggLogisticsCompanyCode);
        String appKey = "12574478";
        String t = String.valueOf(System.currentTimeMillis());
        //签名
        String sign = sign(token, t, appKey, data);

        params.add(new BasicNameValuePair("data", data));
        params.add(new BasicNameValuePair("api", "mtop.cnwireless.CNLogisticDetailService.wapqueryLogisticPackageByMailNo"));
        params.add(new BasicNameValuePair("appKey", appKey));
        params.add(new BasicNameValuePair("t", t));
        params.add(new BasicNameValuePair("type", "originaljson"));
        params.add(new BasicNameValuePair("v", "1.0"));
        params.add(new BasicNameValuePair("sign", sign));


        String urlParams = URLEncodedUtils.format(params, "utf-8");
        HttpGet httpGet = new HttpGet(API + "?" + urlParams);
        httpGet.setConfig(requestConfig);

        //这两个 Header 必须添加
        httpGet.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.98 Safari/537.36");
        httpGet.addHeader("Referer", "http://www.guoguo-app.com/");

        HttpResponse response = httpClient.execute(httpGet);

        String responseText = EntityUtils.toString(response.getEntity());

        log.info("接口请求结果：" + responseText);

        responseText = processResult(responseText);

        if (StringUtils.isBlank(responseText) || StringUtils.contains(responseText, "非法")) {

            log.info("菜鸟裹裹抓取失败");

            result.setGrabStatus(LogisticsConstant.GRAB_RESULT_FAIL);
            result.setLogisticsStatus(WayBillConstant.EXPRESS_STATE_SHIPED);
        } else {
            result.setData(responseText);
            result.setGrabStatus(LogisticsConstant.GRAB_RESULT_SUCCESS);

            log.info("菜鸟裹裹抓取成功");

            if (LogisticsUtil.isSigned(responseText)) {
            	log.info("运单查询返回包含已签收字段，认为已签收！wayBill=" + responseText);
                result.setLogisticsStatus(WayBillConstant.EXPRESS_STATE_SINGED);
            }
        }
        return result;
    }

    private String mappingCompanyCode(String code) {
        if (StringUtils.isEmpty(code)) return null;

        switch (code) {
            case "shunfeng":
                return "SF";
            case "yunda":
                return "YUNDA";
            case "annengwuliu":
                return "ANE56";
            case "ems":
                return "EMS";
            case "shentong":
                return "STO";
            case "tiantian":
                return "TTKDEX";
            case "yuantong":
                return "YTO";
            case "zhongtong":
                return "ZTO";
            case "youshuwuliu":
                return "UC";
            case "huitong":
            case "huitongkuaidi":
                return "HTKY";
            case "zhaijisong":
                return "ZJS";
            case "kuaijiesudi":
            	return "FAST";
            case "wanxiangwuliu":
            	return "EWINSHINE";
            default:
                return null;
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	private String processResult(String responseText) {
        try {

            List<Map<String, String>> ret = new ArrayList<>();

            Map<String, Object> map = JsonUtil.readJsonToMap(responseText);
            List<Map> transitList = (List<Map>) ((Map) map.get("data")).get("transitList");

            if (transitList == null) return null;

            for (Map item : transitList) {
                Map<String, String> m = new HashMap<>();
                m.put("time", (String) item.get("time"));
                m.put("context", (String) item.get("message"));
                ret.add(m);
            }
            return JsonUtil.objectToJson(ret);
        } catch (Exception e) {
            log.error("processResult", e);
            return null;
        }
    }

    /**
     * 从 Cookie 中取出 token 同时判断 token 有效期
     * _m_h5_tk=7ba21ca4893f9c9eaff91b8568f1a2fc_1495522735729
     *
     * @return token
     */
    private String getToken() {
        String token = null;
        long expires = 0;

        for (Cookie cookie : cookieStore.getCookies()) {
            if ("_m_h5_tk".equals(cookie.getName())) {
                String[] tmp = cookie.getValue().split("_");
                token = tmp[0];
                expires = Long.parseLong(tmp[1]);
            }
        }

        if (StringUtils.isNotBlank(token) && expires > System.currentTimeMillis()) {
            return token;
        } else {
            return null;
        }
    }

    /**
     * 参数签名，将参数 token,t,appkey,data 使用&符号链接，进行一次 MD5,得出签名
     */
    private String sign(String token, String t, String appKey, String data) {
        String toSign = token + "&" + t + "&" + appKey + "&" + data;
        return DigestUtils.md5Hex(toSign);
    }

}
