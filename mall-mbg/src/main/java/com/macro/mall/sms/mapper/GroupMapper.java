package com.macro.mall.sms.mapper;

import com.macro.mall.sms.model.SmsGroup;

import java.util.List;


/**
 * 
 * @author zscat
 * @email 951449465@qq.com
 * @date 2019-03-29 14:29:09
 */

public interface GroupMapper {

	SmsGroup get(Long id);
	
	List<SmsGroup> list(SmsGroup group);
	
	int save(SmsGroup group);
	
	int update(SmsGroup group);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);

	SmsGroup getByGoodsId(Long goodsId);
}
