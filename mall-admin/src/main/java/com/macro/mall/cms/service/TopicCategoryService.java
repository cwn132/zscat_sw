package com.macro.mall.cms.service;

import com.macro.mall.model.CmsTopicCategory;


import java.util.List;

/**
 * 话题分类表
 *
 * @author zscat
 * @email 951449465@qq.com
 * @date 2019-03-22 18:11:51
 */
public interface TopicCategoryService {


    int createTopicCategory(CmsTopicCategory topicCategory);
    
    int updateTopicCategory(Long id, CmsTopicCategory topicCategory);

    int deleteTopicCategory(Long id);



    List<CmsTopicCategory> listTopicCategory(CmsTopicCategory topicCategory, int pageNum, int pageSize);

    CmsTopicCategory getTopicCategory(Long id);

   
}
