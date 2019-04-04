package com.macro.mall.sms.mapper;

import com.macro.mall.sms.model.SmsGroupMember;

import java.util.List;


/**
 * 
 * @author zscat
 * @email 951449465@qq.com
 * @date 2019-03-29 14:29:49
 */

public interface GroupMemberMapper {

	SmsGroupMember get(Long id);
	
	List<SmsGroupMember> list(SmsGroupMember groupMember);
	
	int save(SmsGroupMember groupMember);
	
	int update(SmsGroupMember groupMember);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
