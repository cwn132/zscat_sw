package com.macro.mall.mapper;

import com.macro.mall.model.SmsCoupon;
import com.macro.mall.model.SmsCouponExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SmsCouponMapper {
    int countByExample(SmsCouponExample example);

    int deleteByExample(SmsCouponExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SmsCoupon record);

    int insertSelective(SmsCoupon record);

    List<SmsCoupon> selectByExample(SmsCouponExample example);

    SmsCoupon selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SmsCoupon record, @Param("example") SmsCouponExample example);

    int updateByExample(@Param("record") SmsCoupon record, @Param("example") SmsCouponExample example);

    int updateByPrimaryKeySelective(SmsCoupon record);

    int updateByPrimaryKey(SmsCoupon record);

    List<SmsCoupon> selectNotRecive(Long memberId);
    List<SmsCoupon> selectRecive(Long memberId);



}