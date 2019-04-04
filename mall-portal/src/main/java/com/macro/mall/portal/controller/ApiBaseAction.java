package com.macro.mall.portal.controller;


import com.macro.mall.model.UmsMember;
import com.macro.mall.portal.domain.MemberDetails;
import org.apache.log4j.Logger;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


/**
 * @author harmon
 * @ClassName: ApiBaseAction
 * @Description: 基础控制类
 * @date 2016年9月2日
 */
public class ApiBaseAction {
    protected Logger logger = Logger.getLogger(getClass());
    /**
     * 得到request对象
     */
    @Autowired
    protected HttpServletRequest request;
    /**
     * 得到response对象
     */
    @Autowired
    protected HttpServletResponse response;


    /**
     * 参数绑定异常
     */
    @ExceptionHandler({BindException.class, MissingServletRequestParameterException.class, TypeMismatchException.class})
    @ResponseBody
    public Map<String, Object> bindException(Exception e) {
        if (e instanceof BindException) {
            return toResponsObject(1, "参数绑定异常", e.getMessage());
        }
        return toResponsObject(1, "处理异常", e.getMessage());
    }

    /**
     * @param requestCode
     * @param msg
     * @param data
     * @return Map<String   ,   Object>
     * @throws
     * @Description:构建统一格式返回对象
     * @date 2016年9月2日
     * @author zhuliyun
     */
    public static Map<String, Object> toResponsObject(int requestCode, String msg, Object data) {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("code", requestCode);
        obj.put("msg", msg);
        if (data != null)
            obj.put("data", data);
        return obj;
    }

    public static Map<String, Object> toResponsSuccess(Object data) {
        Map<String, Object> rp = toResponsObject(200, "执行成功", data);
        return rp;
    }

    public static Map<String, Object> toResponsMsgSuccess(String msg) {
        return toResponsObject(200, msg, "");
    }

    public static Map<String, Object> toResponsSuccessForSelect(Object data) {
        Map<String, Object> result = new HashMap<>(2);
        result.put("list", data);
        return toResponsObject(200, "执行成功", result);
    }

    public static Map<String, Object> toResponsFail(String msg) {
        return toResponsObject(1, msg, null);
    }

    /**
     * initBinder 初始化绑定 <br>
     * 这里处理了3种类型<br>
     * 1、字符串自动 trim 去掉前后空格 <br>
     * 2、java.util.Date 转换为 "yyyy-MM-dd HH:mm:ss" 格式<br>
     * 3、java.sql.Date 转换为 "yyyy-MM-dd" 格式<br>
     * 4、java.util.Timestamps 时间转换
     *
     * @param binder  WebDataBinder 要注册的binder
     * @param request 前端请求
     */
    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {

        // 字符串自动Trim
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
    }

    /**
     * 获取请求方IP
     *
     * @return 客户端Ip
     */
    public String getClientIp() {
        String xff = request.getHeader("x-forwarded-for");
        if (xff == null) {
            return "8.8.8.8";
        }
        return xff;
    }
    public UmsMember getCurrentMember() {
        try {
            SecurityContext ctx = SecurityContextHolder.getContext();
            Authentication auth = ctx.getAuthentication();
            MemberDetails memberDetails = (MemberDetails) auth.getPrincipal();
            return memberDetails.getUmsMember();
        }catch (Exception e){
            return new UmsMember();
        }
    }

    /**
     * 获取请求的用户Id
     *
     * @return 客户端Ip
     */
    /*public String getUserId() {
        String token = request.getHeader(AuthorizationInterceptor.LOGIN_TOKEN_KEY);
        //查询token信息
        TokenEntity tokenEntity = tokenService.queryByToken(token);
        if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
            return null;
        }
        return tokenEntity.getUserId();
    }*/
}
