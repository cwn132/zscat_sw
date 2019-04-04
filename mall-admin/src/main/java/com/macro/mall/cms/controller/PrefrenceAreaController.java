package com.macro.mall.cms.controller;

import com.macro.mall.dto.CommonResult;
import com.macro.mall.model.CmsPrefrenceArea;

import com.macro.mall.cms.service.PrefrenceAreaService;
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
 * 优选专区
 *
 * @author zscat
 * @email 951449465@qq.com
 * @date 2019-03-22 18:08:45
 */
@Controller
@Api(tags = "PrefrenceAreaController", description = "优选专区管理")
@RequestMapping("/cms/prefrenceArea")
public class PrefrenceAreaController {
    @Resource
    private PrefrenceAreaService prefrenceAreaService;


    @ApiOperation(value = "添加优选专区")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('cms:prefrenceArea:create')")
    public Object create(@Validated @RequestBody CmsPrefrenceArea cmsPrefrenceArea, BindingResult result) {
        CommonResult commonResult;
        int count = prefrenceAreaService.createPrefrenceArea(cmsPrefrenceArea);
        if (count == 1) {
            commonResult = new CommonResult().success(count);
        } else {
            commonResult = new CommonResult().failed();
        }
        return commonResult;
    }

    @ApiOperation(value = "更新优选专区")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('cms:prefrenceArea:update')")
    public Object update(@PathVariable("id") Long id,
                         @Validated @RequestBody CmsPrefrenceArea cmsPrefrenceArea,
                         BindingResult result) {
        CommonResult commonResult;
        int count = prefrenceAreaService.updatePrefrenceArea(id, cmsPrefrenceArea);
        if (count == 1) {
            commonResult = new CommonResult().success(count);
        } else {
            commonResult = new CommonResult().failed();
        }
        return commonResult;
    }

    @ApiOperation(value = "删除优选专区")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAuthority('cms:prefrenceArea:delete')")
    public Object delete(@PathVariable("id") Long id) {
        int count = prefrenceAreaService.deletePrefrenceArea(id);
        if (count == 1) {
            return new CommonResult().success(null);
        } else {
            return new CommonResult().failed();
        }
    }

    @ApiOperation(value = "根据优选专区名称分页获取优选专区列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAuthority('cms:prefrenceArea:read')")
    public Object getList(CmsPrefrenceArea prefrenceArea,
                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                          @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        return new CommonResult().pageSuccess(prefrenceAreaService.listPrefrenceArea(prefrenceArea, pageNum, pageSize));
    }

    @ApiOperation(value = "根据编号查询优选专区信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAuthority('cms:prefrenceArea:read')")
    public Object getItem(@PathVariable("id") Long id) {
        return new CommonResult().success(prefrenceAreaService.getPrefrenceArea(id));
    }

}
