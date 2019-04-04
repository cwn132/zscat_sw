package com.macro.mall.cms.controller;

import com.macro.mall.cms.service.SubjectService;
import com.macro.mall.dto.CommonResult;
import com.macro.mall.model.CmsSubject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 专题表
 *
 * @author zscat
 * @email 951449465@qq.com
 * @date 2019-03-22 17:16:43
 */
@Controller
@Api(tags = "SubjectController", description = "专题表管理")
@RequestMapping("/cms/subject")
public class SubjectController {
    @Resource
    private SubjectService subjectService;


    @ApiOperation(value = "添加专题表")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('cms:subject:create')")
    public Object create(@Validated @RequestBody CmsSubject cmsSubject, BindingResult result) {
        CommonResult commonResult;
        int count = subjectService.createSubject(cmsSubject);
        if (count == 1) {
            commonResult = new CommonResult().success(count);
        } else {
            commonResult = new CommonResult().failed();
        }
        return commonResult;
    }

    @ApiOperation(value = "更新专题表")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('cms:subject:update')")
    public Object update(@PathVariable("id") Long id,
                         @Validated @RequestBody CmsSubject cmsSubjectParam,
                         BindingResult result) {
        CommonResult commonResult;
        int count = subjectService.updateSubject(id, cmsSubjectParam);
        if (count == 1) {
            commonResult = new CommonResult().success(count);
        } else {
            commonResult = new CommonResult().failed();
        }
        return commonResult;
    }

    @ApiOperation(value = "删除专题表")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAuthority('cms:subject:delete')")
    public Object delete(@PathVariable("id") Long id) {
        int count = subjectService.deleteSubject(id);
        if (count == 1) {
            return new CommonResult().success(null);
        } else {
            return new CommonResult().failed();
        }
    }

    @ApiOperation(value = "根据专题表名称分页获取专题表列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAuthority('cms:subject:read')")
    public Object getList(CmsSubject subject,
                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                          @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        return new CommonResult().pageSuccess(subjectService.listSubject(subject, pageNum, pageSize));
    }

    @ApiOperation(value = "根据编号查询专题表信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAuthority('cms:subject:read')")
    public Object getItem(@PathVariable("id") Long id) {
        return new CommonResult().success(subjectService.getSubject(id));
    }

}
