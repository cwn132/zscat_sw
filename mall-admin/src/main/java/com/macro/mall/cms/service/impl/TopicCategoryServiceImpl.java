package com.macro.mall.cms.service.impl;

import com.github.pagehelper.PageHelper;

import com.macro.mall.mapper.CmsTopicCategoryMapper;

import com.macro.mall.model.CmsTopicCategory;

import com.macro.mall.model.CmsTopicCategoryExample;
import com.macro.mall.cms.service.TopicCategoryService;
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
public class TopicCategoryServiceImpl implements TopicCategoryService {
    @Resource
    private CmsTopicCategoryMapper topicCategoryMapper;
    


    @Override
    public int createTopicCategory(CmsTopicCategory topicCategory) {
        return topicCategoryMapper.insert(topicCategory);
    }

    @Override
    public int updateTopicCategory(Long id, CmsTopicCategory topicCategory) {
        topicCategory.setId(id);
        return topicCategoryMapper.updateByPrimaryKeySelective(topicCategory);
    }

    @Override
    public int deleteTopicCategory(Long id) {
        return topicCategoryMapper.deleteByPrimaryKey(id);
    }


    @Override
    public List<CmsTopicCategory> listTopicCategory(CmsTopicCategory topicCategory, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return topicCategoryMapper.selectByExample(new CmsTopicCategoryExample());

    }

    @Override
    public CmsTopicCategory getTopicCategory(Long id) {
        return topicCategoryMapper.selectByPrimaryKey(id);
    }

   
}
