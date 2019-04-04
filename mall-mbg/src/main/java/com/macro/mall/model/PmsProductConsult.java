package com.macro.mall.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class PmsProductConsult implements Serializable {
    /**
     * 咨询编号
     */
    private Long id;

    /**
     * 商品编号
     */
    private Long goodsId;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 咨询发布者会员编号(0：游客)
     */
    private Long memberId;

    /**
     * 会员名称
     */
    private String memberName;

    /**
     * 店铺编号
     */
    private Long storeId;

    /**
     * 咨询发布者邮箱
     */
    private String email;

    /**
     * 咨询内容
     */
    private String consultContent;

    /**
     * 咨询添加时间
     */
    private Date consultAddtime;

    /**
     * 咨询回复内容
     */
    private String consultReply;

    /**
     * 咨询回复时间
     */
    private Date consultReplyTime;

    /**
     * 0表示不匿名 1表示匿名
     */
    private Boolean isanonymous;

    private Boolean isDel;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConsultContent() {
        return consultContent;
    }

    public void setConsultContent(String consultContent) {
        this.consultContent = consultContent;
    }

    public Date getConsultAddtime() {
        return consultAddtime;
    }

    public void setConsultAddtime(Date consultAddtime) {
        this.consultAddtime = consultAddtime;
    }

    public String getConsultReply() {
        return consultReply;
    }

    public void setConsultReply(String consultReply) {
        this.consultReply = consultReply;
    }

    public Date getConsultReplyTime() {
        return consultReplyTime;
    }

    public void setConsultReplyTime(Date consultReplyTime) {
        this.consultReplyTime = consultReplyTime;
    }

    public Boolean getIsanonymous() {
        return isanonymous;
    }

    public void setIsanonymous(Boolean isanonymous) {
        this.isanonymous = isanonymous;
    }

    public Boolean getDel() {
        return isDel;
    }

    public void setDel(Boolean del) {
        isDel = del;
    }
}