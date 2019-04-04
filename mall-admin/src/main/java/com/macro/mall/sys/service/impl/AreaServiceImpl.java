package com.macro.mall.sys.service.impl;

import com.github.pagehelper.PageHelper;
import com.macro.mall.mapper.AreaMapper;
import com.macro.mall.model.Area;
import com.macro.mall.sys.service.AreaService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品品牌Service实现类
 * Created by macro on 2018/4/26.
 */
@Service
public class AreaServiceImpl implements AreaService {
    @Resource
    private AreaMapper areaMapper;
    


    @Override
    public int createArea(Area area) {
        return areaMapper.save(area);
    }

    @Override
    public int updateArea(Long id, Area area) {
        area.setId(id);
        return areaMapper.update(area);
    }

    @Override
    public int deleteArea(Long id) {
        return areaMapper.remove(id);
    }


    @Override
    public List<Area> listArea(Area area, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return areaMapper.list(area);

    }

    @Override
    public Area getArea(Long id) {
        return areaMapper.get(id);
    }

   
}
