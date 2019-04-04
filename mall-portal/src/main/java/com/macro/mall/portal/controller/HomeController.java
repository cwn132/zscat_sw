package com.macro.mall.portal.controller;

import com.macro.mall.annotation.IgnoreAuth;
import com.macro.mall.model.*;
import com.macro.mall.portal.cms.service.CmsSubjectService;
import com.macro.mall.portal.constant.RedisKey;
import com.macro.mall.portal.domain.CommonResult;
import com.macro.mall.portal.domain.HomeContentResult;
import com.macro.mall.portal.domain.HomeFlashPromotion;
import com.macro.mall.portal.service.*;
import com.macro.mall.portal.util.JsonUtil;
import com.macro.mall.portal.vo.GeetInit;
import com.macro.mall.portal.vo.GeetestLib;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * 首页内容管理Controller
 * https://gitee.com/zscat-platform/mall on 2019/1/28.
 */
@RestController
@Api(tags = "HomeController", description = "首页内容管理")
@RequestMapping("/api/home")
public class HomeController {
    @Autowired
    private HomeService homeService;
    @Autowired
    UmsMemberCouponService umsMemberCouponService;
    @Autowired
    private PmsProductAttributeCategoryService productAttributeCategoryService;
    @Autowired
    private SmsHomeAdvertiseService advertiseService;
    @Autowired
    private PmsProductService pmsProductService;
    @Autowired
    private PmsProductAttributeService productAttributeService;

    @Autowired
    private PmsProductCategoryService productCategoryService;
    @Autowired
    private CmsSubjectService subjectService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private UmsMemberService memberService;

    @IgnoreAuth
    @ApiOperation("首页内容页信息展示")
    @RequestMapping(value = "/content", method = RequestMethod.GET)
    public Object content() {
        HomeContentResult contentResult = null;
        String bannerJson = redisService.get(RedisKey.HomeContentResult);
        if(bannerJson!=null){
            contentResult = JsonUtil.jsonToPojo(bannerJson,HomeContentResult.class);
        }else {
            contentResult = homeService.content();
            redisService.set(RedisKey.HomeContentResult,JsonUtil.objectToJson(contentResult));
            redisService.expire(RedisKey.HomeContentResult,24*60*60);
        }
        return new CommonResult().success(contentResult);
    }
    @IgnoreAuth
    @ApiOperation("首页内容页信息展示")
    @RequestMapping(value = "/pc/content", method = RequestMethod.GET)
    public Object pcContent() {
        HomeContentResult contentResult = null;
        String bannerJson = redisService.get(RedisKey.HomeContentResult);
        if(bannerJson!=null){
            contentResult = JsonUtil.jsonToPojo(bannerJson,HomeContentResult.class);
        }else {
            contentResult = homeService.content();
            redisService.set(RedisKey.HomeContentResult,JsonUtil.objectToJson(contentResult));
            redisService.expire(RedisKey.HomeContentResult,24*60*60);
        }
        return new CommonResult().success(contentResult);
    }

    @IgnoreAuth
    @ApiOperation("分页获取推荐商品")
    @RequestMapping(value = "/recommendProductList", method = RequestMethod.GET)
    public Object recommendProductList(@RequestParam(value = "pageSize", defaultValue = "4") Integer pageSize,
                                       @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<PmsProduct> productList = homeService.recommendProductList(pageSize, pageNum);
        return new CommonResult().success(productList);
    }
    @IgnoreAuth
    @ApiOperation("分页获取最热商品")
    @RequestMapping(value = "/hotProductList", method = RequestMethod.GET)
    public Object hotProductList(@RequestParam(value = "pageSize", defaultValue = "4") Integer pageSize,
                                       @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<PmsProduct> productList = homeService.hotProductList(pageSize, pageNum);
        return new CommonResult().success(productList);
    }
    @IgnoreAuth
    @ApiOperation("分页获取最新商品")
    @RequestMapping(value = "/newProductList", method = RequestMethod.GET)
    public Object newProductList(@RequestParam(value = "pageSize", defaultValue = "4") Integer pageSize,
                                 @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<PmsProduct> productList = homeService.newProductList(pageSize, pageNum);
        return new CommonResult().success(productList);
    }
    @IgnoreAuth
    @ApiOperation("分页获取秒杀商品")
    @RequestMapping(value = "/skillGoods", method = RequestMethod.GET)
    public Object skillGoods(@RequestParam(value = "pageSize", defaultValue = "4") Integer pageSize,
                                 @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        HomeFlashPromotion productList = homeService.getHomeFlashPromotion();
        return new CommonResult().success(productList);
    }

