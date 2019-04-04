package com.macro.mall.cms.service.impl;

import com.github.pagehelper.PageHelper;

import com.macro.mall.mapper.CmsMemberReportMapper;

import com.macro.mall.model.CmsMemberReport;

import com.macro.mall.model.CmsMemberReportExample;
import com.macro.mall.cms.service.MemberReportService;
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
public class MemberReportServiceImpl implements MemberReportService {
    @Resource
    private CmsMemberReportMapper memberReportMapper;
    


    @Override
    public int createMemberReport(CmsMemberReport memberReport) {
        return memberReportMapper.insert(memberReport);
    }

    @Override
    public int updateMemberReport(Long id, CmsMemberReport memberReport) {
        memberReport.setId(id);
        return memberReportMapper.updateByPrimaryKeySelective(memberReport);
    }

    @Override
    public int deleteMemberReport(Long id) {
        return memberReportMapper.deleteByPrimaryKey(id);
    }


    @Override
    public List<CmsMemberReport> listMemberReport(CmsMemberReport memberReport, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return memberReportMapper.selectByExample(new CmsMemberReportExample());

    }

    @Override
    public CmsMemberReport getMemberReport(Long id) {
        return memberReportMapper.selectByPrimaryKey(id);
    }

   
}
