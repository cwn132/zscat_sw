package com.macro.mall.mapper;

import com.macro.mall.model.UmsPermission;
import com.macro.mall.model.UmsPermissionExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UmsPermissionMapper {
    int countByExample(UmsPermissionExample example);

    int deleteByExample(UmsPermissionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UmsPermission record);

    int insertSelective(UmsPermission record);

    List<UmsPermission> selectByExample(UmsPermissionExample example);

    UmsPermission selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UmsPermission record, @Param("example") UmsPermissionExample example);

    int updateByExample(@Param("record") UmsPermission record, @Param("example") UmsPermissionExample example);

    int updateByPrimaryKeySelective(UmsPermission record);

    int updateByPrimaryKey(UmsPermission record);


    List<UmsPermission> listMenuByUserId(Long id);

    List<String> listUserPerms(Long id);
}