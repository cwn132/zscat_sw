package com.macro.mall.sys.service.impl;

import com.macro.mall.bo.Tree;
import com.macro.mall.dto.UmsPermissionNode;
import com.macro.mall.mapper.UmsPermissionMapper;
import com.macro.mall.model.UmsPermission;
import com.macro.mall.model.UmsPermissionExample;
import com.macro.mall.sys.service.UmsPermissionService;
import com.macro.mall.util.BuildTree;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 后台用户权限管理Service实现类
 * https://gitee.com/zscat-platform/mall on 2018/9/29.
 */
@Service
public class UmsPermissionServiceImpl implements UmsPermissionService {
    @Resource
    private UmsPermissionMapper permissionMapper;

    @Override
    public int create(UmsPermission permission) {
        permission.setStatus(1);
        permission.setCreateTime(new Date());
        permission.setSort(0);
        return permissionMapper.insert(permission);
    }

    @Override
    public int update(Long id, UmsPermission permission) {
        permission.setId(id);
        return permissionMapper.updateByPrimaryKey(permission);
    }

    @Override
    public int delete(List<Long> ids) {
        UmsPermissionExample example = new UmsPermissionExample();
        example.createCriteria().andIdIn(ids);
        return permissionMapper.deleteByExample(example);
    }

    @Override
    public List<UmsPermissionNode> treeList() {
        List<UmsPermission> permissionList = permissionMapper.selectByExample(new UmsPermissionExample());
        List<UmsPermissionNode> result = permissionList.stream()
                .filter(permission -> permission.getPid().equals(0L))
                .map(permission -> covert(permission, permissionList)).collect(Collectors.toList());
        return result;
    }

    @Override
    public List<UmsPermission> list() {
        return permissionMapper.selectByExample(new UmsPermissionExample());
    }

    @Override
    public List<Tree<UmsPermission>> getPermissionsByUserId(Long id) {
        List<Tree<UmsPermission>> trees = new ArrayList<Tree<UmsPermission>>();
        List<UmsPermission> menuDOs = permissionMapper.listMenuByUserId(id);
        for (UmsPermission sysMenuDO : menuDOs) {
            Tree<UmsPermission> tree = new Tree<UmsPermission>();
            tree.setId(sysMenuDO.getId().toString());
            tree.setParentId(sysMenuDO.getPid().toString());
            tree.setTitle(sysMenuDO.getName());
            Map<String, Object> attributes = new HashMap<>(16);
            attributes.put("url", sysMenuDO.getUri());
            attributes.put("icon", sysMenuDO.getIcon());
            tree.setMeta(attributes);
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        List<Tree<UmsPermission>> list = BuildTree.buildList(trees, "0");
        return list;
    }
    @Override
    public Set<String> listPerms(Long userId) {
        List<String> perms = permissionMapper.listUserPerms(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StringUtils.isNotBlank(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }
    /**
     * 将权限转换为带有子级的权限对象
     * 当找不到子级权限的时候map操作不会再递归调用covert
     */
    private UmsPermissionNode covert(UmsPermission permission, List<UmsPermission> permissionList) {
        UmsPermissionNode node = new UmsPermissionNode();
        BeanUtils.copyProperties(permission, node);
        List<UmsPermissionNode> children = permissionList.stream()
                .filter(subPermission -> subPermission.getPid().equals(permission.getId()))
                .map(subPermission -> covert(subPermission, permissionList)).collect(Collectors.toList());
        node.setChildren(children);
        return node;
    }
    @Override
    public UmsPermission getItem(Long id) {
        UmsPermission admin = permissionMapper.selectByPrimaryKey(id);
        return admin;
    }
}
