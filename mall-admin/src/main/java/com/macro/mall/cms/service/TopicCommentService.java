package com.macro.mall.cms.service;

import com.macro.mall.model.CmsTopicComment;


import java.util.List;

/**
 * 专题评论表
 *
 * @author zscat
 * @email 951449465@qq.com
 * @date 2019-03-22 18:12:05
 */
public interface TopicCommentService {


    int createTopicComment(CmsTopicComment topicComment);
    
    int updateTopicComment(Long id, CmsTopicComment topicComment);

    int deleteTopicComment(Long id);



    List<CmsTopicComment> listTopicComment(CmsTopicComment topicComment, int pageNum, int pageSize);

    CmsTopicComment getTopicComment(Long id);

   
}
