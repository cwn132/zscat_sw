package com.macro.mall.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ExpressInfoModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7995369736904704681L;
	
	private long id;
	private long orderId;
	private String expressCorpId;
	private String expressNo;
	private int expressStatus;
	List<WayBillItem> expressDetail = new ArrayList<WayBillItem>();
	private String updateTime;
	private String expressCorpName;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public String getExpressCorpId() {
		return expressCorpId;
	}
	public void setExpressCorpId(String expressCorpId) {
		this.expressCorpId = expressCorpId;
	}
	public String getExpressNo() {
		return expressNo;
	}
	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}
	public int getExpressStatus() {
		return expressStatus;
	}
	public void setExpressStatus(int expressStatus) {
		this.expressStatus = expressStatus;
	}
	
	public List<WayBillItem> getExpressDetail() {
		return expressDetail;
	}
	public void setExpressDetail(List<WayBillItem> expressDetail) {
		this.expressDetail = expressDetail;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getExpressCorpName() {
		return expressCorpName;
	}
	public void setExpressCorpName(String expressCorpName) {
		this.expressCorpName = expressCorpName;
	}
	
}
