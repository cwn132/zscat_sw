package com.macro.mall.portal.service.impl;

import com.github.pagehelper.PageHelper;
import com.macro.mall.dto.*;
import com.macro.mall.exception.BusinessException;
import com.macro.mall.mapper.*;
import com.macro.mall.model.*;
import com.macro.mall.portal.service.OmsOrderService;
import com.macro.mall.portal.util.DateUtil;
import com.macro.mall.portal.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 订单管理Service实现类
 * https://gitee.com/zscat-platform/mall on 2018/10/11.
 */
@Service
@Slf4j
public class OmsOrderServiceImpl implements OmsOrderService {
    @Resource
    private OmsOrderMapper orderMapper;
    @Resource
    private OmsOrderDao orderDao;
    @Resource
    private OmsOrderOperateHistoryDao orderOperateHistoryDao;
    @Resource
    private OmsOrderOperateHistoryMapper orderOperateHistoryMapper;

    @Resource
    private ExpressInfoDao expressInfoDao;
    @Resource
    private ExpressCompanyDao expressCompanyDao;


    @Override
    public ExpressInfoModel queryExpressInfo(Long orderId) throws Exception {
        OmsOrder order = orderMapper.selectByPrimaryKey(orderId);
        if(order == null){
            throw new BusinessException("order is not exist. orderId = " + orderId);
        }
        ExpressInfoModel expressInfoModel = new ExpressInfoModel();
        ExpressInfo expressInfo = expressInfoDao.selectByExpNo(order.getDeliverySn());
        if(expressInfo == null){
            log.info("expressInfo is not exist. orderId = " + orderId);
            List<WayBillItem> expressDetail = new ArrayList<>();
            WayBillItem wayBillItem = new WayBillItem();
            wayBillItem.setContext("暂无物流信息");
            expressDetail.add(wayBillItem);
            expressInfoModel.setExpressDetail(expressDetail);
            return expressInfoModel;
        }
        ExpressCompany expressCompany = expressCompanyDao.selectByExpressCorpId(expressInfo.getExpressCorpId());

        expressInfoModel.setExpressCorpId(expressInfo.getExpressCorpId());
        String expressInfoJson = expressInfo.getExpressDetail();
        if(expressInfoJson == null || "".equals(expressInfoJson)){
            List<WayBillItem> expressDetail = new ArrayList<>();
            WayBillItem wayBillItem = new WayBillItem();
            wayBillItem.setContext("暂无物流信息");
            expressDetail.add(wayBillItem);
            expressInfoModel.setExpressDetail(expressDetail);
        } else {
            List<WayBillItem> wayBillItems = JsonUtils.json2list(expressInfoJson, WayBillItem.class);

            expressInfoModel.setExpressDetail(wayBillItems);

            Collections.sort(expressInfoModel.getExpressDetail(), new Comparator<WayBillItem>() {

                @Override
                public int compare(WayBillItem w1, WayBillItem w2) {
                    try {
                        Date d1 = DateUtil.strToDate(w1.getTime(), "yyyy-MM-dd HH:mm:ss");
                        Date d2 = DateUtil.strToDate(w2.getTime(), "yyyy-MM-dd HH:mm:ss");

                        return -1 * d1.compareTo(d2);
                    } catch (Exception e) {
                        throw new IllegalArgumentException("转换物流详情中的日期出错！" + e.getMessage());
                    }
                }
            });
        }
        expressInfoModel.setExpressNo(expressInfo.getExpressNo());
        expressInfoModel.setExpressStatus(expressInfo.getExpressStatus());
        expressInfoModel.setId(expressInfo.getId());
        expressInfoModel.setOrderId(order.getId());
        expressInfoModel.setUpdateTime(DateUtil.dateToStr(expressInfo.getUpdateTime(), "yyyy-MM-dd HH:mm:ss"));
        expressInfoModel.setExpressCorpName(expressCompany==null?"":expressCompany.getExpressCorpName());
        return expressInfoModel;
    }
    @Override
    public List<OmsOrder> list(OmsOrderQueryParam queryParam, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        return orderDao.getList(queryParam);
    }

    @Override
    public int delivery(List<OmsOrderDeliveryParam> deliveryParamList) {
        //批量发货
        int count = orderDao.delivery(deliveryParamList);
        //添加操作记录
        List<OmsOrderOperateHistory> operateHistoryList = deliveryParamList.stream()
                .map(omsOrderDeliveryParam -> {
                    OmsOrderOperateHistory history = new OmsOrderOperateHistory();
                    history.setOrderId(omsOrderDeliveryParam.getOrderId());
                    history.setCreateTime(new Date());
                    history.setOperateMan("后台管理员");
                    history.setOrderStatus(2);
                    history.setNote("完成发货");
                    return history;
                }).collect(Collectors.toList());
        orderOperateHistoryDao.insertList(operateHistoryList);
        return count;
    }

