package com.macro.mall.sms.service.impl;

import com.github.pagehelper.PageHelper;
import com.macro.mall.sms.mapper.GroupMapper;
import com.macro.mall.sms.model.SmsGroup;
import com.macro.mall.sms.service.GroupService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品品牌Service实现类
 * Created by macro on 2018/4/26.
 */
@Service
public class GroupServiceImpl implements GroupService {
    @Resource
    private GroupMapper groupMapper;
    


    @Override
    public int createGroup(SmsGroup group) {
        return groupMapper.save(group);
    }

    @Override
    public int updateGroup(Long id, SmsGroup group) {
        group.setId(id);
        return groupMapper.update(group);
    }

    @Override
    public int deleteGroup(Long id) {
        return groupMapper.remove(id);
    }


    @Override
    public List<SmsGroup> listGroup(SmsGroup group, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return groupMapper.list(new SmsGroup());

    }

    @Override
    public SmsGroup getGroup(Long id) {
        return groupMapper.get(id);
    }

   
}
