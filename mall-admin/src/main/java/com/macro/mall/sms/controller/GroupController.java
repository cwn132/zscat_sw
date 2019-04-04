package com.macro.mall.sms.controller;

import com.macro.mall.dto.CommonResult;
import com.macro.mall.sms.model.SmsGroup;
import com.macro.mall.sms.service.GroupService;
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
 * 
 *https://blog.csdn.net/qq_32795773/article/details/81281515
 * @author zscat
 * @email 951449465@qq.com
 * @date 2019-03-29 14:29:09
 */
@Controller
@Api(tags = "GroupController", description = "管理")
@RequestMapping("/sms/group")
public class GroupController {
    @Resource
    private GroupService groupService;


    @ApiOperation(value = "添加")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('sms:group:create')")
    public Object create(@Validated @RequestBody SmsGroup smsGroup, BindingResult result) {
        CommonResult commonResult;
        int count = groupService.createGroup(smsGroup);
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
    @PreAuthorize("hasAuthority('sms:group:update')")
    public Object update(@PathVariable("id") Long id,
                         @Validated @RequestBody SmsGroup smsGroup,
                         BindingResult result) {
        CommonResult commonResult;
        int count = groupService.updateGroup(id, smsGroup);
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
    @PreAuthorize("hasAuthority('sms:group:delete')")
    public Object delete(@PathVariable("id") Long id) {
        int count = groupService.deleteGroup(id);
        if (count == 1) {
            return new CommonResult().success(null);
        } else {
            return new CommonResult().failed();
        }
    }

    @ApiOperation(value = "根据名称分页获取列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAuthority('sms:group:read')")
    public Object getList(SmsGroup group,
                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                          @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {

        List<SmsGroup> groupList = groupService.listGroup(group, pageNum, pageSize);
        for (SmsGroup smsGroup : groupList){

        }
        return new CommonResult().pageSuccess(groupList);
    }

    @ApiOperation(value = "根据编号查询信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAuthority('sms:group:read')")
    public Object getItem(@PathVariable("id") Long id) {
        return new CommonResult().success(groupService.getGroup(id));
    }

}
