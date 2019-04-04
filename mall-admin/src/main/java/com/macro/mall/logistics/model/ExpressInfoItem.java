package com.macro.mall.logistics.model;

public class ExpressInfoItem {
	private String time;
	private String location;
	private String context;
	private String ftime;
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getFtime() {
		return ftime;
	}
	public void setFtime(String ftime) {
		this.ftime = ftime;
	}
	@Override
	public String toString() {
		return "WayBillItem [time=" + time + ", location=" + location + ", context=" + context + ", ftime=" + ftime
				+ "]";
	}
	
}
