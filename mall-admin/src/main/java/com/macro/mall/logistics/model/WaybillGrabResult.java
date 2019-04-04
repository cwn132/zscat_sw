package com.macro.mall.logistics.model;

import java.io.Serializable;

public class WaybillGrabResult implements Serializable {

	private static final long serialVersionUID = -4360419855249541775L;

	private int grabStatus;
	private int logisticsStatus;
	private String data;

	public int getGrabStatus() {
		return grabStatus;
	}

	public void setGrabStatus(int grabStatus) {
		this.grabStatus = grabStatus;
	}

	public int getLogisticsStatus() {
		return logisticsStatus;
	}

	public void setLogisticsStatus(int logisticsStatus) {
		this.logisticsStatus = logisticsStatus;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
