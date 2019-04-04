package com.macro.mall.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UmsRole implements Serializable {
    private Long id;

    /**
     * 名称
     *
     * @mbggenerated
     */
    private String name;

    /**
     * 描述
     *
     * @mbggenerated
     */
    private String description;

    /**
     * 后台用户数量
     *
     * @mbggenerated
     */
    private Integer adminCount;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 启用状态：0->禁用；1->启用
     *
     * @mbggenerated
     */
    private Integer status;

    private Integer sort;

    private String menuIds;

    private  boolean checked =false;

    private static final long serialVersionUID = 1L;


}