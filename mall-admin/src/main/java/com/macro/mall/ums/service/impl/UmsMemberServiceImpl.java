package com.macro.mall.ums.service.impl;

import com.github.pagehelper.PageHelper;

import com.macro.mall.mapper.UmsMemberMapper;

import com.macro.mall.model.UmsMember;
import com.macro.mall.model.UmsMemberExample;
import com.macro.mall.model.PmsProduct;
import com.macro.mall.model.PmsProductExample;

import com.macro.mall.ums.service.UmsMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 商品品牌Service实现类
 * https://gitee.com/zscat-platform/mall on 2018/4/26.
 */
@Service
public class UmsMemberServiceImpl implements UmsMemberService {
    @Autowired
    private UmsMemberMapper memberMapper;


    @Override
    public List<UmsMember> listAllMember() {
        return memberMapper.selectByExample(new UmsMemberExample());
    }

    @Override
    public int createMember(UmsMember umsMember) {
        return memberMapper.insertSelective(umsMember);
    }

    @Override
    public int updateMember(Long id, UmsMember umsMember) {
        umsMember.setId(id);
        return memberMapper.updateByPrimaryKeySelective(umsMember);
    }

    @Override
    public int deleteMember(Long id) {
        return memberMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteMember(List<Long> ids) {
        UmsMemberExample UmsMemberExample = new UmsMemberExample();
        UmsMemberExample.createCriteria().andIdIn(ids);
        return memberMapper.deleteByExample(UmsMemberExample);
    }

    @Override
    public List<UmsMember> listMember(String keyword, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        UmsMemberExample UmsMemberExample = new UmsMemberExample();
        UmsMemberExample.Criteria criteria = UmsMemberExample.createCriteria();
        if (!StringUtils.isEmpty(keyword)) {
            criteria.andUsernameLike("%" + keyword + "%");
        }
        return memberMapper.selectByExample(UmsMemberExample);
    }

    @Override
    public UmsMember getMember(Long id) {
        return memberMapper.selectByPrimaryKey(id);
    }


}
