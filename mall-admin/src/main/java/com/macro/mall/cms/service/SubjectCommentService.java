package com.macro.mall.cms.service;

import com.macro.mall.model.CmsSubjectComment;


import java.util.List;

/**
 * 专题评论表
 *
 * @author zscat
 * @email 951449465@qq.com
 * @date 2019-03-22 18:11:15
 */
public interface SubjectCommentService {


    int createSubjectComment(CmsSubjectComment subjectComment);
    
    int updateSubjectComment(Long id, CmsSubjectComment subjectComment);

    int deleteSubjectComment(Long id);



    List<CmsSubjectComment> listSubjectComment(CmsSubjectComment subjectComment, int pageNum, int pageSize);

    CmsSubjectComment getSubjectComment(Long id);

   
}
