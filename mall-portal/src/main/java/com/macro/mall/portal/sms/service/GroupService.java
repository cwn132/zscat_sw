package com.macro.mall.portal.sms.service;


import com.macro.mall.model.UmsMember;
import com.macro.mall.portal.sms.vo.GroupAndOrderVo;
import com.macro.mall.sms.model.SmsGroup;
import com.macro.mall.sms.model.SmsGroupMember;

import java.util.List;

/**
 * 
 *
 * @author zscat
 * @email 951449465@qq.com
 * @date 2019-03-29 14:29:09
 */
public interface GroupService {

    /**
     * 发起拼团或者加入拼团
     * @param groupMember
     * @return
     */
    List<SmsGroupMember>  addGroup(SmsGroupMember groupMember);
    int createGroup(SmsGroup group);
    
    int updateGroup(Long id, SmsGroup group);

    int deleteGroup(Long id);



    List<SmsGroup> listGroup(SmsGroup group, int pageNum, int pageSize);

    SmsGroup getGroup(Long id);


    Object generateOrder(GroupAndOrderVo orderParam,UmsMember member);

    Object preOrder(GroupAndOrderVo orderParam);
}
