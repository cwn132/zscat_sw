package com.macro.mall.cms.service.impl;

import com.github.pagehelper.PageHelper;

import com.macro.mall.mapper.CmsPrefrenceAreaMapper;

import com.macro.mall.model.CmsPrefrenceArea;

import com.macro.mall.model.CmsPrefrenceAreaExample;
import com.macro.mall.cms.service.PrefrenceAreaService;
import org.springframework.beans.BeanUtils;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 商品品牌Service实现类
 * Created by macro on 2018/4/26.
 */
@Service
public class PrefrenceAreaServiceImpl implements PrefrenceAreaService {
    @Resource
    private CmsPrefrenceAreaMapper prefrenceAreaMapper;
    


    @Override
    public int createPrefrenceArea(CmsPrefrenceArea prefrenceArea) {
        return prefrenceAreaMapper.insert(prefrenceArea);
    }

    @Override
    public int updatePrefrenceArea(Long id, CmsPrefrenceArea prefrenceArea) {
        prefrenceArea.setId(id);
        return prefrenceAreaMapper.updateByPrimaryKeySelective(prefrenceArea);
    }

    @Override
    public int deletePrefrenceArea(Long id) {
        return prefrenceAreaMapper.deleteByPrimaryKey(id);
    }


    @Override
    public List<CmsPrefrenceArea> listPrefrenceArea(CmsPrefrenceArea prefrenceArea, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return prefrenceAreaMapper.selectByExample(new CmsPrefrenceAreaExample());

    }

    @Override
    public CmsPrefrenceArea getPrefrenceArea(Long id) {
        return prefrenceAreaMapper.selectByPrimaryKey(id);
    }

   
}
