package com.macro.mall.cms.controller;

import com.macro.mall.dto.CommonResult;
import com.macro.mall.model.CmsSubjectCategory;

import com.macro.mall.cms.service.SubjectCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * 专题分类表
 *
 * @author zscat
 * @email 951449465@qq.com
 * @date 2019-03-22 18:11:04
 */
@Controller
@Api(tags = "SubjectCategoryController", description = "专题分类表管理")
@RequestMapping("/cms/subjectCategory")
public class SubjectCategoryController {
    @Resource
    private SubjectCategoryService subjectCategoryService;


    @ApiOperation(value = "添加专题分类表")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('cms:subjectCategory:create')")
    public Object create(@Validated @RequestBody CmsSubjectCategory cmsSubjectCategory, BindingResult result) {
        CommonResult commonResult;
        int count = subjectCategoryService.createSubjectCategory(cmsSubjectCategory);
        if (count == 1) {
            commonResult = new CommonResult().success(count);
        } else {
            commonResult = new CommonResult().failed();
        }
        return commonResult;
    }

    @ApiOperation(value = "更新专题分类表")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('cms:subjectCategory:update')")
    public Object update(@PathVariable("id") Long id,
                         @Validated @RequestBody CmsSubjectCategory cmsSubjectCategory,
                         BindingResult result) {
        CommonResult commonResult;
        int count = subjectCategoryService.updateSubjectCategory(id, cmsSubjectCategory);
        if (count == 1) {
            commonResult = new CommonResult().success(count);
        } else {
            commonResult = new CommonResult().failed();
        }
        return commonResult;
    }

    @ApiOperation(value = "删除专题分类表")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAuthority('cms:subjectCategory:delete')")
    public Object delete(@PathVariable("id") Long id) {
        int count = subjectCategoryService.deleteSubjectCategory(id);
        if (count == 1) {
            return new CommonResult().success(null);
        } else {
            return new CommonResult().failed();
        }
    }

    @ApiOperation(value = "根据专题分类表名称分页获取专题分类表列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAuthority('cms:subjectCategory:read')")
    public Object getList(CmsSubjectCategory subjectCategory,
                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                          @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        return new CommonResult().pageSuccess(subjectCategoryService.listSubjectCategory(subjectCategory, pageNum, pageSize));
    }

    @ApiOperation(value = "根据编号查询专题分类表信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAuthority('cms:subjectCategory:read')")
    public Object getItem(@PathVariable("id") Long id) {
        return new CommonResult().success(subjectCategoryService.getSubjectCategory(id));
    }

}
