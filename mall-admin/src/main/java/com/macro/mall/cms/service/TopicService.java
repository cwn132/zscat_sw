package com.macro.mall.cms.service;

import com.macro.mall.model.CmsTopic;


import java.util.List;

/**
 * 话题表
 *
 * @author zscat
 * @email 951449465@qq.com
 * @date 2019-03-22 18:11:46
 */
public interface TopicService {


    int createTopic(CmsTopic topic);
    
    int updateTopic(Long id, CmsTopic topic);

    int deleteTopic(Long id);



    List<CmsTopic> listTopic(CmsTopic topic, int pageNum, int pageSize);

    CmsTopic getTopic(Long id);

   
}
