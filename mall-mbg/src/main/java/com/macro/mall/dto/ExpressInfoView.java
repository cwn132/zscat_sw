package com.macro.mall.dto;


import com.macro.mall.model.ExpressInfo;

public class ExpressInfoView extends ExpressInfo {

	private static final long serialVersionUID = 1344453505420083793L;
	
	private String expressCorpName;

	public String getExpressCorpName() {
		return expressCorpName;
	}

	public void setExpressCorpName(String expressCorpName) {
		this.expressCorpName = expressCorpName;
	}
	
}
