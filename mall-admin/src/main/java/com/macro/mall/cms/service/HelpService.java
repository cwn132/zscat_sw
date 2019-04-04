package com.macro.mall.cms.service;

import com.macro.mall.model.CmsHelp;

import java.util.List;

/**
 * 帮助表
 *
 * @author zscat
 * @email 951449465@qq.com
 * @date 2019-03-22 13:40:04
 */
public interface HelpService {


    int createHelp(CmsHelp help);
    
    int updateHelp(Long id, CmsHelp help);

    int deleteHelp(Long id);



    List<CmsHelp> listHelp(CmsHelp help, int pageNum, int pageSize);

    CmsHelp getHelp(Long id);

   
}
