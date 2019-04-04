package com.macro.mall.sys.service.impl;

import com.macro.mall.mapper.UmsRolePermissionRelationDao;
import com.macro.mall.mapper.UmsRoleMapper;
import com.macro.mall.mapper.UmsRolePermissionRelationMapper;
import com.macro.mall.model.*;
import com.macro.mall.sys.service.UmsRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 后台角色管理Service实现类
 * https://gitee.com/zscat-platform/mall on 2018/9/30.
 */
@Service
public class UmsRoleServiceImpl implements UmsRoleService {
    @Autowired
    private UmsRoleMapper roleMapper;
    @Autowired
    private UmsRolePermissionRelationMapper rolePermissionRelationMapper;
    @Autowired
    private UmsRolePermissionRelationDao rolePermissionRelationDao;

    @Override
    public int create(UmsRole role) {
        role.setCreateTime(new Date());
        role.setStatus(1);
        role.setAdminCount(0);
        role.setSort(0);
         roleMapper.insert(role);
        updatePermission(role.getId(),role.getMenuIds());
        return 1;
    }

    @Override
    public int update(Long id, UmsRole role) {
        role.setId(id);
        updatePermission(id,role.getMenuIds());
        return roleMapper.updateByPrimaryKey(role);
    }

    @Override
    public int delete(List<Long> ids) {
        UmsRoleExample example = new UmsRoleExample();
        example.createCriteria().andIdIn(ids);
        return roleMapper.deleteByExample(example);
    }

    @Override
    public List<UmsPermission> getPermissionList(Long roleId) {
        return rolePermissionRelationDao.getPermissionList(roleId);
    }

    public void updatePermission(Long roleId, String permissionIds) {
        //先删除原有关系
        UmsRolePermissionRelationExample example = new UmsRolePermissionRelationExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        rolePermissionRelationMapper.deleteByExample(example);
        //批量插入新关系
        List<UmsRolePermissionRelation> relationList = new ArrayList<>();
        if (!StringUtils.isEmpty(permissionIds)){
            String[] mids = permissionIds.split(",");
            for (String permissionId : mids) {
                UmsRolePermissionRelation relation = new UmsRolePermissionRelation();
                relation.setRoleId(roleId);
                relation.setPermissionId(Long.valueOf(permissionId));
                relationList.add(relation);
            }
            rolePermissionRelationDao.insertList(relationList);
        }

    }

    @Override
    public int updatePermission(Long roleId, List<Long> permissionIds) {
        //先删除原有关系
        UmsRolePermissionRelationExample example = new UmsRolePermissionRelationExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        rolePermissionRelationMapper.deleteByExample(example);
        //批量插入新关系
        List<UmsRolePermissionRelation> relationList = new ArrayList<>();
        for (Long permissionId : permissionIds) {
            UmsRolePermissionRelation relation = new UmsRolePermissionRelation();
            relation.setRoleId(roleId);
            relation.setPermissionId(permissionId);
            relationList.add(relation);
        }
        return rolePermissionRelationDao.insertList(relationList);
    }

    @Override
    public List<UmsRole> list() {
        return roleMapper.selectByExample(new UmsRoleExample());
    }

    @Override
    public UmsRole getItem(Long id) {
        UmsRole admin = roleMapper.selectByPrimaryKey(id);
        return admin;
    }

    @Override
    public List<UmsRolePermissionRelation> getRolePermission(Long roleId) {
        return rolePermissionRelationDao.getRolePermissionList(roleId);
    }
}
