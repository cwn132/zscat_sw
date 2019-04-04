package com.macro.mall.logistics.service;


import com.macro.mall.logistics.constant.LogisticsUtil;
import com.macro.mall.mapper.ExpressInfoDao;
import com.macro.mall.mapper.OmsOrderMapper;
import com.macro.mall.model.ExpressInfo;
import com.macro.mall.model.OmsOrder;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LogisticInitService{

	@Resource
	private ExpressInfoDao expressInfoDao;
	
	@Resource
	private OmsOrderMapper orderDao;
	

	
	protected Logger log = LoggerFactory.getLogger(getClass());
	
	public void logisticInit() {
		SimpleDateFormat format = new SimpleDateFormat("HH");
		Calendar time = Calendar.getInstance();
		//早8-晚17才插入初始物流信息
		if(Integer.valueOf(format.format(time.getTime()))<8 || Integer.valueOf(format.format(time.getTime()))>19){
			log.info("There is not in update time");
			return;
		}
		//需要插入初始化物流信息列表
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -5);
		List<ExpressInfo>  expressInfos = expressInfoDao.selectNeedInitRecievedInfo(cal.getTime());
		if(expressInfos == null || expressInfos.size() == 0){
			log.info("There is no expressInfos to update");
			return;
		}
		for(ExpressInfo exp : expressInfos){
			ExpressInfo express = expressInfoDao.selectByPrimaryKeyByLock(exp.getId(),true);
			int random = RandomUtils.nextInt(60);
			Calendar start = Calendar.getInstance();
			start.add(Calendar.MINUTE, -(random+30));
			//两次物流信息更新间隔在当前时间1个半小时之间的，或者超过90分钟的
			if(start.getTime().after(express.getUpdateTime())){
				OmsOrder order = orderDao.queryOrderByExpressNo(express.getExpressNo(), false);
				if(order != null){
				//	SupplierWareHouse house = supplierWareHouseDao.selectSupplierWareHouseByPk(order.getWarehouseId());
					//if(house!=null){
						//当前应该插入第几次
						int count = LogisticsUtil.getMapkey(express.getExpressDetail());
						String detail = "";
						/*if(house.getType()== GoodsType.BONDED_CROSS_BORDER.getValue()){
							//保税跨境只插入5次信息
							if(count>5){
								continue;
							}
							detail = LogisticsUtil.getBondedDetailMap().get(count+"");
						}
						if(house.getType()==GoodsType.TRADE_DOOGS.getValue()){*/
							//非跨境大贸货只插入4次信息
							if(count>4){
								continue;
							}
							detail = LogisticsUtil.getTradeDetailMap().get(count+"");
						//}
						//随机插入时间（当前时间前1-5分钟）
						int insertTime = RandomUtils.nextInt(5);
						//{"time":"2016-06-02 13:00:06","context":"客户 签收人: 邮件收发章 已签收  感谢使用圆通速递，期待再次为您服务","ftime":"2016-06-02 13:00:06"}
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						time.add(Calendar.MINUTE,-insertTime);
						Map<String, String> map = new HashMap<String, String>();
						map.put("time",sdf.format(time.getTime()));
						map.put("ftime",sdf.format(time.getTime()));
						map.put("source","system");
						map.put("context", detail);
						express.setExpressDetail(LogisticsUtil.joinDefaultLogistics(express.getExpressDetail(),map));
						expressInfoDao.updateByPrimaryKey(express);
				//	}
				}
			}
		}
	}
	
}
