package com.macro.mall.portal.controller;

import com.macro.mall.annotation.IgnoreAuth;
import com.macro.mall.model.UmsMemberReceiveAddress;
import com.macro.mall.portal.domain.CommonResult;
import com.macro.mall.portal.service.UmsMemberReceiveAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 会员收货地址管理Controller
 * https://gitee.com/zscat-platform/mall on 2018/8/28.
 */
@Controller
@Api(tags = "UmsMemberReceiveAddressController", description = "会员收货地址管理")
@RequestMapping("/api/address")
public class UmsMemberReceiveAddressController {
    @Autowired
    private UmsMemberReceiveAddressService memberReceiveAddressService;

    @ApiOperation("添加收货地址")
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(@RequestBody UmsMemberReceiveAddress address) {
        int count = memberReceiveAddressService.add(address);
        if (count > 0) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }

    @ApiOperation("删除收货地址")
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam(value = "id", required = false, defaultValue = "0") Long id) {
        int count = memberReceiveAddressService.delete(id);
        if (count > 0) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }

    @ApiOperation("修改收货地址")
    @RequestMapping(value = "/save")
    @ResponseBody
    public Object update(UmsMemberReceiveAddress address) {
        int count = 0 ;
        if (address!=null && address.getId()!=null){
             count = memberReceiveAddressService.update(address.getId(), address);
        }else {
             count = memberReceiveAddressService.add(address);
        }
        if (count > 0) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }

    @IgnoreAuth
    @ApiOperation("显示所有收货地址")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list() {
        List<UmsMemberReceiveAddress> addressList = memberReceiveAddressService.list();
        return new CommonResult().success(addressList);
    }

    @IgnoreAuth
    @ApiOperation("显示所有收货地址")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ResponseBody
    public Object getItem(@RequestParam(value = "id", required = false, defaultValue = "0") Long id) {
        UmsMemberReceiveAddress address = memberReceiveAddressService.getItem(id);
        return new CommonResult().success(address);
    }
    @IgnoreAuth
    @ApiOperation("显示所有收货地址")
    @RequestMapping(value = "/getItemDefautl", method = RequestMethod.GET)
    @ResponseBody
    public Object getItemDefautl() {
        UmsMemberReceiveAddress address = memberReceiveAddressService.getDefaultItem();
        return new CommonResult().success(address);
    }
    /**
     *
     * @param id
     * @return
     */
    @ApiOperation("设为默认地址")
    @RequestMapping(value = "/address-set-default")
    @ResponseBody
    public Object setDefault(@RequestParam(value = "id", required = false, defaultValue = "0") Long id) {
        int count = memberReceiveAddressService.setDefault(id);
        if (count > 0) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }
}
