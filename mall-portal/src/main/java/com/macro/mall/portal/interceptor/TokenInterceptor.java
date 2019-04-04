/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：TokenInterceptor.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://localhost:8085
 */

package com.macro.mall.portal.interceptor;

import com.macro.mall.annotation.IgnoreAuth;
import com.macro.mall.exception.ApiRRException;
import com.macro.mall.portal.common.CommonConstant;
import com.macro.mall.portal.util.IpAddressUtil;
import com.macro.mall.portal.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The class Token interceptor.
 *
 * @author paascloud.net @gmail.com
 */
@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenInterceptor.class);
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    public static final String LOGIN_TOKEN_KEY = "Mall-Token";
    /**
     * After completion.
     *
     * @param request  the request
     * @param response the response
     * @param arg2     the arg 2
     * @param ex       the ex
     * @throws Exception the exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception ex) throws Exception {
        if (ex != null) {
            log.error("<== afterCompletion . ex={}", ex.getMessage(), ex);
          //  this.handleException(response);
        }
    }

    /**
     * Post handle.
     *
     * @param request  the request
     * @param response the response
     * @param arg2     the arg 2
     * @param mv       the mv
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView mv) {
    }

    /**
     * Pre handle boolean.
     *
     * @param request  the request
     * @param response the response
     * @param handler  the handler
     * @return the boolean
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        long startTime, endTime;
        Map<String,String[]> params = new HashMap<String,String[]>(request.getParameterMap());

        StringBuffer sbParams = new StringBuffer();
        sbParams.append("?");

        for (String key : params.keySet()) {
            if(null == key || null == params.get(key) || null == params.get(key)[0]){  continue;}
            sbParams.append(key).append("=").append(params.get(key)[0]).append("&");
        }

        if(sbParams.length() > 1) {sbParams = sbParams.delete(sbParams.length() - 1, sbParams.length());}

        String fullUrl = ((HttpServletRequest)request).getRequestURL().toString();
        String token = getToken((HttpServletRequest)request);
        String requestType = ((HttpServletRequest)request).getMethod();

        String uri = request.getRequestURI();
        IgnoreAuth annotation;
        if (handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(IgnoreAuth.class);
        } else {
            return true;
        }
        //如果有@IgnoreAuth注解，则不验证token
        if (annotation != null || fullUrl.contains("error")) {
            return true;
        }
        String authHeader = request.getHeader(this.tokenHeader);
        //如果header中不存在token，则从参数中获取token
        if (StringUtils.isBlank(authHeader) ||  "undefined".equals(authHeader.toLowerCase())) {
            authHeader = request.getParameter(LOGIN_TOKEN_KEY);
        }
//前置条件：token为空情况处理
        if(StringUtils.isBlank(authHeader) ||  "undefined".equals(authHeader.toLowerCase())) {
            log.info(formMapKey(null, "token is null url=" + fullUrl, requestType,
                    IpAddressUtil.getIpAddr((HttpServletRequest)request), sbParams.toString(), "null")
                    + ",\"cost\":\"" + 0 + "ms\"");
            throw new ApiRRException("请先登录", 401);
        }

        if (authHeader != null && authHeader.startsWith(this.tokenHead)) {
            String authToken = authHeader.substring(this.tokenHead.length());// The part after "Bearer "
            String username = jwtTokenUtil.getUserNameFromToken(authToken);
            LOGGER.info("checking username:{}", username);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                log.info(formMapKey(username, fullUrl, requestType,
                        IpAddressUtil.getIpAddr((HttpServletRequest)request), sbParams.toString(), token)
                        + ",\"cost\":\"" + 0 + "ms\"");
                if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    LOGGER.info("authenticated user:{}", username);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }else {
                log.info(formMapKey("wu", fullUrl, requestType,
                        IpAddressUtil.getIpAddr((HttpServletRequest) request), sbParams.toString(), token)
                        + ",\"cost\":\"" + 0 + "ms\"");
            }
        }else {
            throw new ApiRRException("请先登录", 401);
        }
        log.info("<== preHandle - 权限拦截器.  url={}", uri);


        return true;
    }

    private void handleException(HttpServletResponse res) throws IOException {
        res.resetBuffer();
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Credentials", "true");
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        res.getWriter().write("{\"code\":100009 ,\"message\" :\"解析token失败\"}");
        res.flushBuffer();
    }
    private String formMapKey(Object userId, String mothedName, String requestType,
                              String ip, String params, String token) {
        return "\"time\"" + ":\"" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(new Date())
                + "\",\"name\"" + ":\"" + mothedName + "\",\"uid\":\"" + userId
                + "\",\"type\":\"" + requestType + "\",\"ip\":\"" + ip
                + "\",\"token\":\"" + token + "\",\"params\":\"" + params + "\"";
    }

    private String getRequestType(HttpServletRequest request) {
        String ajaxHeader = request.getHeader(CommonConstant.AJAX_REQUEST_HEADER_KEY);
        if (ajaxHeader != null && ajaxHeader.equalsIgnoreCase(CommonConstant.AJAX_REQUEST_HEADER_VALUE)) {
            return "ajax";
        }
        String nativeValue = request.getHeader(CommonConstant.AJAX_NATIVE_HEADER_KEY);
        if (nativeValue != null && nativeValue.equalsIgnoreCase(CommonConstant.AJAX_NATIVE_HEADER_VALUE)) {
            return "native";
        }
        return "web";
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(CommonConstant.AJAX_REQUEST_TOKEN_KEY);
        if(StringUtils.isNotBlank(token)){
            //Bearer xxxxxx  (xxxxxx 是 token)
            String[] temp = token.split(" ");
            if(temp.length > 1){
                return temp[1];
            }
        }
        return null;
    }

}
  