package com.macro.mall.cms.service;

import com.macro.mall.model.CmsMemberReport;


import java.util.List;

/**
 * 用户举报表
 *
 * @author zscat
 * @email 951449465@qq.com
 * @date 2019-03-22 17:58:26
 */
public interface MemberReportService {


    int createMemberReport(CmsMemberReport memberReport);
    
    int updateMemberReport(Long id, CmsMemberReport memberReport);

    int deleteMemberReport(Long id);



    List<CmsMemberReport> listMemberReport(CmsMemberReport memberReport, int pageNum, int pageSize);

    CmsMemberReport getMemberReport(Long id);

   
}
