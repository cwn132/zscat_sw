package com.macro.mall.mapper;


import com.macro.mall.model.Area;

import java.util.List;


/**
 * 
 * @author zscat
 * @email 951449465@qq.com
 * @date 2019-03-28 16:50:35
 */

public interface AreaMapper {

	Area get(Long id);
	
	List<Area> list(Area area);
	
	int save(Area area);
	
	int update(Area area);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
