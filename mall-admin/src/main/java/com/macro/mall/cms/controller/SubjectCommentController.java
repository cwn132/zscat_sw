package com.macro.mall.cms.controller;

import com.macro.mall.dto.CommonResult;
import com.macro.mall.model.CmsSubjectComment;

import com.macro.mall.cms.service.SubjectCommentService;
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
 * 专题评论表
 *
 * @author zscat
 * @email 951449465@qq.com
 * @date 2019-03-22 18:11:15
 */
@Controller
@Api(tags = "SubjectCommentController", description = "专题评论表管理")
@RequestMapping("/cms/subjectComment")
public class SubjectCommentController {
    @Resource
    private SubjectCommentService subjectCommentService;


    @ApiOperation(value = "添加专题评论表")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('cms:subjectComment:create')")
    public Object create(@Validated @RequestBody CmsSubjectComment cmsSubjectComment, BindingResult result) {
        CommonResult commonResult;
        int count = subjectCommentService.createSubjectComment(cmsSubjectComment);
        if (count == 1) {
            commonResult = new CommonResult().success(count);
        } else {
            commonResult = new CommonResult().failed();
        }
        return commonResult;
    }

    @ApiOperation(value = "更新专题评论表")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('cms:subjectComment:update')")
    public Object update(@PathVariable("id") Long id,
                         @Validated @RequestBody CmsSubjectComment cmsSubjectComment,
                         BindingResult result) {
        CommonResult commonResult;
        int count = subjectCommentService.updateSubjectComment(id, cmsSubjectComment);
        if (count == 1) {
            commonResult = new CommonResult().success(count);
        } else {
            commonResult = new CommonResult().failed();
        }
        return commonResult;
    }

    @ApiOperation(value = "删除专题评论表")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAuthority('cms:subjectComment:delete')")
    public Object delete(@PathVariable("id") Long id) {
        int count = subjectCommentService.deleteSubjectComment(id);
        if (count == 1) {
            return new CommonResult().success(null);
        } else {
            return new CommonResult().failed();
        }
    }

    @ApiOperation(value = "根据专题评论表名称分页获取专题评论表列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAuthority('cms:subjectComment:read')")
    public Object getList(CmsSubjectComment subjectComment,
                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                          @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        return new CommonResult().pageSuccess(subjectCommentService.listSubjectComment(subjectComment, pageNum, pageSize));
    }

    @ApiOperation(value = "根据编号查询专题评论表信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAuthority('cms:subjectComment:read')")
    public Object getItem(@PathVariable("id") Long id) {
        return new CommonResult().success(subjectCommentService.getSubjectComment(id));
    }

}
