package com.macro.mall.portal.controller;

import com.macro.mall.annotation.IgnoreAuth;
import com.macro.mall.dto.CommonResult;
import com.macro.mall.model.UmsMember;
import com.macro.mall.model.UserFormId;
import com.macro.mall.portal.service.UmsMemberService;
import com.macro.mall.util.Validator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 会员登录注册管理Controller
 * https://gitee.com/zscat-platform/mall on 2018/8/3.
 */
@Controller
@Api(tags = "UmsMemberController", description = "会员管理系统")
@RequestMapping("/api/member")
public class UmsMemberController extends ApiBaseAction{
    @Autowired
    private UmsMemberService memberService;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @IgnoreAuth
    @ApiOperation(value = "登录以后返回token")
    @GetMapping(value = "/login")
    @ResponseBody
    public Object login(UmsMember umsMember) {
        if (umsMember==null){
            return new CommonResult().validateFailed("用户名或密码错误");
        }
        try {
            Map<String, Object> token = memberService.login(umsMember.getUsername(), umsMember.getPassword());
            if (token.get("token") == null) {
                return new CommonResult().validateFailed("用户名或密码错误");
            }
            return new CommonResult().success(token);
        } catch (AuthenticationException e) {
            return new CommonResult().validateFailed("用户名或密码错误");
        }

    }

    @IgnoreAuth
    @ApiOperation("注册")
    @RequestMapping(value = "/reg")
    @ResponseBody
    public Object register(UmsMember umsMember) {
        if (umsMember==null){
            return new CommonResult().validateFailed("用户名或密码错误");
        }
        return memberService.register(umsMember);
    }
    @IgnoreAuth
    @ApiOperation("获取验证码")
    @RequestMapping(value = "/getAuthCode", method = RequestMethod.GET)
    @ResponseBody
    public Object getAuthCode(@RequestParam String telephone) {
        return memberService.generateAuthCode(telephone);
    }

    @ApiOperation("修改密码")
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @ResponseBody
    public Object updatePassword(@RequestParam String telephone,
                                 @RequestParam String password,
                                 @RequestParam String authCode) {
        return memberService.updatePassword(telephone, password, authCode);
    }
    @IgnoreAuth
    @GetMapping("/user")
    @ResponseBody
    public Object user() {
        UmsMember umsMember = memberService.getCurrentMember();
        if (umsMember != null && umsMember.getId() != null) {
            return new com.macro.mall.portal.domain.CommonResult().success(umsMember);
        }
        return new com.macro.mall.portal.domain.CommonResult().failed();

    }

    @ApiOperation(value = "刷新token")
    @RequestMapping(value = "/token/refresh", method = RequestMethod.GET)
    @ResponseBody
    public Object refreshToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String refreshToken = memberService.refreshToken(token);
        if (refreshToken == null) {
            return new CommonResult().failed();
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return new CommonResult().success(tokenMap);
    }



    @ApiOperation(value = "登出功能")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public Object logout() {
        return new CommonResult().success(null);
    }


    /**
     * 提交小程序推送formid
     * @param request
     * @param response
     * @param formId 小程序推送formId
     * @param source @see com.fittime.health.market.model.PushUserFormIdRecord.source
     * @return
     */
    @RequestMapping(value = "submitFormId")
    @ApiOperation(value = "提交小程序推送formid")
    @ResponseBody
    public Object submitFormId(HttpServletRequest request, HttpServletResponse response,  String formId, Integer source) {

        UserFormId entity = new UserFormId();

        if (Validator.empty(formId)) {
            return new CommonResult().validateFailed("前置参数错误，formId不能为空");
        }

        if (Validator.empty(source)) {
            return new CommonResult().validateFailed("前置参数错误，source不能为空");
        }

        //校验formId是否已经存在
        if(memberService.exists(formId)) {
            return new CommonResult().validateFailed("前置参数错误，formId已经存在 formId：" + formId);
        }

        entity.setUserId(this.getCurrentMember().getId());
        entity.setFormId(formId);
        entity.setSource(source);
        entity.setStatus(1);

        memberService.insert(entity);

        return new CommonResult().success("添加成功");
    }
}
