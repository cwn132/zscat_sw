package com.macro.mall.portal.service;

import com.macro.mall.model.OmsCompanyAddress;

import java.util.List;

/**
 * 收货地址管Service
 * https://gitee.com/zscat-platform/mall on 2018/10/18.
 */
public interface OmsCompanyAddressService {
    /**
     * 获取全部收货地址
     */
    List<OmsCompanyAddress> list();
}