    @IgnoreAuth
    @ApiOperation("获取首页商品分类")
    @RequestMapping(value = "/productCateList", method = RequestMethod.GET)
    public Object getProductCateList(@RequestParam(value = "parentId", required = false, defaultValue = "0") Long parentId) {
        List<PmsProductCategory> productCategoryList = homeService.getProductCateList(parentId);
        return new CommonResult().success(productCategoryList);
    }
    @IgnoreAuth
    @ApiOperation("根据分类获取专题")
    @RequestMapping(value = "/subjectList", method = RequestMethod.GET)
    public Object getSubjectList(@RequestParam(required = false) Long cateId,
                                 @RequestParam(value = "pageSize", defaultValue = "4") Integer pageSize,
                                 @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<CmsSubject> subjectList = homeService.getSubjectList(cateId, pageSize, pageNum);
        return new CommonResult().success(subjectList);
    }
    @IgnoreAuth
    @GetMapping(value = "/subjectDetail")
    @ApiOperation(value = "据分类获取专题")
    public Object subjectDetail(@RequestParam(value = "id", required = false, defaultValue = "0") Long id) {
        CmsSubject cmsSubject = subjectService.selectByPrimaryKey(id);
        UmsMember umsMember = memberService.getCurrentMember();
        /*if (umsMember != null && umsMember.getId() != null) {
            MemberProductCollection findCollection = productCollectionRepository.findByMemberIdAndProductId(
                    umsMember.getId(), id);
            if(findCollection!=null){
                cmsSubject.setIs_favorite(1);
            }else{
                cmsSubject.setIs_favorite(2);
            }
        }*/
        return new com.macro.mall.dto.CommonResult().success(cmsSubject);
    }



    /**
     * banner
     *
     * @return
     */
    @IgnoreAuth
    @GetMapping("/bannerList")
    public Object bannerList(@RequestParam(value = "type", required = false, defaultValue = "10") Integer type) {
        List<SmsHomeAdvertise> bannerList = null;
        String bannerJson = redisService.get(RedisKey.appletBannerKey+type);
        if(bannerJson!=null && bannerJson!="[]"){
            bannerList = JsonUtil.jsonToList(bannerJson,SmsHomeAdvertise.class);
        }else {
            bannerList = advertiseService.list(null, type, null, 5, 1);
            redisService.set(RedisKey.appletBannerKey+type,JsonUtil.objectToJson(bannerList));
            redisService.expire(RedisKey.appletBannerKey+type,24*60*60);
        }
      //  List<SmsHomeAdvertise> bannerList = advertiseService.list(null, type, null, 5, 1);
        return new CommonResult().success(bannerList);
    }


    @IgnoreAuth
    @RequestMapping(value = "/navList",method = RequestMethod.GET)
    @ApiOperation(value = "获取导航栏")
    public Object getNavList(){

        return new com.macro.mall.dto.CommonResult().success(null);
    }

    @RequestMapping(value = "/member/geetestInit",method = RequestMethod.GET)
    @ApiOperation(value = "极验初始化")
    public String geetesrInit(HttpServletRequest request){

        GeetestLib gtSdk = new GeetestLib(GeetestLib.id, GeetestLib.key,GeetestLib.newfailback);

        String resStr = "{}";

        //自定义参数,可选择添加
        HashMap<String, String> param = new HashMap<String, String>();

        //进行验证预处理
        int gtServerStatus = gtSdk.preProcess(param);

        //将服务器状态设置到redis中

        String key = UUID.randomUUID().toString();
     //   jedisClient.set(key,gtServerStatus+"");
       // jedisClient.expire(key,360);

        resStr = gtSdk.getResponseStr();
        GeetInit geetInit = JsonUtil.jsonToPojo(resStr,GeetInit.class);
               // new Gson().fromJson(resStr,GeetInit.class);
        geetInit.setStatusKey(key);
        return JsonUtil.objectToJson(geetInit);
    }


    @IgnoreAuth
    @ApiOperation("分页获取推荐商品")
    @RequestMapping(value = "/getHomeCouponList", method = RequestMethod.GET)
    public Object getHomeCouponList() {
        List<SmsCoupon> couponList = new ArrayList<>();
        UmsMember umsMember = memberService.getCurrentMember();
        if (umsMember != null && umsMember.getId() != null) {
            couponList = umsMemberCouponService.selectNotRecive(umsMember.getId());
        }
        return new CommonResult().success(couponList);
    }
}
