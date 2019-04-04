package com.macro.mall.mapper;


import com.macro.mall.dto.ExpressInfoView;
import com.macro.mall.model.ExpressInfo;
import com.macro.mall.model.ImportOrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ExpressInfoDao {

    ExpressInfo selectByPrimaryKey(@Param("id") Long id);

	ExpressInfo selectByPrimaryKeyByLock(@Param("id") Long id, @Param("isLock") boolean isLock);

	public int insert(ImportOrderItem item);

	public ExpressInfo selectByExpNo(@Param("expressNo") String expressNo);
	
	public int updateById(ImportOrderItem item);
	
	public int updateByOrder(ImportOrderItem item);
	
	public ExpressInfoView queryExpressInfoViewByNo(String expressNo);
	
	public ExpressInfoView queryExpressInfoViewById(Long id);

	/**
	 * 查询所有非已签收状态的信息
	 * @return
	 */
	public List<ExpressInfo> selectNotRecievedInfo();

	/**
	 * 查询所有需要插入初始状态的信息
	 * @return
	 */
	public List<ExpressInfo> selectNeedInitRecievedInfo(Date startTime);

	int updateByPrimaryKey(ExpressInfo record);
	
}
