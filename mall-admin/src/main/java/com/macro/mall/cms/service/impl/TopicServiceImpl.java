package com.macro.mall.cms.service.impl;

import com.github.pagehelper.PageHelper;

import com.macro.mall.mapper.CmsTopicMapper;

import com.macro.mall.model.CmsTopic;

import com.macro.mall.model.CmsTopicExample;
import com.macro.mall.cms.service.TopicService;
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
public class TopicServiceImpl implements TopicService {
    @Resource
    private CmsTopicMapper topicMapper;
    


    @Override
    public int createTopic(CmsTopic topic) {
        return topicMapper.insert(topic);
    }

    @Override
    public int updateTopic(Long id, CmsTopic topic) {
        topic.setId(id);
        return topicMapper.updateByPrimaryKeySelective(topic);
    }

    @Override
    public int deleteTopic(Long id) {
        return topicMapper.deleteByPrimaryKey(id);
    }


    @Override
    public List<CmsTopic> listTopic(CmsTopic topic, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return topicMapper.selectByExample(new CmsTopicExample());

    }

    @Override
    public CmsTopic getTopic(Long id) {
        return topicMapper.selectByPrimaryKey(id);
    }

   
}
