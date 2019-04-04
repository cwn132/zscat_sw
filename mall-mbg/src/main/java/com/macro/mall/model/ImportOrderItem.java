
/************************************************************************
日  期：		2016年6月3日 下午2:33:00
作  者:		赵高发
版  本：     	v1.0
描  述:	   	批量导入订单
历  史：      
************************************************************************/

package com.macro.mall.model;
public class ImportOrderItem {
	private Long expressId;	// 物流单id
	private Long orderId;	//订单号
	private String	expressNo;	// 运单号
	private String  expressCorpId; // 物流公司id
	public ImportOrderItem() {
	}

	public ImportOrderItem(Long orderId, String expressNo, String expressCorpId) {
		this.orderId = orderId;
		this.expressNo = expressNo;
		this.expressCorpId = expressCorpId;
	}

	public Long getExpressId() {
		return expressId;
	}

	public void setExpressId(Long expressId) {
		this.expressId = expressId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getExpressNo() {
		return expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	public String getExpressCorpId() {
		return expressCorpId;
	}

	public void setExpressCorpId(String expressCorpId) {
		this.expressCorpId = expressCorpId;
	}

}