    @Override
    public int close(List<Long> ids, String note) {
        OmsOrder record = new OmsOrder();
        record.setStatus(4);
        OmsOrderExample example = new OmsOrderExample();
        example.createCriteria().andDeleteStatusEqualTo(0).andIdIn(ids);
        int count = orderMapper.updateByExampleSelective(record, example);
        List<OmsOrderOperateHistory> historyList = ids.stream().map(orderId -> {
            OmsOrderOperateHistory history = new OmsOrderOperateHistory();
            history.setOrderId(orderId);
            history.setCreateTime(new Date());
            history.setOperateMan("后台管理员");
            history.setOrderStatus(4);
            history.setNote("订单关闭:" + note);
            return history;
        }).collect(Collectors.toList());
        orderOperateHistoryDao.insertList(historyList);
        return count;
    }

    @Override
    public int delete(List<Long> ids) {
        OmsOrder record = new OmsOrder();
        record.setDeleteStatus(1);
        OmsOrderExample example = new OmsOrderExample();
        example.createCriteria().andDeleteStatusEqualTo(0).andIdIn(ids);
        return orderMapper.updateByExampleSelective(record, example);
    }

    @Override
    public OmsOrderDetail detail(Long id) {
        return orderDao.getDetail(id);
    }

    @Override
    public int updateReceiverInfo(OmsReceiverInfoParam receiverInfoParam) {
        OmsOrder order = new OmsOrder();
        order.setId(receiverInfoParam.getOrderId());
        order.setReceiverName(receiverInfoParam.getReceiverName());
        order.setReceiverPhone(receiverInfoParam.getReceiverPhone());
        order.setReceiverPostCode(receiverInfoParam.getReceiverPostCode());
        order.setReceiverDetailAddress(receiverInfoParam.getReceiverDetailAddress());
        order.setReceiverProvince(receiverInfoParam.getReceiverProvince());
        order.setReceiverCity(receiverInfoParam.getReceiverCity());
        order.setReceiverRegion(receiverInfoParam.getReceiverRegion());
        order.setModifyTime(new Date());
        int count = orderMapper.updateByPrimaryKeySelective(order);
        //插入操作记录
        OmsOrderOperateHistory history = new OmsOrderOperateHistory();
        history.setOrderId(receiverInfoParam.getOrderId());
        history.setCreateTime(new Date());
        history.setOperateMan("后台管理员");
        history.setOrderStatus(receiverInfoParam.getStatus());
        history.setNote("修改收货人信息");
        orderOperateHistoryMapper.insert(history);
        return count;
    }

    @Override
    public int updateMoneyInfo(OmsMoneyInfoParam moneyInfoParam) {
        OmsOrder order = new OmsOrder();
        order.setId(moneyInfoParam.getOrderId());
        order.setFreightAmount(moneyInfoParam.getFreightAmount());
        order.setDiscountAmount(moneyInfoParam.getDiscountAmount());
        order.setModifyTime(new Date());
        int count = orderMapper.updateByPrimaryKeySelective(order);
        //插入操作记录
        OmsOrderOperateHistory history = new OmsOrderOperateHistory();
        history.setOrderId(moneyInfoParam.getOrderId());
        history.setCreateTime(new Date());
        history.setOperateMan("后台管理员");
        history.setOrderStatus(moneyInfoParam.getStatus());
        history.setNote("修改费用信息");
        orderOperateHistoryMapper.insert(history);
        return count;
    }

    @Override
    public int updateNote(Long id, String note, Integer status) {
        OmsOrder order = new OmsOrder();
        order.setId(id);
        order.setNote(note);
        order.setModifyTime(new Date());
        int count = orderMapper.updateByPrimaryKeySelective(order);
        OmsOrderOperateHistory history = new OmsOrderOperateHistory();
        history.setOrderId(id);
        history.setCreateTime(new Date());
        history.setOperateMan("后台管理员");
        history.setOrderStatus(status);
        history.setNote("修改备注信息：" + note);
        orderOperateHistoryMapper.insert(history);
        return count;
    }
    @Override
    public int updateAll(OmsOrder omsOrder){

        return orderMapper.updateByPrimaryKeySelective(omsOrder);
    }



}
