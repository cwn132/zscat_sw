package com.macro.mall.sms.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author zscat
 * @email 951449465@qq.com
 * @date 2019-03-29 14:29:49
 */
@Data
public class SmsGroupMember implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//
	private Long groupId;

	private Long goodsId;
	//
	private Long memberId;
	//
	private Date createTime;

	private Long mainId;

	private String name;
	private int status = 1; // 1 正常 2未支付
	private int count = 0;




}
