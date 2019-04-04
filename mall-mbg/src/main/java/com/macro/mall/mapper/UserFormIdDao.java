/**
 * 
 */
package com.macro.mall.mapper;

import com.macro.mall.model.UserFormId;
import org.apache.ibatis.annotations.Param;

/**
 * 营销模块 - 市场推送Dao接口定义
 * @author yang.liu
 */
public interface UserFormIdDao {

	/**
	 * 增加市场推送FormId信息
	 * @param entity
	 */
	public void insert(UserFormId entity);
	
	/**
	 * 根据传入的formId，返回记录条数，用于判定其是否已经存在
	 * @param formId
	 * @return
	 */
	public int exists(@Param("formId") String formId);
}
