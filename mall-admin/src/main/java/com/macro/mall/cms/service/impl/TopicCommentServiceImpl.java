package com.macro.mall.cms.service.impl;

import com.github.pagehelper.PageHelper;

import com.macro.mall.mapper.CmsTopicCommentMapper;

import com.macro.mall.model.CmsTopicComment;

import com.macro.mall.model.CmsTopicCommentExample;
import com.macro.mall.cms.service.TopicCommentService;
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
public class TopicCommentServiceImpl implements TopicCommentService {
    @Resource
    private CmsTopicCommentMapper topicCommentMapper;
    


    @Override
    public int createTopicComment(CmsTopicComment topicComment) {
        return topicCommentMapper.insert(topicComment);
    }

    @Override
    public int updateTopicComment(Long id, CmsTopicComment topicComment) {
        topicComment.setId(id);
        return topicCommentMapper.updateByPrimaryKeySelective(topicComment);
    }

    @Override
    public int deleteTopicComment(Long id) {
        return topicCommentMapper.deleteByPrimaryKey(id);
    }


    @Override
    public List<CmsTopicComment> listTopicComment(CmsTopicComment topicComment, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return topicCommentMapper.selectByExample(new CmsTopicCommentExample());

    }

    @Override
    public CmsTopicComment getTopicComment(Long id) {
        return topicCommentMapper.selectByPrimaryKey(id);
    }

   
}
