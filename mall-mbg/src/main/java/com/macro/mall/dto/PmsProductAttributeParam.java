package com.macro.mall.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * 商品属性参数
 * https://gitee.com/zscat-platform/mall on 2018/4/26.
 */
public class PmsProductAttributeParam {
    @ApiModelProperty("属性分类ID")
    private Long productAttributeCategoryId;
    @ApiModelProperty("属性名称")
    private String name;
    @ApiModelProperty("属性选择类型：0->唯一；1->单选；2->多选")
    private Integer selectType;
    @ApiModelProperty("属性录入方式：0->手工录入；1->从列表中选取")
    private Integer inputType;
    @ApiModelProperty("可选值列表，以逗号隔开")
    private String inputList;

    private Integer sort;
    @ApiModelProperty("分类筛选样式：0->普通；1->颜色")
    private Integer filterType;
    @ApiModelProperty("检索类型；0->不需要进行检索；1->关键字检索；2->范围检索")
    private Integer searchType;
    @ApiModelProperty("相同属性产品是否关联；0->不关联；1->关联")
    private Integer relatedStatus;
    @ApiModelProperty("是否支持手动新增；0->不支持；1->支持")
    private Integer handAddStatus;
    @ApiModelProperty("属性的类型；0->规格；1->参数")
    private Integer type;

    public Long getProductAttributeCategoryId() {
        return productAttributeCategoryId;
    }

    public void setProductAttributeCategoryId(Long productAttributeCategoryId) {
        this.productAttributeCategoryId = productAttributeCategoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSelectType() {
        return selectType;
    }

    public void setSelectType(Integer selectType) {
        this.selectType = selectType;
    }

    public Integer getInputType() {
        return inputType;
    }

    public void setInputType(Integer inputType) {
        this.inputType = inputType;
    }

    public String getInputList() {
        return inputList;
    }

    public void setInputList(String inputList) {
        this.inputList = inputList;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getFilterType() {
        return filterType;
    }

    public void setFilterType(Integer filterType) {
        this.filterType = filterType;
    }

    public Integer getSearchType() {
        return searchType;
    }

    public void setSearchType(Integer searchType) {
        this.searchType = searchType;
    }

    public Integer getRelatedStatus() {
        return relatedStatus;
    }

    public void setRelatedStatus(Integer relatedStatus) {
        this.relatedStatus = relatedStatus;
    }

    public Integer getHandAddStatus() {
        return handAddStatus;
    }

    public void setHandAddStatus(Integer handAddStatus) {
        this.handAddStatus = handAddStatus;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
