package com.macro.mall.portal.controller;

import com.macro.mall.dto.OmsOrderDetail;
import com.macro.mall.dto.OmsOrderQueryParam;
import com.macro.mall.mapper.OmsOrderItemMapper;
import com.macro.mall.model.*;
import com.macro.mall.portal.constant.RedisKey;
import com.macro.mall.portal.domain.CommonResult;
import com.macro.mall.portal.domain.ConfirmOrderResult;
import com.macro.mall.portal.domain.OrderParam;
import com.macro.mall.portal.service.OmsOrderService;
import com.macro.mall.portal.service.OmsPortalOrderService;
import com.macro.mall.portal.service.RedisService;
import com.macro.mall.portal.service.UmsMemberService;
import com.macro.mall.portal.util.JsonUtil;
import com.macro.mall.portal.vo.TbThanks;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 订单管理Controller
 * https://gitee.com/zscat-platform/mall on 2018/8/30.
 */
@Slf4j
@Controller
@Api(tags = "OmsPortalOrderController", description = "订单管理")
@RequestMapping("/api/order")
public class OmsPortalOrderController extends  ApiBaseAction{
    @Autowired
    private OmsPortalOrderService portalOrderService;
    @Autowired
    private OmsOrderService orderService;
    @Autowired
    private UmsMemberService umsMemberService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private OmsOrderItemMapper orderItemMapper;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(OmsOrderQueryParam queryParam,
                       @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                       @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        queryParam.setMemberId(umsMemberService.getCurrentMember().getId());
        List<OmsOrder> orderList = orderService.list(queryParam, pageSize, pageNum);
        for (OmsOrder order : orderList){
            OmsOrderItemExample orderItemExample = new OmsOrderItemExample();
            orderItemExample.createCriteria().andOrderIdEqualTo(order.getId());
            List<OmsOrderItem> orderItemList = orderItemMapper.selectByExample(orderItemExample);
            order.setOrderItemList(orderItemList);
        }
        return new com.macro.mall.dto.CommonResult().pageSuccess(orderList);
    }

    @ApiOperation("获取订单详情:订单信息、商品信息、操作记录")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ResponseBody
    public Object detail(@RequestParam(value = "id", required = false, defaultValue = "0") Long id) {
        OmsOrderDetail orderDetailResult = null;
        String bannerJson = redisService.get(RedisKey.PmsProductResult+id);
        if(bannerJson!=null){
            orderDetailResult = JsonUtil.jsonToPojo(bannerJson,OmsOrderDetail.class);
        }else {
            orderDetailResult = orderService.detail(id);
            redisService.set(RedisKey.PmsProductResult+id,JsonUtil.objectToJson(orderDetailResult));
            redisService.expire(RedisKey.PmsProductResult+id,10*60);
        }

        return new com.macro.mall.dto.CommonResult().success(orderDetailResult);
    }

    /**
     *
     * @return
     */
    @Deprecated
    @ApiOperation("根据购物车信息生成确认单信息")
    @RequestMapping(value = "/generateConfirmOrder", method = RequestMethod.POST)
    @ResponseBody
    public Object generateConfirmOrder() {
        ConfirmOrderResult confirmOrderResult = portalOrderService.generateConfirmOrder();
        return new CommonResult().success(confirmOrderResult);
    }


    @ResponseBody
    @GetMapping("/submitPreview")
    public Object submitPreview(OrderParam orderParam){
        try {
            ConfirmOrderResult result = portalOrderService.submitPreview(orderParam);
            return new CommonResult().success(result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 提交订单
     * @param orderParam
     * @return
     */
    @ApiOperation("根据购物车信息生成订单")
    @RequestMapping(value = "/generateOrder")
    @ResponseBody
    public Object generateOrder(OrderParam orderParam) {
        return portalOrderService.generateOrder(orderParam);
    }


    @RequestMapping(value = "/payOrder")
    @ApiOperation(value = "支付订单")
    @ResponseBody
    public Object payOrder(TbThanks tbThanks){
        int result=portalOrderService.payOrder(tbThanks);
        return new CommonResult().success(result);
    }

    @ApiOperation("自动取消超时订单")
    @RequestMapping(value = "/cancelTimeOutOrder", method = RequestMethod.POST)
    @ResponseBody
    public Object cancelTimeOutOrder() {
        return portalOrderService.cancelTimeOutOrder();
    }

    @ApiOperation("取消单个超时订单")
    @RequestMapping(value = "/cancelOrder", method = RequestMethod.POST)
    @ResponseBody
    public Object cancelOrder(Long orderId) {
        portalOrderService.sendDelayMessageCancelOrder(orderId);
        return new CommonResult().success(null);
    }

    /**
     * 查看物流
     */
    @ApiOperation("查看物流")
    @ResponseBody
    @RequestMapping("/getWayBillInfo")
    public Object getWayBillInfo(@RequestParam(value = "orderId", required = false, defaultValue = "0") Long orderId) throws Exception {
        try {
            UmsMember member = this.getCurrentMember();
            OmsOrder order = orderService.detail(orderId);
            if(order==null){
               return null;
            }
            if (!order.getMemberId().equals(member.getId())) {
                return new CommonResult().success("非当前用户订单");
            }

            ExpressInfoModel expressInfoModel = orderService.queryExpressInfo(orderId);
            return new CommonResult().success(expressInfoModel);
        } catch (Exception e) {
            log.error("get waybillInfo error. error=" + e.getMessage(), e);
            return new CommonResult().failed("获取物流信息失败，请稍后重试");
        }

    }
}
