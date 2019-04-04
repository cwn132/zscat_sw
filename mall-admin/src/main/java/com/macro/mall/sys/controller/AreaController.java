package com.macro.mall.sys.controller;

import com.macro.mall.dto.CommonResult;
import com.macro.mall.model.Area;
import com.macro.mall.sys.service.AreaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 
 *
 * @author zscat
 * @email 951449465@qq.com
 * @date 2019-03-28 16:50:35
 */
@Controller
@Api(tags = "AreaController", description = "管理")
@RequestMapping("/t/area")
public class AreaController {
    @Resource
    private AreaService areaService;


    @ApiOperation(value = "添加")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('t:area:create')")
    public Object create(@Validated @RequestBody Area tArea, BindingResult result) {
        CommonResult commonResult;
        int count = areaService.createArea(tArea);
        if (count == 1) {
            commonResult = new CommonResult().success(count);
        } else {
            commonResult = new CommonResult().failed();
        }
        return commonResult;
    }

    @ApiOperation(value = "更新")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('t:area:update')")
    public Object update(@PathVariable("id") Long id,
                         @Validated @RequestBody Area tArea,
                         BindingResult result) {
        CommonResult commonResult;
        int count = areaService.updateArea(id, tArea);
        if (count == 1) {
            commonResult = new CommonResult().success(count);
        } else {
            commonResult = new CommonResult().failed();
        }
        return commonResult;
    }

    @ApiOperation(value = "删除")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAuthority('t:area:delete')")
    public Object delete(@PathVariable("id") Long id) {
        int count = areaService.deleteArea(id);
        if (count == 1) {
            return new CommonResult().success(null);
        } else {
            return new CommonResult().failed();
        }
    }

    @ApiOperation(value = "根据名称分页获取列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAuthority('t:area:read')")
    public Object getList(Area area,
                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                          @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        return new CommonResult().pageSuccess(areaService.listArea(area, pageNum, pageSize));
    }

    @ApiOperation(value = "根据编号查询信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAuthority('t:area:read')")
    public Object getItem(@PathVariable("id") Long id) {
        return new CommonResult().success(areaService.getArea(id));
    }

}
