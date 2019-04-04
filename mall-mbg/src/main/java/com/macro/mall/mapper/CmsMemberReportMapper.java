package com.macro.mall.mapper;

import com.macro.mall.model.CmsMemberReport;
import com.macro.mall.model.CmsMemberReportExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CmsMemberReportMapper {
    int countByExample(CmsMemberReportExample example);

    int deleteByExample(CmsMemberReportExample example);

    int insert(CmsMemberReport record);

    int insertSelective(CmsMemberReport record);

    List<CmsMemberReport> selectByExample(CmsMemberReportExample example);

    int updateByExampleSelective(@Param("record") CmsMemberReport record, @Param("example") CmsMemberReportExample example);

    int updateByExample(@Param("record") CmsMemberReport record, @Param("example") CmsMemberReportExample example);

    CmsMemberReport selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CmsMemberReport memberReport);
}