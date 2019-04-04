package com.macro.mall.cms.service;

import com.macro.mall.model.CmsSubjectCategory;


import java.util.List;

/**
 * 专题分类表
 *
 * @author zscat
 * @email 951449465@qq.com
 * @date 2019-03-22 18:11:04
 */
public interface SubjectCategoryService {


    int createSubjectCategory(CmsSubjectCategory subjectCategory);
    
    int updateSubjectCategory(Long id, CmsSubjectCategory subjectCategory);

    int deleteSubjectCategory(Long id);



    List<CmsSubjectCategory> listSubjectCategory(CmsSubjectCategory subjectCategory, int pageNum, int pageSize);

    CmsSubjectCategory getSubjectCategory(Long id);

   
}
