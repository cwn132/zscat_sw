package com.macro.mall.dto;

import com.macro.mall.model.UmsAdmin;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户登录参数
 * https://gitee.com/zscat-platform/mall on 2018/4/26.
 */
@Getter
@Setter
public class UmsAdminParam extends UmsAdmin{
    @ApiModelProperty(value = "用户名", required = true)
    private String username;
    @ApiModelProperty(value = "密码", required = true)
    private String password;
    @ApiModelProperty(value = "用户头像")
    private String icon;
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "用户昵称")
    private String nickName;
    @ApiModelProperty(value = "备注")
    private String note;
}
