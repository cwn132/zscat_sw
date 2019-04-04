package com.fittime.ecom.fitcamp.sale.dao;

import com.fittime.ecom.fitcamp.sale.model.PmsProductConsult;

public interface PmsProductConsultMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PmsProductConsult record);

    int insertSelective(PmsProductConsult record);

    PmsProductConsult selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PmsProductConsult record);

    int updateByPrimaryKey(PmsProductConsult record);
}