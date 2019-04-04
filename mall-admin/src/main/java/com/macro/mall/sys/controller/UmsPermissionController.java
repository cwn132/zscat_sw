package com.macro.mall.sys.controller;

import com.macro.mall.dto.CommonResult;
import com.macro.mall.dto.UmsPermissionNode;
import com.macro.mall.model.UmsPermission;
import com.macro.mall.sys.service.UmsPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 后台用户权限管理
 * https://gitee.com/zscat-platform/mall on 2018/9/29.
 */
@Controller
@Api(tags = "UmsPermissionController", description = "后台用户权限管理")
@RequestMapping("/permission")
public class UmsPermissionController  extends BaseController{
    @Autowired
    private UmsPermissionService permissionService;

    @ApiOperation("添加权限")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Object create(@RequestBody UmsPermission permission) {
        int count = permissionService.create(permission);
        if (count > 0) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }

    @ApiOperation("修改权限")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable Long id, @RequestBody UmsPermission permission) {
        int count = permissionService.update(id, permission);
        if (count > 0) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }

    @ApiOperation("获取指定用户信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object getItem(@PathVariable Long id) {
        UmsPermission admin = permissionService.getItem(id);
        return new CommonResult().success(admin);
    }

    @ApiOperation("根据id批量删除权限")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public Object delete(@RequestParam("ids") List<Long> ids) {
        int count = permissionService.delete(ids);
        if (count > 0) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }

    @ApiOperation("以层级结构返回所有权限")
    @RequestMapping(value = "/treeList", method = RequestMethod.GET)
    @ResponseBody
    public Object treeList() {
        List<UmsPermissionNode> permissionNodeList = permissionService.treeList();
        return new CommonResult().success(permissionNodeList);
    }

    @ApiOperation("获取所有权限列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list() {
        List<UmsPermission> permissionList = permissionService.list();
        return new CommonResult().success(permissionList);
    }
    @ApiOperation("获取所有权限列表")
    @RequestMapping(value = "/findPermissions", method = RequestMethod.GET)
    @ResponseBody
    public Object findPermissions() {
        return new CommonResult().success(permissionService.getPermissionsByUserId(getCurrentUser().getId()));
    }
}
