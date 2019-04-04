package com.macro.mall.ums.controller;

import com.macro.mall.dto.CommonResult;
import com.macro.mall.model.UmsMember;
import com.macro.mall.ums.service.UmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 会员功能Controller
 * https://gitee.com/zscat-platform/mall on 2018/4/26.
 */
@Controller
@Api(tags = "UmsMemberController", description = "会员管理")
@RequestMapping("/member")
public class UmsMemberController {
    @Autowired
    private UmsMemberService umsMemberService;
    

    @ApiOperation(value = "添加会员")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('pms:member:create')")
    public Object create(@Validated @RequestBody UmsMember pmsBrand, BindingResult result) {
        CommonResult commonResult;
        int count = umsMemberService.createMember(pmsBrand);
        if (count == 1) {
            commonResult = new CommonResult().success(count);
        } else {
            commonResult = new CommonResult().failed();
        }
        return commonResult;
    }

    @ApiOperation(value = "更新会员")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('pms:member:update')")
    public Object update(@PathVariable("id") Long id,
                         @Validated @RequestBody UmsMember pmsBrandParam,
                         BindingResult result) {
        CommonResult commonResult;
        int count = umsMemberService.updateMember(id, pmsBrandParam);
        if (count == 1) {
            commonResult = new CommonResult().success(count);
        } else {
            commonResult = new CommonResult().failed();
        }
        return commonResult;
    }

    @ApiOperation(value = "删除会员")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAuthority('pms:member:delete')")
    public Object delete(@PathVariable("id") Long id) {
        int count = umsMemberService.deleteMember(id);
        if (count == 1) {
            return new CommonResult().success(null);
        } else {
            return new CommonResult().failed();
        }
    }

    @ApiOperation(value = "根据会员名称分页获取会员列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
  //  @PreAuthorize("hasAuthority('pms:member:read')")
    public Object getList(@RequestParam(value = "keyword", required = false) String keyword,
                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                          @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        return new CommonResult().pageSuccess(umsMemberService.listMember(keyword, pageNum, pageSize));
    }

    @ApiOperation(value = "根据编号查询会员信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAuthority('pms:member:read')")
    public Object getItem(@PathVariable("id") Long id) {
        return new CommonResult().success(umsMemberService.getMember(id));
    }

    @ApiOperation(value = "批量删除会员")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('pms:member:delete')")
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        int count = umsMemberService.deleteMember(ids);
        if (count > 0) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }


}
