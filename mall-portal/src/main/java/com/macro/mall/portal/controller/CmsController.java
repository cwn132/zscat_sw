package com.macro.mall.portal.controller;

import com.macro.mall.annotation.IgnoreAuth;
import com.macro.mall.dto.CommonResult;
import com.macro.mall.model.CmsSubject;
import com.macro.mall.model.CmsSubjectCategory;
import com.macro.mall.model.CmsSubjectComment;
import com.macro.mall.portal.cms.service.SubjectCategoryService;
import com.macro.mall.portal.cms.service.SubjectCommentService;
import com.macro.mall.portal.cms.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: shenzhuan
 * @Date: 2019/4/2 15:02
 * @Description:
 */
@RestController
@Api(tags = "CmsController", description = "内容关系管理")
@RequestMapping("/api/cms")
public class CmsController {

    @Resource
    private SubjectCategoryService subjectCategoryService;
    @Resource
    private SubjectService subjectService;
    @Resource
    private SubjectCommentService commentService;

    @IgnoreAuth
    @ApiOperation(value = "查询文章列表")
    @GetMapping(value = "/subject/list")
    public Object subjectList(CmsSubject subject,
                              @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize,
                              @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum) {
        List<CmsSubject> list = subjectService.listSubject(subject,pageSize,pageNum);
        return new CommonResult().pageSuccess(list);
    }

    @IgnoreAuth
    @ApiOperation(value = "查询文章分类列表")
    @GetMapping(value = "/subjectCategory/list")
    public Object cateList(CmsSubjectCategory subjectCategory,
                          @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize,
                          @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum) {
        List<CmsSubjectCategory> list = subjectCategoryService.listSubjectCategory(subjectCategory,pageSize,pageNum);
        return new CommonResult().pageSuccess(list);
    }


    @IgnoreAuth
    @ApiOperation(value = "查询文章评论列表")
    @GetMapping(value = "/subjectComment/list")
    public Object subjectList(CmsSubjectComment subjectComment,
                              @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize,
                              @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum) {
        List<CmsSubjectComment> list = commentService.listSubjectComment(subjectComment,pageSize,pageNum);
        return new CommonResult().pageSuccess(list);
    }

    @ApiOperation(value = "添加")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('t:area:create')")
    public Object create(@Validated @RequestBody CmsSubject subject, BindingResult result) {
        CommonResult commonResult;
        int count = subjectService.createSubject(subject);
        if (count == 1) {
            commonResult = new CommonResult().success(count);
        } else {
            commonResult = new CommonResult().failed();
        }
        return commonResult;
    }


}
