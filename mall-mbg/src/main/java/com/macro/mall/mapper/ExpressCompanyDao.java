package com.macro.mall.mapper;


import com.macro.mall.model.ExpressCompany;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExpressCompanyDao {
	
    int deleteByPrimaryKey(Integer id);

    int insert(ExpressCompany record);

    ExpressCompany selectByPrimaryKey(Integer id);
    
    ExpressCompany selectByCorpName(@Param("expressCorpName") String expressCorpName);

    int updateByPrimaryKey(ExpressCompany record);
    
    ExpressCompany selectByExpressCorpId(String expressCorpId);
    
    List<ExpressCompany> queryList();
    
}