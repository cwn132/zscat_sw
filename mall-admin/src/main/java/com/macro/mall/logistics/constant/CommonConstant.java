package com.macro.mall.logistics.constant;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 静态常量公共接口
 * 
 * @author
 */
public class CommonConstant {
	
	public static final ThreadLocal<HttpServletRequest> requestTL = new ThreadLocal<HttpServletRequest>(); // 保存request的threadlocal
	public static final ThreadLocal<HttpSession> sessionTL = new ThreadLocal<HttpSession>(); // 保存session的threadlocal
	
	public static final int THREAD_POOL_CORE_SIZE = 10;// 线程池最少线程数
	public static final int THREAD_POOL_MAX_SIZE = 50;// 最大线程数
	public static final int THREAD_MAX_THREAD_WAIT = 1000;// 最大线程等待数
	public static final int THREAD_POOL_WAIT_SECONDS = 5 * 60;// 最长等待时间
	
	public static final int MAX_SQL_IN_SET_SIZE = 900; // 最大的in语句的size
	
	

	public static String JINGDONG_PRICE_PIC_URL = "http://jprice.360buyimg.com/getSkuPriceImgService.action?origin=1&webSite=1&type=6&skuId=";
	public static String JINGDONG_PRICE_URL = "http://p.3.cn/prices/mgets?skuIds=J_";
	public static String YIHAODIAN_PRICE_URL = "http://busystock.i.yihaodian.com/restful/detail?mcsite=1&productId=#{productId}&merchantId=#{merchantId}";
	
	
	public static final int SMS_TYPE_BUSINESS = 0;// 短信类型-业务短信
	public static final int SMS_TYPE_PROMOTION = 1;// 短信类型-推广短信

	// 修改url by wangjn 20130218
	public static String XMLDTDS = "http://apache.org/xml/features/nonvalidating/load-external-dtd";

	/** 更新商品排序值的url */
	public static final String REBUILD_GOODS_ORDER_VALUE_URL = "rebuild.html?target=15&id=";
	
	public static final String DEVILFISH_PV_URL = "http://devilfish.netease.com/external_url_data.m?sitename=baojian&date={date}&min_pv_num=1";
	
	public static final int YES_CODE = 1;// 是操作
	public static final int NO_CODE = 0;// 否操作
}
