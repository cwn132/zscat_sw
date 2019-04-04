package com.macro.mall.cms.controller;

import com.macro.mall.cms.service.HelpService;
import com.macro.mall.dto.CommonResult;
import com.macro.mall.model.CmsHelp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 帮助表
 *
 * @author zscat
 * @email 951449465@qq.com
 * @date 2019-03-22 13:40:04
 */
@Controller
@Api(tags = "HelpController", description = "${labeltext}管理")
@RequestMapping("/cms/help")
public class HelpController {
    @Resource
    private HelpService helpService;


    @ApiOperation(value = "添加${labeltext}")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('cms:help:create')")
    public Object create(@Validated @RequestBody CmsHelp cmsHelp, BindingResult result) {
        CommonResult commonResult;
        int count = helpService.createHelp(cmsHelp);
        if (count == 1) {
            commonResult = new CommonResult().success(count);
        } else {
            commonResult = new CommonResult().failed();
        }
        return commonResult;
    }

    @ApiOperation(value = "更新${labeltext}")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('cms:help:update')")
    public Object update(@PathVariable("id") Long id,
                         @Validated @RequestBody CmsHelp cmsCmsHelp,
                         BindingResult result) {
        CommonResult commonResult;
        int count = helpService.updateHelp(id, cmsCmsHelp);
        if (count == 1) {
            commonResult = new CommonResult().success(count);
        } else {
            commonResult = new CommonResult().failed();
        }
        return commonResult;
    }

    @ApiOperation(value = "删除${labeltext}")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAuthority('cms:help:delete')")
    public Object delete(@PathVariable("id") Long id) {
        int count = helpService.deleteHelp(id);
        if (count == 1) {
            return new CommonResult().success(null);
        } else {
            return new CommonResult().failed();
        }
    }

    @ApiOperation(value = "根据${labeltext}名称分页获取${labeltext}列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAuthority('cms:help:read')")
    public Object getList(CmsHelp help,
                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                          @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        return new CommonResult().pageSuccess(helpService.listHelp(help, pageNum, pageSize));
    }

    @ApiOperation(value = "根据编号查询${labeltext}信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAuthority('cms:help:read')")
    public Object getItem(@PathVariable("id") Long id) {
        return new CommonResult().success(helpService.getHelp(id));
    }

}
