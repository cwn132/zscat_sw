package com.macro.mall.portal.sms.service.impl;

import com.github.pagehelper.PageHelper;
import com.macro.mall.mapper.OmsOrderMapper;
import com.macro.mall.mapper.PmsProductMapper;
import com.macro.mall.model.OmsOrder;
import com.macro.mall.model.PmsProduct;
import com.macro.mall.model.UmsMember;
import com.macro.mall.model.UmsMemberReceiveAddress;
import com.macro.mall.portal.config.WxAppletProperties;
import com.macro.mall.portal.domain.CommonResult;
import com.macro.mall.portal.domain.ConfirmOrderResult;
import com.macro.mall.portal.service.UmsMemberReceiveAddressService;
import com.macro.mall.portal.service.WechatApiService;
import com.macro.mall.portal.sms.service.GroupService;
import com.macro.mall.portal.sms.vo.GroupAndOrderVo;
import com.macro.mall.portal.util.DateUtils;
import com.macro.mall.portal.util.applet.TemplateData;
import com.macro.mall.portal.util.applet.WX_TemplateMsgUtil;
import com.macro.mall.sms.mapper.GroupMapper;
import com.macro.mall.sms.mapper.GroupMemberMapper;
import com.macro.mall.sms.model.SmsGroup;
import com.macro.mall.sms.model.SmsGroupMember;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品品牌Service实现类
 * Created by macro on 2018/4/26.
 */
@Service
public class GroupServiceImpl implements GroupService {

    @Resource
    private UmsMemberReceiveAddressService memberReceiveAddressService;
    @Resource
    private GroupMapper groupMapper;
    @Resource
    private GroupMemberMapper groupMemberMapper;
    @Resource
    private OmsOrderMapper orderMapper;
    @Resource
    private PmsProductMapper productMapper;

    @Autowired
    private WxAppletProperties wxAppletProperties;

    @Resource
    WechatApiService wechatApiService;

    private static final Logger log = LoggerFactory.getLogger(GroupServiceImpl.class);

    @Transactional
    @Override
    public Object generateOrder(GroupAndOrderVo orderParam,UmsMember member) {
        String type = orderParam.getType();
        orderParam.setMemberId(member.getId());
        orderParam.setName(member.getIcon());
        PmsProduct goods = productMapper.selectByPrimaryKey(orderParam.getGoodsId());

        if (goods.getStock() < 0) {
            return new CommonResult().failed("库存不足，无法下单");
        }
        SmsGroup group = null;
        if ("0".equals(type)) { // 0 下单 1 拼团 2 发起拼团

        }
        if ("1".equals(type)) {
            SmsGroupMember sm = new SmsGroupMember();
            sm.setGroupId(orderParam.getGroupId());
            sm.setMemberId(orderParam.getMemberId());
            List<SmsGroupMember> smsGroupMemberList = groupMemberMapper.list(sm);
            if (smsGroupMemberList!=null && smsGroupMemberList.size()>0){
                return new CommonResult().failed("你已经参加此拼团");
            }
            group = groupMapper.get(orderParam.getGroupId());
            Date endTime = DateUtils.convertStringToDate(DateUtils.addHours(group.getEndTime(), group.getHours()), "yyyy-MM-dd HH:mm:ss");
            Long nowT = System.currentTimeMillis();
            if (nowT > group.getStartTime().getTime() && nowT < endTime.getTime()) {
                if (orderParam.getMemberId() == null || orderParam.getMemberId() < 1) {
                    orderParam.setMemberId(orderParam.getMainId());
                }
                orderParam.setStatus(2);
                groupMemberMapper.save(orderParam);
            } else {
                return new CommonResult().failed("活动已经结束");
            }
        } else if ("2".equals(type)) {
            group = groupMapper.get(orderParam.getGroupId());
            Date endTime = DateUtils.convertStringToDate(DateUtils.addHours(group.getEndTime(), group.getHours()), "yyyy-MM-dd HH:mm:ss");
            Long nowT = System.currentTimeMillis();
            if (nowT > group.getStartTime().getTime() && nowT < endTime.getTime()) {
                if (orderParam.getMemberId() == null || orderParam.getMemberId() < 1) {
                    orderParam.setMemberId(orderParam.getMainId());
                }
                orderParam.setStatus(2);
                orderParam.setMainId(orderParam.getMemberId());
                groupMemberMapper.save(orderParam);
            } else {
                return new CommonResult().failed("活动已经结束");
            }

        }

        //根据商品合计、运费、活动优惠、优惠券、积分计算应付金额
        OmsOrder order = new OmsOrder();
        order.setDiscountAmount(new BigDecimal(0));
        order.setTotalAmount(goods.getPrice());
        order.setFreightAmount(new BigDecimal(0));
        order.setPromotionAmount(new BigDecimal(0));


        order.setCouponAmount(new BigDecimal(0));

        order.setIntegration(0);
        order.setIntegrationAmount(new BigDecimal(0));

        order.setPayAmount(goods.getPrice());
        order.setGoodsId(goods.getId());
        order.setGoodsName(order.getGoodsName());
        //转化为订单信息并插入数据库
        order.setMemberId(orderParam.getMemberId());
        order.setCreateTime(new Date());
        order.setMemberUsername(member.getUsername());
        //支付方式：0->未支付；1->支付宝；2->微信
        order.setPayType(orderParam.getPayType());
        //订单来源：0->PC订单；1->app订单
        order.setSourceType(orderParam.getSourceType());
        //订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单
        order.setStatus(0);
        //订单类型：0->正常订单；1->秒杀订单
        order.setOrderType(orderParam.getOrderType());
        //收货人信息：姓名、电话、邮编、地址
        UmsMemberReceiveAddress address = memberReceiveAddressService.getItem(orderParam.getAddressId());
        order.setReceiverName(address.getName());
        order.setReceiverPhone(address.getPhoneNumber());
        order.setReceiverPostCode(address.getPostCode());
        order.setReceiverProvince(address.getProvince());
        order.setReceiverCity(address.getCity());
        order.setReceiverRegion(address.getRegion());
        order.setReceiverDetailAddress(address.getDetailAddress());
        //0->未确认；1->已确认
        order.setConfirmStatus(0);
        order.setDeleteStatus(0);
        //计算赠送积分
        order.setIntegration(0);
        //计算赠送成长值
        order.setGrowth(0);
        //生成订单号
        order.setOrderSn(generateOrderSn(order));
        // TODO: 2018/9/3 bill_*,delivery_*
        //插入order表和order_item表
        orderMapper.insert(order);

        Map<String, Object> result = new HashMap<>();
        result.put("order", order);

        if (orderParam.getSourceType() == 1) {
            push(orderParam, order, orderParam.getPage(), orderParam.getFormId());
        }
        return new CommonResult().success("下单成功", result);
    }

