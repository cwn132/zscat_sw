package com.macro.mall.portal.common;



public class CommonConstant {
	

//	public static final String DOMAIN = "http://localhost:8080";

	// 外部来源和内部来源的cookie名
	public static final String OUTER_SOURCE_COOKIE = "RJFT_OUTER_SOURCE";
	public static final String INNER_SOURCE_COOKIE = "RJFT_INNER_SOURCE";

	public static final String OUTER_SOURCE_COOKIE_CLUE = "RJFT_OUTER_SOURCE_CLUE";
	// 来源cookie过期时间
	public static final int OUTER_SOURCE_COOKIE_TIMEOUT = 24 * 60 * 60;
	
	public static final String WEIXIN_QRPAY_COOKIE = "_wxpay";
	
	// 用户信息在session中的key
	public static final String USER_SESSION_KEY = "USER_SESSION";
	//订单确认信息在session中的key
	public static final String ORDER_SESSION_KEY = "ORDER_SESSION";
	
	public static final String  FITTIME_CART_COOKIE = "FITTIME_CART_COOKIE";
	//心愿单cookie
	public static final String FITTIME_WISH_COOKIE = "FITTIME_WISH_COOKIE";
	
	//操作成功（用于controller返回code）
	public static final int OPERATE_SUCCESS = 200;
	//参数错误
	public static final int PARAM_ERROR_CODE = -1;
	//操作失败
	public static final int OPERATE_FAIL = -2;
	// 没有登录
	public static final int RET_NOT_LOGIN = -100;
	// 优惠券领取次已达到最大次数（包含当前这次）
	public static final int COUPON_OBTAIN_LAST_TIME = 201;
	
	public static final String AJAX_REQUEST_HEADER_KEY = "x-rjft-request";
	public static final String AJAX_REQUEST_HEADER_VALUE = "ajax";
	
	public static final String AJAX_REQUEST_TOKEN_KEY = "Authorization";
	
	//app原生页请求
	public static final String AJAX_NATIVE_HEADER_KEY = "x-rjft-native";
	public static final String AJAX_NATIVE_HEADER_VALUE = "native";
	
	public static final String CART_SOURCE_COOKIE = "cookie";
	public static final String CART_SOURCE_MODIFY = "modify";

	public static final Object NEED_ID_CARD_NO = "needIdCard";
	
	public static final int TITLE_LENGTH=11;
	
	public static final int VOTE_BANNED = 300;
	
	public static final int VOTE_LIMITED = 301;
	
	public static final String CUSTOM_COURSE_SALT = "FITCAMP_CUSTOMCOURSE_";
}
