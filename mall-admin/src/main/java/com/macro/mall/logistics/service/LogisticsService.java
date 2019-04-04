package com.macro.mall.logistics.service;

public interface LogisticsService {
	
	/**
	 * grab_logistics_cron	void logisticsServiceImpl.logisticGrab();	0 20 9 * * ?	0.0.0.0	3	从快递100同步快递信息 每日上午9:20同步一次	0	healthManage	2018-08-27 15:23:58
	 logistisc_ini_cron	void logisticInitService.logisticInit();	0 3/15 8-17 * 1-5 ?	0.0.0.0	10	早8-晚17才插入初始物流信息	0	healthManage	2018-08-27 15:24:03
	 http://xxxx/order/getWayBillInfo.html?orderId="+orderId
	 * 物流抓取存库
	 *
	 *  ImportOrderItem orderItem =new ImportOrderItem(oderId, expressNo, expressCompany.getExpressCorpId());

	 int expressCounter=0;
	 Long expressId = orderDetail.getExpressId();
	 if(expressId!=null){
	 orderItem.setExpressId(expressId);
	 expressCounter = expressInfoDao.updateById(orderItem);
	 }else{
	 expressCounter = expressInfoDao.insert(orderItem);
	 }
	 if(expressCounter!=1){
	 result.setRetcode(CommonConstant.PARAM_ERROR_CODE);
	 result.setRetdesc("已导入"+(i-1)+"条订单运单数据,"+"第"+(i+1)+"行"+"订单编号或运单号列不匹配!");
	 return result;
	 }

	 int orderCounter = orderDao.orderToSended(orderItem.getOrderId(),orderItem.getExpressId());
	 */
	public void logisticGrab();
}
