package com.macro.mall.cms.service.impl;

import com.github.pagehelper.PageHelper;
import com.macro.mall.cms.service.SubjectService;
import com.macro.mall.mapper.CmsSubjectMapper;
import com.macro.mall.model.CmsSubject;
import com.macro.mall.model.CmsSubjectExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品品牌Service实现类
 * Created by macro on 2018/4/26.
 */
@Service
public class SubjectServiceImpl implements SubjectService {
    @Resource
    private CmsSubjectMapper subjectMapper;
    


    @Override
    public int createSubject(CmsSubject subject) {
        return subjectMapper.insert(subject);
    }

    @Override
    public int updateSubject(Long id, CmsSubject subject) {
        subject.setId(id);
        return subjectMapper.updateByPrimaryKeySelective(subject);
    }

    @Override
    public int deleteSubject(Long id) {
        return subjectMapper.deleteByPrimaryKey(id);
    }


    @Override
    public List<CmsSubject> listSubject(CmsSubject subject, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return subjectMapper.selectByExample(new CmsSubjectExample());

    }

    @Override
    public CmsSubject getSubject(Long id) {
        return subjectMapper.selectByPrimaryKey(id);
    }

   
}
