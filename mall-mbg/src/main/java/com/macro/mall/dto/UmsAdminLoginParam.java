package com.macro.mall.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * 用户登录参数
 * https://gitee.com/zscat-platform/mall on 2018/4/26.
 */
public class UmsAdminLoginParam {
    @ApiModelProperty(value = "用户名", required = true)
    private String username;
    @ApiModelProperty(value = "密码", required = true)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
