package com.macro.mall.portal.controller;

import com.macro.mall.annotation.IgnoreAuth;
import com.macro.mall.dto.CommonResult;
import com.macro.mall.dto.PmsProductAndGroup;
import com.macro.mall.model.*;
import com.macro.mall.portal.cms.service.SubjectCategoryService;
import com.macro.mall.portal.cms.service.SubjectCommentService;
import com.macro.mall.portal.cms.service.SubjectService;
import com.macro.mall.portal.service.PmsProductAttributeCategoryService;
import com.macro.mall.portal.service.PmsProductCategoryService;
import com.macro.mall.portal.service.PmsProductService;
import com.macro.mall.portal.sms.service.GroupService;
import com.macro.mall.portal.util.DateUtils;
import com.macro.mall.sms.mapper.GroupMapper;
import com.macro.mall.sms.model.SmsGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: shenzhuan
 * @Date: 2019/4/2 15:02
 * @Description:
 */
@RestController
@Api(tags = "CmsController", description = "商品关系管理")
@RequestMapping("/api/pms")
public class PmsController {

    @Resource
    private GroupService groupService;
    @Resource
    private PmsProductService pmsProductService;
    @Resource
    private PmsProductAttributeCategoryService productAttributeCategoryService;
    @Resource
    private PmsProductCategoryService productCategoryService;
    @Resource
    private GroupMapper groupMapper;

    @Resource
    private SubjectCategoryService subjectCategoryService;
    @Resource
    private SubjectService subjectService;
    @Resource
    private SubjectCommentService commentService;

    @IgnoreAuth
    @GetMapping(value = "/goods/detail")
    @ApiOperation(value = "查询商品详情信息")
    public Object queryProductDetail(@RequestParam(value = "id", required = false, defaultValue = "0") Long id) {
        PmsProductAndGroup productResult = pmsProductService.get(id);
        Map<String ,Object> map = new HashMap<>();
        SmsGroup group = groupMapper.getByGoodsId(id);
        if (group!=null){
            Date endTime = DateUtils.convertStringToDate(DateUtils.addHours(group.getEndTime(),group.getHours()),"yyyy-MM-dd HH:mm:ss");
            Long nowT = System.currentTimeMillis();
            if (group!=null && nowT>group.getStartTime().getTime() && nowT<endTime.getTime()){
                map.put("group",group);
                map.put("isGroup",1);
            }else{
                map.put("isGroup",2);
            }
        }

        map.put("goods",productResult);
        return new com.macro.mall.portal.domain.CommonResult().success(map);
    }
    @IgnoreAuth
    @ApiOperation(value = "查询商品列表")
    @GetMapping(value = "/goods/list")
    public Object subjectList(PmsProduct product,
                              @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize,
                              @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum) {
        List<PmsProduct> list = pmsProductService.list(product,pageSize,pageNum);
        return new CommonResult().pageSuccess(list);
    }

    @IgnoreAuth
    @ApiOperation(value = "查询商品分类列表")
    @GetMapping(value = "/subjectCategory/list")
    public Object cateList(PmsProductCategory productCategory,
                          @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize,
                          @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum) {
        List<CmsSubjectCategory> list = productCategoryService.list(productCategory,pageSize,pageNum);
        return new CommonResult().pageSuccess(list);
    }


    @IgnoreAuth
    @ApiOperation(value = "查询商品属性分类列表")
    @GetMapping(value = "/subjectComment/list")
    public Object subjectList(PmsProductAttributeCategory productAttributeCategory,
                              @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize,
                              @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum) {
        List<CmsSubjectComment> list = productAttributeCategoryService.list(productAttributeCategory,pageSize,pageNum);
        return new CommonResult().pageSuccess(list);
    }

    @ApiOperation(value = "添加")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('t:area:create')")
    public Object create(@Validated @RequestBody CmsSubject subject, BindingResult result) {
        CommonResult commonResult;
        int count = subjectService.createSubject(subject);
        if (count == 1) {
            commonResult = new CommonResult().success(count);
        } else {
            commonResult = new CommonResult().failed();
        }
        return commonResult;
    }


}
