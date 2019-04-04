package com.macro.mall.model;

import java.io.Serializable;
import java.util.Date;

public class ExpressInfo implements Serializable {

	private static final long serialVersionUID = 5193464731035832374L;

	private long id;
//	private long orderId;
	private String expressCorpId;
	private String expressNo;
	private int expressStatus; // 运单状态0.初始化 1.已发货 2 已签收
	private String expressDetail;
	private Date updateTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getExpressDetail() {
		return expressDetail;
	}

	public void setExpressDetail(String expressDetail) {
		this.expressDetail = expressDetail;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
