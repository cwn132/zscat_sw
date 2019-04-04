package com.macro.mall.portal.service;

import com.macro.mall.model.UmsMemberLevel;

import java.util.List;

/**
 * 会员等级管理Service
 * https://gitee.com/zscat-platform/mall on 2018/4/26.
 */
public interface UmsMemberLevelService {
    /**
     * 获取所有会员登录
     *
     * @param defaultStatus 是否为默认会员
     */
    List<UmsMemberLevel> list(Integer defaultStatus);
}
