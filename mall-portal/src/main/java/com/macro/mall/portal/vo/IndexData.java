package com.macro.mall.portal.vo;


import com.macro.mall.model.CmsSubject;
import com.macro.mall.model.PmsProductAttributeCategory;
import com.macro.mall.model.SmsCoupon;
import com.macro.mall.model.SmsHomeAdvertise;

import java.util.List;

/**
 * Created by Administrator on 2017/10/18 0018.
 */
public class IndexData {
    private List<TArticleDO> module_list;
    private List<SmsHomeAdvertise> banner_list;
    private List<TArticleDO> nav_icon_list;
    private List<PmsProductAttributeCategory> cat_list;
    private int cat_goods_cols;
    private List<TArticleDO> block_list;
    private List<SmsCoupon> coupon_list;
    private List<CmsSubject> subjectList;

    public List<CmsSubject> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(List<CmsSubject> subjectList) {
        this.subjectList = subjectList;
    }

    public List<TArticleDO> getModule_list() {
        return module_list;
    }

    public void setModule_list(List<TArticleDO> module_list) {
        this.module_list = module_list;
    }

    public List<SmsHomeAdvertise> getBanner_list() {
        return banner_list;
    }

    public void setBanner_list(List<SmsHomeAdvertise> banner_list) {
        this.banner_list = banner_list;
    }

    public List<TArticleDO> getNav_icon_list() {
        return nav_icon_list;
    }

    public void setNav_icon_list(List<TArticleDO> nav_icon_list) {
        this.nav_icon_list = nav_icon_list;
    }

    public List<PmsProductAttributeCategory> getCat_list() {
        return cat_list;
    }

    public void setCat_list(List<PmsProductAttributeCategory> cat_list) {
        this.cat_list = cat_list;
    }

    public int getCat_goods_cols() {
        return cat_goods_cols;
    }

    public void setCat_goods_cols(int cat_goods_cols) {
        this.cat_goods_cols = cat_goods_cols;
    }

    public List<TArticleDO> getBlock_list() {
        return block_list;
    }

    public void setBlock_list(List<TArticleDO> block_list) {
        this.block_list = block_list;
    }

    public List<SmsCoupon> getCoupon_list() {
        return coupon_list;
    }

    public void setCoupon_list(List<SmsCoupon> coupon_list) {
        this.coupon_list = coupon_list;
    }

}
