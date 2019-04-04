package com.macro.mall.cms.service;

import com.macro.mall.model.CmsSubject;

import java.util.List;

/**
 * 专题表
 *
 * @author zscat
 * @email 951449465@qq.com
 * @date 2019-03-22 17:16:43
 */
public interface SubjectService {


    int createSubject(CmsSubject subject);
    
    int updateSubject(Long id, CmsSubject subject);

    int deleteSubject(Long id);



    List<CmsSubject> listSubject(CmsSubject subject, int pageNum, int pageSize);

    CmsSubject getSubject(Long id);

   
}
