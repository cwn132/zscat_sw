package com.macro.mall.cms.controller;

import com.macro.mall.dto.CommonResult;
import com.macro.mall.model.CmsTopicCategory;

import com.macro.mall.cms.service.TopicCategoryService;
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
 * 话题分类表
 *
 * @author zscat
 * @email 951449465@qq.com
 * @date 2019-03-22 18:11:51
 */
@Controller
@Api(tags = "TopicCategoryController", description = "话题分类表管理")
@RequestMapping("/cms/topicCategory")
public class TopicCategoryController {
    @Resource
    private TopicCategoryService topicCategoryService;


    @ApiOperation(value = "添加话题分类表")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('cms:topicCategory:create')")
    public Object create(@Validated @RequestBody CmsTopicCategory cmsTopicCategory, BindingResult result) {
        CommonResult commonResult;
        int count = topicCategoryService.createTopicCategory(cmsTopicCategory);
        if (count == 1) {
            commonResult = new CommonResult().success(count);
        } else {
            commonResult = new CommonResult().failed();
        }
        return commonResult;
    }

    @ApiOperation(value = "更新话题分类表")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('cms:topicCategory:update')")
    public Object update(@PathVariable("id") Long id,
                         @Validated @RequestBody CmsTopicCategory cmsTopicCategory,
                         BindingResult result) {
        CommonResult commonResult;
        int count = topicCategoryService.updateTopicCategory(id, cmsTopicCategory);
        if (count == 1) {
            commonResult = new CommonResult().success(count);
        } else {
            commonResult = new CommonResult().failed();
        }
        return commonResult;
    }

    @ApiOperation(value = "删除话题分类表")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAuthority('cms:topicCategory:delete')")
    public Object delete(@PathVariable("id") Long id) {
        int count = topicCategoryService.deleteTopicCategory(id);
        if (count == 1) {
            return new CommonResult().success(null);
        } else {
            return new CommonResult().failed();
        }
    }

    @ApiOperation(value = "根据话题分类表名称分页获取话题分类表列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAuthority('cms:topicCategory:read')")
    public Object getList(CmsTopicCategory topicCategory,
                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                          @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        return new CommonResult().pageSuccess(topicCategoryService.listTopicCategory(topicCategory, pageNum, pageSize));
    }

    @ApiOperation(value = "根据编号查询话题分类表信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAuthority('cms:topicCategory:read')")
    public Object getItem(@PathVariable("id") Long id) {
        return new CommonResult().success(topicCategoryService.getTopicCategory(id));
    }

}
