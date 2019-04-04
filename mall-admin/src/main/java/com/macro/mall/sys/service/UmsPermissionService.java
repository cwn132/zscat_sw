package com.macro.mall.sys.service;

import com.macro.mall.bo.Tree;
import com.macro.mall.dto.UmsPermissionNode;
import com.macro.mall.model.UmsPermission;

import java.util.List;
import java.util.Set;

/**
 * 后台用户权限管理Service
 * https://gitee.com/zscat-platform/mall on 2018/9/29.
 */
public interface UmsPermissionService {
    /**
     * 添加权限
     */
    int create(UmsPermission permission);

    /**
     * 修改权限
     */
    int update(Long id, UmsPermission permission);

    /**
     * 批量删除权限
     */
    int delete(List<Long> ids);

    /**
     * 以层级结构返回所有权限
     */
    List<UmsPermissionNode> treeList();

    /**
     * 获取所有权限
     */
    List<UmsPermission> list();

    List<Tree<UmsPermission>> getPermissionsByUserId(Long id);

    Set<String> listPerms(Long userId);

    UmsPermission getItem(Long id);
}
