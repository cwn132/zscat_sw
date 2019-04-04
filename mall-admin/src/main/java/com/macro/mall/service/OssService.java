package com.macro.mall.service;

import com.macro.mall.dto.OssCallbackResult;
import com.macro.mall.dto.OssPolicyResult;

import javax.servlet.http.HttpServletRequest;

/**
 * oss上传管理Service
 * https://gitee.com/zscat-platform/mall on 2018/5/17.
 */
public interface OssService {
    OssPolicyResult policy();

    OssCallbackResult callback(HttpServletRequest request);
}
