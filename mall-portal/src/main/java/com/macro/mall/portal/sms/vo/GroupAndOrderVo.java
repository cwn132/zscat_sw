package com.macro.mall.portal.sms.vo;


import com.macro.mall.sms.model.SmsGroupMember;
import lombok.Data;

/**
 * @Auther: shenzhuan
 * @Date: 2019/3/29 17:07
 * @Description:
 */
@Data
public class GroupAndOrderVo  extends SmsGroupMember {
    private String type ; // 0 下单 1 拼团 2 发起拼团

    //收货地址id
    private Long addressId;
    //支付方式
    private Integer payType = 1; // 1 微信 2 支付宝

    private  Integer sourceType =1; // 1 小程序  2 h5  3 pc  4 android  5 ios

    private Integer orderType = 1; // 1 普通订单 2拼团订单 3秒杀订单

    private String page;
    private String formId;

    private String wxid;
}
