package com.macro.mall.logistics.service.impl;


import com.macro.mall.exception.BusinessException;
import com.macro.mall.logistics.constant.KaiDi100Constant;
import com.macro.mall.logistics.constant.LogisticsConstant;
import com.macro.mall.logistics.constant.LogisticsUtil;
import com.macro.mall.logistics.constant.WayBillConstant;
import com.macro.mall.logistics.model.WaybillGrabResult;
import com.macro.mall.logistics.service.LogisticsGrabInterface;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Random;

@Service("kuaidi100WebService")
public class Kuaidi100WebService implements LogisticsGrabInterface {
	
	private Logger log = LoggerFactory.getLogger(getClass());

	private static int connectionTimeout = 5000;// 连接超时
	private static int soTimeout = 10000;
	
	private static String[] AGNET_LIST = {
			"Mozilla/5.0 (Windows NT 5.1) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.46 Safari/535.11",
			"Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11",
			"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/14.0.835.163 Safari/535.1",
			"Mozilla/5.0 (iPhone; CPU iPhone OS 9_3_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Mobile/13E238 MicroMessenger/6.3.16 NetType/WIFI Language/zh_CN",
			"Mozilla/5.0 (Linux; Android 4.4.4; Lenovo A6800 Build/S100) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/33.0.0.0 Mobile Safari/537.36",
			"Mozilla/5.0 (iPhone; CPU iPhone OS 9_3_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Mobile/13E238",
			"Mozilla/5.0 (Linux; Android 4.4.2; SM-N9006 Build/KOT49H) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/30.0.0.0 Mobile Safari/537.36",
			"Mozilla/5.0 (Linux; Android 4.4.4; vivo X5Max L Build/KTU84P) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/33.0.0.0 Mobile Safari/537.36",
			"Mozilla/5.0 (Linux; Android 4.4.2; HUAWEI MT7-TL00 Build/HuaweiMT7-TL00) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/30.0.0.0 Mobile Safari/537.36",
			"Mozilla/5.0 (iPhone; CPU iPhone OS 8_3 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) Mobile/12F70 Weibo (iPhone7,1__weibo__6.5.0__iphone__os8.3)",
			"Mozilla/5.0 (Linux; Android 4.4.4; MI PAD Build/KTU84P) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/33.0.0.0 Safari/537.36",
			"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36 TheWorld 6",
			"Mozilla/5.0 (iPhone; CPU iPhone OS 9_0_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Mobile/13A404",
			"Mozilla/5.0 (Linux; Android 4.4.4; CHM-CL00 Build/CHM-CL00) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/33.0.0.0 Mobile Safari/537.36",
			"Mozilla/5.0 (Linux; Android 6.0.1; SAMSUNG SM-G9350 Build/MMB29M) AppleWebKit/537.36 (KHTML, like Gecko) SamsungBrowser/4.0 Chrome/44.0.2403.133 Mobile Safari/537.36",
			"Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:45.0) Gecko/20100101 Firefox/45.0"};
	
	private static final String API = "http://www.kuaidi100.com/query?type={com}&postid={code}&id=1&valicode=&temp=";
	
	@Override
	public WaybillGrabResult queryWayBillDetail(String logisticsCompanyCode, String waybillNum) throws ClientProtocolException, IOException {
		Random r = new Random();
		try {
			Thread.sleep(r.nextInt(10) * 1000L + 5000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		WaybillGrabResult result = new WaybillGrabResult();
		String url = API.replace("{com}", logisticsCompanyCode).replace("{code}", waybillNum) + getRandomNum(17);
		try {
			HttpClient httpclient = HttpClients.createDefault();
			httpclient = HttpClients.custom().setUserAgent(getRandomAgent()).build();
			HttpGet httpGet = new HttpGet(url);
			RequestConfig requestConfig = RequestConfig.custom()
					.setConnectTimeout(connectionTimeout)
					.setConnectionRequestTimeout(1000)
					.setSocketTimeout(soTimeout)
					.build();
			httpGet.setConfig(requestConfig);
			httpGet.setHeader("Referer", "http://www.kuaidi100.com/");
			httpGet.addHeader("X-Forwarded-For", getRandomIp());
			log.info("[kuaidi100]request:" + url);
			HttpResponse httpResponse = httpclient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			String jsonStr = EntityUtils.toString(httpEntity);
			log.info("[kuaidi100]response:" + jsonStr);
			
			if (StringUtils.isBlank(jsonStr)) {
				result.setGrabStatus(LogisticsConstant.GRAB_RESULT_FAIL);
			} else {
				JSONObject json = JSONObject.fromObject(jsonStr);
				String status = json.getString("status");
				if (!KaiDi100Constant.STATUS_SUCCESS.equals(status)) {
					result.setGrabStatus(LogisticsConstant.GRAB_RESULT_FAIL);
				} else {
					result.setGrabStatus(LogisticsConstant.GRAB_RESULT_SUCCESS);
					Integer state = json.getInt("state");
					String dataJson = json.getString("data");
					
					if(KaiDi100Constant.STATE_RECEIVED == state){
						result.setLogisticsStatus(WayBillConstant.EXPRESS_STATE_SINGED);
					} else if (KaiDi100Constant.STATE_DELIVERED == state || KaiDi100Constant.STATE_ONTHEWAY == state) {
						boolean flag =  false;
						if(StringUtils.isNotBlank(dataJson)){
							//检查是否包含可以认为已签收的字段
							if (LogisticsUtil.isSigned(dataJson)) {
				            	log.info("运单查询返回包含已签收字段，认为已签收！wayBill=" + dataJson);
				                result.setLogisticsStatus(WayBillConstant.EXPRESS_STATE_SINGED);
				                flag = true;
				            }
						} else {
							throw new BusinessException("data is Empty!");
						}
						
						if( !flag){
							if( StringUtils.isNotBlank(dataJson) ){
								if (  dataJson.contains("签收") ) {
									log.info("运单查询返回未签收但详情中包含签收字段, 请人工确认！wayBill=" + dataJson);
									throw new BusinessException("运单查询返回未签收但详情中包含签收字段, 请人工确认！");
								}
							}
							result.setLogisticsStatus(WayBillConstant.EXPRESS_STATE_SHIPED);
						} 
					} else {
						log.error("expressInfo exception. expressCorp=" + logisticsCompanyCode + "expressNo=" + waybillNum + ", kuaidi100 state=" + state);
					}
					result.setData(dataJson);
				}
			}
			
		} catch (Exception e) {
			result.setGrabStatus(LogisticsConstant.GRAB_RESULT_EXCEPTION);
			log.error("grab logistics info from kuaidi100 error.", e);
		}
		return result;
	}

	private static String getRandomNum(int len) {
		String batChar = "0123456789123456789";

		Random rand = new Random();
		rand.setSeed(System.nanoTime());

		StringBuilder builder = new StringBuilder();
		builder.append("0.");
		for (int i = 0; i < len; i++) {
			builder.append(batChar.charAt(rand.nextInt(batChar.length())));
		}
		return builder.toString();
	}
	
	private static String getRandomAgent() {
		Random rand = new Random();
		rand.setSeed(System.nanoTime());
		String agent = AGNET_LIST[rand.nextInt(AGNET_LIST.length)];
		return agent;
	}

	public static String getRandomIp(){
		StringBuffer ip = new StringBuffer();
		Random r = new Random();
		for(int i = 0; i < 4; ++i){
			ip.append(r.nextInt(127)+r.nextInt(128));
			if(i != 3){
				ip.append(".");
			}
		}
		return ip.toString();
	}
}
