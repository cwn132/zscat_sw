package com.macro.mall.portal.service;

import com.macro.mall.model.PmsProductConsult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品品牌Service
 * https://gitee.com/zscat-platform/mall on 2018/4/26.
 */
public interface PmsProductConsultService {

    int create(PmsProductConsult record);

    @Transactional
    int update(Long id, PmsProductConsult record);

    int delete(Long id);

    int delete(List<Long> ids);

    List<PmsProductConsult> list(PmsProductConsult record, int pageNum, int pageSize);

    PmsProductConsult get(Long id);


}
