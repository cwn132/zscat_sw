package com.macro.mall.sms.service;


import com.macro.mall.sms.model.SmsGroup;

import java.util.List;

/**
 * 
 *
 * @author zscat
 * @email 951449465@qq.com
 * @date 2019-03-29 14:29:09
 */
public interface GroupService {


    int createGroup(SmsGroup group);
    
    int updateGroup(Long id, SmsGroup group);

    int deleteGroup(Long id);



    List<SmsGroup> listGroup(SmsGroup group, int pageNum, int pageSize);

    SmsGroup getGroup(Long id);

   
}
