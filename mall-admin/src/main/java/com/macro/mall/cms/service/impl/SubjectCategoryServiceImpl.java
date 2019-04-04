package com.macro.mall.cms.service.impl;

import com.github.pagehelper.PageHelper;

import com.macro.mall.mapper.CmsSubjectCategoryMapper;

import com.macro.mall.model.CmsSubjectCategory;

import com.macro.mall.model.CmsSubjectCategoryExample;
import com.macro.mall.cms.service.SubjectCategoryService;
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
public class SubjectCategoryServiceImpl implements SubjectCategoryService {
    @Resource
    private CmsSubjectCategoryMapper subjectCategoryMapper;
    


    @Override
    public int createSubjectCategory(CmsSubjectCategory subjectCategory) {
        return subjectCategoryMapper.insert(subjectCategory);
    }

    @Override
    public int updateSubjectCategory(Long id, CmsSubjectCategory subjectCategory) {
        subjectCategory.setId(id);
        return subjectCategoryMapper.updateByPrimaryKeySelective(subjectCategory);
    }

    @Override
    public int deleteSubjectCategory(Long id) {
        return subjectCategoryMapper.deleteByPrimaryKey(id);
    }


    @Override
    public List<CmsSubjectCategory> listSubjectCategory(CmsSubjectCategory subjectCategory, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return subjectCategoryMapper.selectByExample(new CmsSubjectCategoryExample());

    }

    @Override
    public CmsSubjectCategory getSubjectCategory(Long id) {
        return subjectCategoryMapper.selectByPrimaryKey(id);
    }

   
}
