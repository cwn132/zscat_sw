package com.macro.mall.dto;

import com.macro.mall.model.PmsProduct;
import com.macro.mall.sms.model.SmsGroupMember;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 创建和修改商品时使用的参数
 * https://gitee.com/zscat-platform/mall on 2018/4/26.
 */
@Data
public class PmsProductAndGroup extends PmsProduct {
    private Map<String, List<SmsGroupMember>> map;
    private String isGroup = "1"; //1 可以发起团购
    private String  is_favorite;
}
