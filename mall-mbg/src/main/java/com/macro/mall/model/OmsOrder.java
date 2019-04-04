package com.macro.mall.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OmsOrder implements Serializable {
    /**
     * 订单id
     *
     * @mbggenerated
     */
    private Long id;

    private Long supplyId;

    private Long memberId;

    private Long couponId;

    private Long goodsId;
    private String goodsName;
    List<OmsOrderItem> orderItemList ;
    /**
     * 订单编号
     *
     * @mbggenerated
     */
    private String orderSn;

    /**
     * 提交时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 用户帐号
     *
     * @mbggenerated
     */
    private String memberUsername;

    /**
     * 订单总金额
     *
     * @mbggenerated
     */
    private BigDecimal totalAmount;

    /**
     * 应付金额（实际支付金额）
     *
     * @mbggenerated
     */
    private BigDecimal payAmount;

    /**
     * 运费金额
     *
     * @mbggenerated
     */
    private BigDecimal freightAmount;

    /**
     * 促销优化金额（促销价、满减、阶梯价）
     *
     * @mbggenerated
     */
    private BigDecimal promotionAmount;

    /**
     * 积分抵扣金额
     *
     * @mbggenerated
     */
    private BigDecimal integrationAmount;

    /**
     * 优惠券抵扣金额
     *
     * @mbggenerated
     */
    private BigDecimal couponAmount;

    /**
     * 管理员后台调整订单使用的折扣金额
     *
     * @mbggenerated
     */
    private BigDecimal discountAmount;

    /**
     * 支付方式：0->未支付；1->支付宝；2->微信 3 qq
     *
     * @mbggenerated
     */
    private Integer payType;

    /**
     * 订单来源：0->PC订单；1->app订单
     *
     * @mbggenerated
     */
    private Integer sourceType;

    /**
     * 订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 订单类型：0->正常订单；1->秒杀订单
     *
     * @mbggenerated
     */
    private Integer orderType;

    /**
     * 物流公司(配送方式)
     *
     * @mbggenerated
     */
    private String deliveryCompany;
    private String prepayId;

    /**
     * 物流单号
     *
     * @mbggenerated
     */
    private String deliverySn;

    /**
     * 自动确认时间（天）
     *
     * @mbggenerated
     */
    private Integer autoConfirmDay;

    /**
     * 可以获得的积分
     *
     * @mbggenerated
     */
    private Integer integration;

    /**
     * 可以活动的成长值
     *
     * @mbggenerated
     */
    private Integer growth;

    /**
     * 活动信息
     *
     * @mbggenerated
     */
    private String promotionInfo;

    /**
     * 发票类型：0->不开发票；1->电子发票；2->纸质发票
     *
     * @mbggenerated
     */
    private Integer billType;

    /**
     * 发票抬头
     *
     * @mbggenerated
     */
    private String billHeader;

    /**
     * 发票内容
     *
     * @mbggenerated
     */
    private String billContent;

    /**
     * 收票人电话
     *
     * @mbggenerated
     */
    private String billReceiverPhone;

    /**
     * 收票人邮箱
     *
     * @mbggenerated
     */
    private String billReceiverEmail;

    /**
     * 收货人姓名
     *
     * @mbggenerated
     */
    private String receiverName;

    /**
     * 收货人电话
     *
     * @mbggenerated
     */
    private String receiverPhone;

    /**
     * 收货人邮编
     *
     * @mbggenerated
     */
    private String receiverPostCode;

    /**
     * 省份/直辖市
     *
     * @mbggenerated
     */
    private String receiverProvince;

    /**
     * 城市
     *
     * @mbggenerated
     */
    private String receiverCity;

    /**
     * 区
     *
     * @mbggenerated
     */
    private String receiverRegion;

    /**
     * 详细地址
     *
     * @mbggenerated
     */
    private String receiverDetailAddress;

    /**
     * 订单备注
     *
     * @mbggenerated
     */
    private String note;

    /**
     * 确认收货状态：0->未确认；1->已确认
     *
     * @mbggenerated
     */
    private Integer confirmStatus;

    /**
     * 删除状态：0->未删除；1->已删除
     *
     * @mbggenerated
     */
    private Integer deleteStatus;

    /**
     * 下单时使用的积分
     *
     * @mbggenerated
     */
    private Integer useIntegration;

    /**
     * 支付时间
     *
     * @mbggenerated
     */
    private Date paymentTime;

    /**
     * 发货时间
     *
     * @mbggenerated
     */
    private Date deliveryTime;

    /**
     * 确认收货时间
     *
     * @mbggenerated
     */
    private Date receiveTime;

    /**
     * 评价时间
     *
     * @mbggenerated
     */
    private Date commentTime;

    /**
     * 修改时间
     *
     * @mbggenerated
     */
    private Date modifyTime;

    private static final long serialVersionUID = 1L;

}