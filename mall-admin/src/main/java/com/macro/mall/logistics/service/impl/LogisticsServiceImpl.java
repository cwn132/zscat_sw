package com.macro.mall.logistics.service.impl;


import com.macro.mall.logistics.constant.LogisticsConstant;
import com.macro.mall.logistics.constant.LogisticsUtil;
import com.macro.mall.logistics.model.WaybillGrabResult;
import com.macro.mall.logistics.service.LogisticsGrabInterface;
import com.macro.mall.logistics.service.LogisticsService;
import com.macro.mall.mapper.ExpressInfoDao;
import com.macro.mall.model.ExpressInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class LogisticsServiceImpl implements LogisticsService {

	@Resource(name = "kuaidi100WebService")
	private LogisticsGrabInterface kuaidi100Service;
	@Resource(name = "guoguoServiceImpl")
	private LogisticsGrabInterface guoguoService;
	@Resource
	private ExpressInfoDao expressInfoDao;

	protected Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public void logisticGrab() {
		final AtomicInteger totalGrabed = new AtomicInteger(); // 总抓取个数
		final AtomicInteger totalFailed = new AtomicInteger(); // 累计失败个数

		List<ExpressInfo>  expressInfos = expressInfoDao.selectNotRecievedInfo();
		List<Long> failList = new ArrayList<>();//统计失败的列表

		if(expressInfos == null || expressInfos.size() == 0){
			log.info("There is no expressInfos to update");
			return;
		}

		for(ExpressInfo express : expressInfos){
			ExpressInfo e = expressInfoDao.selectByPrimaryKeyByLock(express.getId(), true);
			try {
				WaybillGrabResult result = kuaidi100Service.queryWayBillDetail(e.getExpressCorpId(), e.getExpressNo());
				if (result.getGrabStatus() != LogisticsConstant.GRAB_RESULT_SUCCESS) {
					result = guoguoService.queryWayBillDetail(e.getExpressCorpId(), e.getExpressNo());
				}
				if (result.getGrabStatus() == LogisticsConstant.GRAB_RESULT_SUCCESS) {
					e.setExpressDetail(LogisticsUtil.joinDefaultLogistics(e.getExpressDetail(), result.getData()));
					e.setExpressStatus(result.getLogisticsStatus());
					expressInfoDao.updateByPrimaryKey(e);
					totalGrabed.incrementAndGet();
				}
			} catch (Exception e1) {
				totalFailed.incrementAndGet();
				failList.add(e.getId());
				log.error("query waybill exception.maybe httpClient exception. errorList is " + failList.toString(), e1);
			}
		}
	}
}
