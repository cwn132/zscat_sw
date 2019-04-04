package com.macro.mall.cms.controller;

import com.macro.mall.cms.service.HelpCategoryService;
import com.macro.mall.dto.CommonResult;
import com.macro.mall.model.CmsHelpCategory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 帮助分类表
 *
 * @author zscat
 * @email 951449465@qq.com
 * @date 2019-03-22 17:34:55
 */
@Controller
@Api(tags = "HelpCategoryController", description = "帮助分类表管理")
@RequestMapping("/cms/helpCategory")
public class HelpCategoryController {
    @Resource
    private HelpCategoryService helpCategoryService;


    @ApiOperation(value = "添加帮助分类表")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('cms:helpCategory:create')")
    public Object create(@Validated @RequestBody CmsHelpCategory cmsHelpCategory, BindingResult result) {
        CommonResult commonResult;
        int count = helpCategoryService.createHelpCategory(cmsHelpCategory);
        if (count == 1) {
            commonResult = new CommonResult().success(count);
        } else {
            commonResult = new CommonResult().failed();
        }
        return commonResult;
    }

    @ApiOperation(value = "更新帮助分类表")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('cms:helpCategory:update')")
    public Object update(@PathVariable("id") Long id,
                         @Validated @RequestBody CmsHelpCategory cmsHelpCategoryParam,
                         BindingResult result) {
        CommonResult commonResult;
        int count = helpCategoryService.updateHelpCategory(id, cmsHelpCategoryParam);
        if (count == 1) {
            commonResult = new CommonResult().success(count);
        } else {
            commonResult = new CommonResult().failed();
        }
        return commonResult;
    }

    @ApiOperation(value = "删除帮助分类表")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAuthority('cms:helpCategory:delete')")
    public Object delete(@PathVariable("id") Long id) {
        int count = helpCategoryService.deleteHelpCategory(id);
        if (count == 1) {
            return new CommonResult().success(null);
        } else {
            return new CommonResult().failed();
        }
    }

    @ApiOperation(value = "根据帮助分类表名称分页获取帮助分类表列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAuthority('cms:helpCategory:read')")
    public Object getList(CmsHelpCategory helpCategory,
                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                          @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        return new CommonResult().pageSuccess(helpCategoryService.listHelpCategory(helpCategory, pageNum, pageSize));
    }

    @ApiOperation(value = "根据编号查询帮助分类表信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAuthority('cms:helpCategory:read')")
    public Object getItem(@PathVariable("id") Long id) {
        return new CommonResult().success(helpCategoryService.getHelpCategory(id));
    }

}
