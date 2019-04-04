package com.macro.mall.cms.controller;

import com.macro.mall.cms.service.MemberReportService;
import com.macro.mall.dto.CommonResult;
import com.macro.mall.model.CmsMemberReport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户举报表
 *
 * @author zscat
 * @email 951449465@qq.com
 * @date 2019-03-22 17:58:26
 */
@Controller
@Api(tags = "MemberReportController", description = "用户举报表管理")
@RequestMapping("/cms/memberReport")
public class MemberReportController {
    @Resource
    private MemberReportService memberReportService;


    @ApiOperation(value = "添加用户举报表")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('cms:memberReport:create')")
    public Object create(@Validated @RequestBody CmsMemberReport cmsMemberReport, BindingResult result) {
        CommonResult commonResult;
        int count = memberReportService.createMemberReport(cmsMemberReport);
        if (count == 1) {
            commonResult = new CommonResult().success(count);
        } else {
            commonResult = new CommonResult().failed();
        }
        return commonResult;
    }

    @ApiOperation(value = "更新用户举报表")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('cms:memberReport:update')")
    public Object update(@PathVariable("id") Long id,
                         @Validated @RequestBody CmsMemberReport cmsMemberReport,
                         BindingResult result) {
        CommonResult commonResult;
        int count = memberReportService.updateMemberReport(id, cmsMemberReport);
        if (count == 1) {
            commonResult = new CommonResult().success(count);
        } else {
            commonResult = new CommonResult().failed();
        }
        return commonResult;
    }

    @ApiOperation(value = "删除用户举报表")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAuthority('cms:memberReport:delete')")
    public Object delete(@PathVariable("id") Long id) {
        int count = memberReportService.deleteMemberReport(id);
        if (count == 1) {
            return new CommonResult().success(null);
        } else {
            return new CommonResult().failed();
        }
    }

    @ApiOperation(value = "根据用户举报表名称分页获取用户举报表列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAuthority('cms:memberReport:read')")
    public Object getList(CmsMemberReport memberReport,
                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                          @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        return new CommonResult().pageSuccess(memberReportService.listMemberReport(memberReport, pageNum, pageSize));
    }

    @ApiOperation(value = "根据编号查询用户举报表信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAuthority('cms:memberReport:read')")
    public Object getItem(@PathVariable("id") Long id) {
        return new CommonResult().success(memberReportService.getMemberReport(id));
    }

}
