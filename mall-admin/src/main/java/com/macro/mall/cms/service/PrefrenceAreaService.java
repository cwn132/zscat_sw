package com.macro.mall.cms.service;

import com.macro.mall.model.CmsPrefrenceArea;


import java.util.List;

/**
 * 优选专区
 *
 * @author zscat
 * @email 951449465@qq.com
 * @date 2019-03-22 18:08:45
 */
public interface PrefrenceAreaService {


    int createPrefrenceArea(CmsPrefrenceArea prefrenceArea);
    
    int updatePrefrenceArea(Long id, CmsPrefrenceArea prefrenceArea);

    int deletePrefrenceArea(Long id);



    List<CmsPrefrenceArea> listPrefrenceArea(CmsPrefrenceArea prefrenceArea, int pageNum, int pageSize);

    CmsPrefrenceArea getPrefrenceArea(Long id);

   
}
