package com.macro.mall.portal.service;

import com.macro.mall.model.PmsSkuStock;

import java.util.List;

/**
 * sku商品库存管理Service
 * https://gitee.com/zscat-platform/mall on 2018/4/27.
 */
public interface PmsSkuStockService {
    /**
     * 根据产品id和skuCode模糊搜索
     */
    List<PmsSkuStock> getList(Long pid, String keyword);

    /**
     * 批量更新商品库存信息
     */
    int update(Long pid, List<PmsSkuStock> skuStockList);

    PmsSkuStock selectById(Long id) ;
}
