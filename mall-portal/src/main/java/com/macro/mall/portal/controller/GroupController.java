package com.macro.mall.portal.controller;

import com.macro.mall.annotation.IgnoreAuth;
import com.macro.mall.dto.PmsProductAndGroup;
import com.macro.mall.model.UmsMember;
import com.macro.mall.portal.domain.CommonResult;
import com.macro.mall.portal.service.PmsProductService;
import com.macro.mall.portal.sms.service.GroupService;
import com.macro.mall.portal.sms.vo.GroupAndOrderVo;
import com.macro.mall.portal.util.DateUtils;
import com.macro.mall.sms.mapper.GroupMapper;
import com.macro.mall.sms.model.SmsGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 订单管理Controller
 * https://gitee.com/zscat-platform/mall on 2018/8/30.
 */
@Slf4j
@RestController
@Api(tags = "GroupController", description = "拼团管理")
@RequestMapping("/api")
public class GroupController extends  ApiBaseAction{

    @Resource
    private GroupService groupService;
    @Resource
    private PmsProductService pmsProductService;
    @Resource
    private GroupMapper groupMapper;

    /**
     * 提交订单
     * @param orderParam
     * @return
     */
    @ApiOperation("商品详情生成订单")
    @RequestMapping(value = "/order/preOrder")
    public Object preOrder(GroupAndOrderVo orderParam) {
        UmsMember member = this.getCurrentMember();
        orderParam.setMemberId(member.getId());
        orderParam.setName(member.getNickname());
        return groupService.preOrder(orderParam);
    }
    /**
     * 提交订单
     * @param orderParam
     * @return
     */
    @ApiOperation("商品详情生成订单")
    @RequestMapping(value = "/order/bookOrder")
    public Object bookOrder(GroupAndOrderVo orderParam) {
        UmsMember member = this.getCurrentMember();
        return groupService.generateOrder(orderParam,member);
    }



    @IgnoreAuth
    @GetMapping(value = "/goods/detail")
    @ApiOperation(value = "查询商品详情信息")
    public Object queryProductDetail(@RequestParam(value = "id", required = false, defaultValue = "0") Long id) {
        PmsProductAndGroup productResult = pmsProductService.get(id);
        Map<String ,Object> map = new HashMap<>();
        SmsGroup group = groupMapper.getByGoodsId(id);
        if (group!=null){
            Date endTime = DateUtils.convertStringToDate(DateUtils.addHours(group.getEndTime(),group.getHours()),"yyyy-MM-dd HH:mm:ss");
            Long nowT = System.currentTimeMillis();
            if (group!=null && nowT>group.getStartTime().getTime() && nowT<endTime.getTime()){
                map.put("group",group);
                map.put("isGroup",1);
            }else{
                map.put("isGroup",2);
            }
        }

        map.put("goods",productResult);
        return new CommonResult().success(map);
    }

}
