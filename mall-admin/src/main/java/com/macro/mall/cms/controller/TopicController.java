package com.macro.mall.cms.controller;

import com.macro.mall.dto.CommonResult;
import com.macro.mall.model.CmsTopic;

import com.macro.mall.cms.service.TopicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * 话题表
 *
 * @author zscat
 * @email 951449465@qq.com
 * @date 2019-03-22 18:11:46
 */
@Controller
@Api(tags = "TopicController", description = "话题表管理")
@RequestMapping("/cms/topic")
public class TopicController {
    @Resource
    private TopicService topicService;


    @ApiOperation(value = "添加话题表")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('cms:topic:create')")
    public Object create(@Validated @RequestBody CmsTopic cmsTopic, BindingResult result) {
        CommonResult commonResult;
        int count = topicService.createTopic(cmsTopic);
        if (count == 1) {
            commonResult = new CommonResult().success(count);
        } else {
            commonResult = new CommonResult().failed();
        }
        return commonResult;
    }

    @ApiOperation(value = "更新话题表")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('cms:topic:update')")
    public Object update(@PathVariable("id") Long id,
                         @Validated @RequestBody CmsTopic cmsTopic,
                         BindingResult result) {
        CommonResult commonResult;
        int count = topicService.updateTopic(id, cmsTopic);
        if (count == 1) {
            commonResult = new CommonResult().success(count);
        } else {
            commonResult = new CommonResult().failed();
        }
        return commonResult;
    }

    @ApiOperation(value = "删除话题表")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAuthority('cms:topic:delete')")
    public Object delete(@PathVariable("id") Long id) {
        int count = topicService.deleteTopic(id);
        if (count == 1) {
            return new CommonResult().success(null);
        } else {
            return new CommonResult().failed();
        }
    }

    @ApiOperation(value = "根据话题表名称分页获取话题表列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAuthority('cms:topic:read')")
    public Object getList(CmsTopic topic,
                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                          @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        return new CommonResult().pageSuccess(topicService.listTopic(topic, pageNum, pageSize));
    }

    @ApiOperation(value = "根据编号查询话题表信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAuthority('cms:topic:read')")
    public Object getItem(@PathVariable("id") Long id) {
        return new CommonResult().success(topicService.getTopic(id));
    }

}