    @Override
    public Object preOrder(GroupAndOrderVo orderParam) {
        ConfirmOrderResult result = new ConfirmOrderResult();
        result.setGroupAndOrderVo(orderParam);
        PmsProduct goods = productMapper.selectByPrimaryKey(orderParam.getGoodsId());
        result.setGoods(goods);
        //获取用户收货地址列表
        List<UmsMemberReceiveAddress> memberReceiveAddressList = memberReceiveAddressService.list();
        result.setMemberReceiveAddressList(memberReceiveAddressList);
        UmsMemberReceiveAddress address = memberReceiveAddressService.getDefaultItem();

        result.setAddress(address);
        return result;
    }

    /**
     * 推送消息
     */
    public void push(GroupAndOrderVo umsMember, OmsOrder order, String page, String formId) {
        log.info("发送模版消息：userId=" + umsMember.getMemberId() + ",orderId=" + order.getId() + ",formId=" + formId);
        if (StringUtils.isEmpty(formId)) {
            log.error("发送模版消息：userId=" + umsMember.getMemberId() + ",orderId=" + order.getId() + ",formId=" + formId);
        }
        String accessToken = null;
        try {
            accessToken = wechatApiService.getAccessToken();

            String templateId = wxAppletProperties.getTemplateId();
            Map<String, TemplateData> param = new HashMap<String, TemplateData>();
            param.put("keyword1", new TemplateData(DateUtils.format(order.getCreateTime(), "yyyy-MM-dd"), "#EE0000"));

            param.put("keyword2", new TemplateData(order.getGoodsName(), "#EE0000"));
            param.put("keyword3", new TemplateData(order.getOrderSn(), "#EE0000"));
            param.put("keyword3", new TemplateData(order.getPayAmount() + "", "#EE0000"));

            JSONObject jsonObject = JSONObject.fromObject(param);
            //调用发送微信消息给用户的接口    ********这里写自己在微信公众平台拿到的模板ID
            WX_TemplateMsgUtil.sendWechatMsgToUser(umsMember.getWxid(), templateId, page + "?id=" + order.getId(),
                    formId, jsonObject, accessToken);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 生成18位订单编号:8位日期+2位平台号码+2位支付方式+6位以上自增id
     */
    private String generateOrderSn(OmsOrder order) {
        StringBuilder sb = new StringBuilder();
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        sb.append(date);
        sb.append(String.format("%02d", order.getSourceType()));
        sb.append(String.format("%02d", order.getPayType()));
        sb.append(order.getMemberId());
        return sb.toString();
    }

    @Override
    public List<SmsGroupMember> addGroup(SmsGroupMember groupMember) {
        if (groupMember.getMemberId() == null || groupMember.getMemberId() < 1) {
            groupMember.setMemberId(groupMember.getMainId());
        }
        groupMemberMapper.save(groupMember);
        SmsGroupMember newG = new SmsGroupMember();
        newG.setMainId(groupMember.getMainId());
        newG.setGroupId(groupMember.getGroupId());
        List<SmsGroupMember> list = groupMemberMapper.list(newG);
        return list;
    }

    @Override
    public int createGroup(SmsGroup group) {
        return groupMapper.save(group);
    }

    @Override
    public int updateGroup(Long id, SmsGroup group) {
        group.setId(id);
        return groupMapper.update(group);
    }

    @Override
    public int deleteGroup(Long id) {
        return groupMapper.remove(id);
    }


    @Override
    public List<SmsGroup> listGroup(SmsGroup group, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return groupMapper.list(new SmsGroup());

    }

    @Override
    public SmsGroup getGroup(Long id) {
        return groupMapper.get(id);
    }


}
