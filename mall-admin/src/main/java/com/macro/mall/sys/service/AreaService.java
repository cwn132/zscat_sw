package com.macro.mall.sys.service;


import com.macro.mall.model.Area;

import java.util.List;

/**
 * 
 *
 * @author zscat
 * @email 951449465@qq.com
 * @date 2019-03-28 16:50:35
 */
public interface AreaService {


    int createArea(Area area);
    
    int updateArea(Long id, Area area);

    int deleteArea(Long id);



    List<Area> listArea(Area area, int pageNum, int pageSize);

    Area getArea(Long id);

   
}
