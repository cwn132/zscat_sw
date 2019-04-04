package com.macro.mall.cms.service.impl;

import com.github.pagehelper.PageHelper;

import com.macro.mall.mapper.CmsSubjectCommentMapper;

import com.macro.mall.model.CmsSubjectComment;

import com.macro.mall.model.CmsSubjectCommentExample;
import com.macro.mall.cms.service.SubjectCommentService;
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
public class SubjectCommentServiceImpl implements SubjectCommentService {
    @Resource
    private CmsSubjectCommentMapper subjectCommentMapper;
    


    @Override
    public int createSubjectComment(CmsSubjectComment subjectComment) {
        return subjectCommentMapper.insert(subjectComment);
    }

    @Override
    public int updateSubjectComment(Long id, CmsSubjectComment subjectComment) {
        subjectComment.setId(id);
        return subjectCommentMapper.updateByPrimaryKeySelective(subjectComment);
    }

    @Override
    public int deleteSubjectComment(Long id) {
        return subjectCommentMapper.deleteByPrimaryKey(id);
    }


    @Override
    public List<CmsSubjectComment> listSubjectComment(CmsSubjectComment subjectComment, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return subjectCommentMapper.selectByExample(new CmsSubjectCommentExample());

    }

    @Override
    public CmsSubjectComment getSubjectComment(Long id) {
        return subjectCommentMapper.selectByPrimaryKey(id);
    }

   
}
