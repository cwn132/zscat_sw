package com.macro.mall;

import com.macro.mall.logistics.service.LogisticInitService;
import com.macro.mall.logistics.service.LogisticsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * https://gitee.com/zscat-platform/mall on 2018/8/27.
 * 前台商品查询逻辑单元测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PortalProductDaoTests {

    @Resource
    private LogisticsService logisticsService;
    @Resource
    private LogisticInitService logisticInitService;

    /**
     * 从快递100同步快递信息 每日上午9:20同步一次
     */
    @Test
    public void logisticGrab() {

        logisticsService.logisticGrab();
    }

    /**
     * 早8-晚17才插入初始物流信息
     */
    @Test
    public void logisticInit() {

        logisticInitService.logisticInit();
    }
    public static void main(String[] args) {
        double recommendCalorie = 1.7;
        System.out.println((int)recommendCalorie);
    }
}
