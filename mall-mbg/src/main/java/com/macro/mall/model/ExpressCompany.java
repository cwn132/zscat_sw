package com.macro.mall.model;

public class ExpressCompany {
    private Integer id;

    private String expressCorpId;

    private String expressCorpName;

    private Byte status;

    private String logoUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExpressCorpId() {
        return expressCorpId;
    }

    public void setExpressCorpId(String expressCorpId) {
        this.expressCorpId = expressCorpId == null ? null : expressCorpId.trim();
    }

    public String getExpressCorpName() {
        return expressCorpName;
    }

    public void setExpressCorpName(String expressCorpName) {
        this.expressCorpName = expressCorpName == null ? null : expressCorpName.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl == null ? null : logoUrl.trim();
    }
}