package com.macro.mall.model;

import java.io.Serializable;



/**
 * 
 * 
 * @author zscat
 * @email 951449465@qq.com
 * @date 2019-03-28 16:50:35
 */
public class Area implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//
	private Long pid;
	//层级
	private Integer deep;
	//名称
	private String name;
	//拼音前缀
	private String pinyinPrefix;
	//拼音
	private String pinyin;
	//备注名
	private String extId;
	//备注名
	private String extName;

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
	public void setPid(Long pid) {
		this.pid = pid;
	}
	/**
	 * 获取：
	 */
	public Long getPid() {
		return pid;
	}
	/**
	 * 设置：层级
	 */
	public void setDeep(Integer deep) {
		this.deep = deep;
	}
	/**
	 * 获取：层级
	 */
	public Integer getDeep() {
		return deep;
	}
	/**
	 * 设置：名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：拼音前缀
	 */
	public void setPinyinPrefix(String pinyinPrefix) {
		this.pinyinPrefix = pinyinPrefix;
	}
	/**
	 * 获取：拼音前缀
	 */
	public String getPinyinPrefix() {
		return pinyinPrefix;
	}
	/**
	 * 设置：拼音
	 */
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	/**
	 * 获取：拼音
	 */
	public String getPinyin() {
		return pinyin;
	}
	/**
	 * 设置：备注名
	 */
	public void setExtId(String extId) {
		this.extId = extId;
	}
	/**
	 * 获取：备注名
	 */
	public String getExtId() {
		return extId;
	}
	/**
	 * 设置：备注名
	 */
	public void setExtName(String extName) {
		this.extName = extName;
	}
	/**
	 * 获取：备注名
	 */
	public String getExtName() {
		return extName;
	}
}
