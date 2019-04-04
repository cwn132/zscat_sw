package com.macro.mall.cms.service.impl;

import com.github.pagehelper.PageHelper;
import com.macro.mall.cms.service.HelpCategoryService;
import com.macro.mall.mapper.CmsHelpCategoryMapper;
import com.macro.mall.model.CmsHelpCategory;
import com.macro.mall.model.CmsHelpCategoryExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品品牌Service实现类
 * Created by macro on 2018/4/26.
 */
@Service
public class HelpCategoryServiceImpl implements HelpCategoryService {
    @Resource
    private CmsHelpCategoryMapper helpCategoryMapper;
    


    @Override
    public int createHelpCategory(CmsHelpCategory helpCategory) {
        return helpCategoryMapper.insert(helpCategory);
    }

    @Override
    public int updateHelpCategory(Long id, CmsHelpCategory helpCategory) {
        helpCategory.setId(id);
        return helpCategoryMapper.updateByPrimaryKeySelective(helpCategory);
    }

    @Override
    public int deleteHelpCategory(Long id) {
        return helpCategoryMapper.deleteByPrimaryKey(id);
    }


    @Override
    public List<CmsHelpCategory> listHelpCategory(CmsHelpCategory helpCategory, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return helpCategoryMapper.selectByExample(new CmsHelpCategoryExample());

    }

    @Override
    public CmsHelpCategory getHelpCategory(Long id) {
        return helpCategoryMapper.selectByPrimaryKey(id);
    }

   
}
