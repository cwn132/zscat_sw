package com.macro.mall.sms.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 
 * 
 * @author zscat
 * @email 951449465@qq.com
 * @date 2019-03-29 14:29:09
 */
public class SmsGroup implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//
	private Long goodsId;
	//商品
	private String goodsName;
	//商品价格
	private BigDecimal originPrice;
	//拼团价格
	private BigDecimal groupPrice;
	//开始时间
	private Date startTime;
	//结束时间
	private Date endTime;
	//拼团小时
	private Integer hours;
	//成团人数
	private Integer peoples;
	//状态
	private Integer status;
	//创建时间
	private Date createTime;
	//拼团总人数
	private Integer maxPeople;
	//团购次数
	private Integer limitGoods;

	/**
	 * 设置：
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：
	 */
	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}
	/**
	 * 获取：
	 */
	public Long getGoodsId() {
		return goodsId;
	}
	/**
	 * 设置：商品
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	/**
	 * 获取：商品
	 */
	public String getGoodsName() {
		return goodsName;
	}
	/**
	 * 设置：商品价格
	 */
	public void setOriginPrice(BigDecimal originPrice) {
		this.originPrice = originPrice;
	}
	/**
	 * 获取：商品价格
	 */
	public BigDecimal getOriginPrice() {
		return originPrice;
	}
	/**
	 * 设置：拼团价格
	 */
	public void setGroupPrice(BigDecimal groupPrice) {
		this.groupPrice = groupPrice;
	}
	/**
	 * 获取：拼团价格
	 */
	public BigDecimal getGroupPrice() {
		return groupPrice;
	}
	/**
	 * 设置：开始时间
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * 获取：开始时间
	 */
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * 设置：结束时间
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * 获取：结束时间
	 */
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * 设置：拼团小时
	 */
	public void setHours(Integer hours) {
		this.hours = hours;
	}
	/**
	 * 获取：拼团小时
	 */
	public Integer getHours() {
		return hours;
	}
	/**
	 * 设置：成团人数
	 */
	public void setPeoples(Integer peoples) {
		this.peoples = peoples;
	}
	/**
	 * 获取：成团人数
	 */
	public Integer getPeoples() {
		return peoples;
	}
	/**
	 * 设置：状态
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：状态
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：拼团总人数
	 */
	public void setMaxPeople(Integer maxPeople) {
		this.maxPeople = maxPeople;
	}
	/**
	 * 获取：拼团总人数
	 */
	public Integer getMaxPeople() {
		return maxPeople;
	}
	/**
	 * 设置：团购次数
	 */
	public void setLimitGoods(Integer limitGoods) {
		this.limitGoods = limitGoods;
	}
	/**
	 * 获取：团购次数
	 */
	public Integer getLimitGoods() {
		return limitGoods;
	}
}
