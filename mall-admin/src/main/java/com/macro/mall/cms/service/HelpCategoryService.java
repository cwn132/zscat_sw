package com.macro.mall.cms.service;


import com.macro.mall.model.CmsHelpCategory;

import java.util.List;

/**
 * 帮助分类表
 *
 * @author zscat
 * @email 951449465@qq.com
 * @date 2019-03-22 17:34:55
 */
public interface HelpCategoryService {


    int createHelpCategory(CmsHelpCategory helpCategory);
    
    int updateHelpCategory(Long id, CmsHelpCategory helpCategory);

    int deleteHelpCategory(Long id);



    List<CmsHelpCategory> listHelpCategory(CmsHelpCategory helpCategory, int pageNum, int pageSize);

    CmsHelpCategory getHelpCategory(Long id);

   
}
