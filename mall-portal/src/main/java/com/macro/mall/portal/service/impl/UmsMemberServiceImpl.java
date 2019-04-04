package com.macro.mall.portal.service.impl;

import com.macro.mall.mapper.UmsMemberLevelMapper;
import com.macro.mall.mapper.UmsMemberMapper;
import com.macro.mall.mapper.UserFormIdDao;
import com.macro.mall.model.*;
import com.macro.mall.portal.config.WxAppletProperties;
import com.macro.mall.portal.controller.ApiBaseAction;
import com.macro.mall.portal.domain.CommonResult;
import com.macro.mall.portal.domain.MemberDetails;
import com.macro.mall.portal.service.RedisService;
import com.macro.mall.portal.service.UmsMemberService;
import com.macro.mall.portal.util.CharUtil;
import com.macro.mall.portal.util.CommonUtil;
import com.macro.mall.portal.util.JsonUtils;
import com.macro.mall.portal.util.JwtTokenUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 会员管理Service实现类
 * https://gitee.com/zscat-platform/mall on 2018/8/3.
 */
@Service
public class UmsMemberServiceImpl implements UmsMemberService {
    @Autowired
    private UmsMemberMapper memberMapper;
    @Autowired
    private UmsMemberLevelMapper memberLevelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RedisService redisService;
    private static final Logger LOGGER = LoggerFactory.getLogger(UmsMemberServiceImpl.class);
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${redis.key.prefix.authCode}")
    private String REDIS_KEY_PREFIX_AUTH_CODE;
    @Value("${authCode.expire.seconds}")
    private Long AUTH_CODE_EXPIRE_SECONDS;
    @Value("${jwt.tokenHead}")
    private String tokenHead;


    @Override
    public UmsMember getByUsername(String username) {
        UmsMemberExample example = new UmsMemberExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<UmsMember> memberList = memberMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(memberList)) {
            return memberList.get(0);
        }
        return null;
    }

    @Override
    public UmsMember getById(Long id) {
        return memberMapper.selectByPrimaryKey(id);
    }

    @Override
    public CommonResult register(String username, String password, String telephone, String authCode) {

        //没有该用户进行添加操作
        UmsMember umsMember = new UmsMember();
        umsMember.setUsername(username);
        umsMember.setPhone(telephone);
        umsMember.setPassword(password);
        this.register(umsMember);
        return new CommonResult().success("注册成功", null);
    }
    @Override
    public CommonResult register(UmsMember user) {
        //验证验证码
        /*if (!verifyAuthCode(authCode, telephone)) {
            return new CommonResult().failed("验证码错误");
        }*/
        if (!user.getPassword().equals(user.getConfimpassword())){
            return new CommonResult().failed("密码不一致");
        }
        //查询是否已有该用户
        UmsMemberExample example = new UmsMemberExample();
        example.createCriteria().andUsernameEqualTo(user.getUsername()).
                andPasswordEqualTo(passwordEncoder.encodePassword(user.getPassword(), null));

        List<UmsMember> umsMembers = memberMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(umsMembers)) {
            return new CommonResult().failed("该用户已经存在");
        }
        //没有该用户进行添加操作
        UmsMember umsMember = new UmsMember();
        umsMember.setUsername(user.getUsername());
        umsMember.setPhone(user.getPhone());
        umsMember.setPassword(passwordEncoder.encodePassword(user.getPassword(), null));
        umsMember.setCreateTime(new Date());
        umsMember.setStatus(1);
        //获取默认会员等级并设置
        UmsMemberLevelExample levelExample = new UmsMemberLevelExample();
        levelExample.createCriteria().andDefaultStatusEqualTo(1);
        List<UmsMemberLevel> memberLevelList = memberLevelMapper.selectByExample(levelExample);
        if (!CollectionUtils.isEmpty(memberLevelList)) {
            umsMember.setMemberLevelId(memberLevelList.get(0).getId());
        }
        memberMapper.insert(umsMember);
        umsMember.setPassword(null);
        return new CommonResult().success("注册成功", null);
    }

    @Override
    public CommonResult generateAuthCode(String telephone) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        //验证码绑定手机号并存储到redis
        redisService.set(REDIS_KEY_PREFIX_AUTH_CODE + telephone, sb.toString());
        redisService.expire(REDIS_KEY_PREFIX_AUTH_CODE + telephone, AUTH_CODE_EXPIRE_SECONDS);
        return new CommonResult().success("获取验证码成功", sb.toString());
    }

    @Override
    public CommonResult updatePassword(String telephone, String password, String authCode) {
        UmsMemberExample example = new UmsMemberExample();
        example.createCriteria().andPhoneEqualTo(telephone);
        List<UmsMember> memberList = memberMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(memberList)) {
            return new CommonResult().failed("该账号不存在");
        }
        //验证验证码
        if (!verifyAuthCode(authCode, telephone)) {
            return new CommonResult().failed("验证码错误");
        }
        UmsMember umsMember = memberList.get(0);
        umsMember.setPassword(passwordEncoder.encodePassword(password, null));
        memberMapper.updateByPrimaryKeySelective(umsMember);
        return new CommonResult().success("密码修改成功", null);
    }

    @Override
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

    @Override
    public void updateIntegration(Long id, Integer integration) {
        UmsMember record = new UmsMember();
        record.setId(id);
        record.setIntegration(integration);
        memberMapper.updateByPrimaryKeySelective(record);
    }

    //对输入的验证码进行校验
    private boolean verifyAuthCode(String authCode, String telephone) {
        if (StringUtils.isEmpty(authCode)) {
            return false;
        }
        String realAuthCode = redisService.get(REDIS_KEY_PREFIX_AUTH_CODE + telephone);
        return authCode.equals(realAuthCode);
    }

    @Override
    public UmsMember queryByOpenId(String openId) {
        return memberMapper.queryByOpenId(openId);
    }

    @Override
    public Object loginByWeixin(HttpServletRequest req) {
        try {
            String code = req.getParameter("code");
            if (StringUtils.isEmpty(code)) {
                System.out.println("code is empty");
            }
            String userInfos = req.getParameter("userInfo");

            String signature = req.getParameter("signature");

            Map<String, Object> me = JsonUtils.readJsonToMap(userInfos);
            if (null == me) {
                return ApiBaseAction.toResponsFail("登录失败");
            }

            Map<String, Object> resultObj = new HashMap<String, Object>();
            //
            //获取openid
            String requestUrl = this.getWebAccess(code);//通过自定义工具类组合出小程序需要的登录凭证 code

            JSONObject sessionData = CommonUtil.httpsRequest(requestUrl, "GET", null);

            if (null == sessionData || StringUtils.isEmpty(sessionData.getString("openid"))) {
                return ApiBaseAction.toResponsFail("登录失败");
            }
            //验证用户信息完整性
            String sha1 = CommonUtil.getSha1(userInfos + sessionData.getString("session_key"));
            if (!signature.equals(sha1)) {
                return ApiBaseAction.toResponsFail("登录失败");
            }
            UmsMember userVo = memberMapper.queryByOpenId(sessionData.getString("openid"));
            String token = null;
            if (null == userVo) {
                UmsMember umsMember = new UmsMember();
                umsMember.setUsername("wxapplet" + CharUtil.getRandomString(12));
                umsMember.setSourceType(1);
                umsMember.setPassword(passwordEncoder.encodePassword("123456", null));
                umsMember.setCreateTime(new Date());
                umsMember.setStatus(1);
                umsMember.setWeixinOpenid(sessionData.getString("openid"));
                if (StringUtils.isEmpty(me.get("avatarUrl").toString())) {
                    //会员头像(默认头像)
                    umsMember.setIcon("/upload/img/avatar/01.jpg");
                } else {
                    umsMember.setIcon(me.get("avatarUrl").toString());
                }
                // umsMember.setGender(Integer.parseInt(me.get("gender")));
                umsMember.setNickname(me.get("nickName").toString());
                //获取默认会员等级并设置
                UmsMemberLevelExample levelExample = new UmsMemberLevelExample();
                levelExample.createCriteria().andDefaultStatusEqualTo(1);
                List<UmsMemberLevel> memberLevelList = memberLevelMapper.selectByExample(levelExample);
                if (!CollectionUtils.isEmpty(memberLevelList)) {
                    umsMember.setMemberLevelId(memberLevelList.get(0).getId());
                }
                memberMapper.insert(umsMember);
                token = jwtTokenUtil.generateToken(umsMember.getUsername());
                resultObj.put("userId", umsMember.getId());
            }else {
                 token = jwtTokenUtil.generateToken(userVo.getUsername());
                resultObj.put("userId", userVo.getId());
            }


            if (StringUtils.isEmpty(token)) {
                return ApiBaseAction.toResponsFail("登录失败");
            }
            resultObj.put("tokenHead", tokenHead);
            resultObj.put("token", token);
            resultObj.put("userInfo", me);

            return ApiBaseAction.toResponsSuccess(resultObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, Object> login(String username, String password) {
        Map<String, Object> tokenMap = new HashMap<>();
        String token = null;
        //密码需要客户端加密后传递
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, passwordEncoder.encodePassword(password, null));
        try {
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UmsMember member = this.getByUsername(username);
            token = jwtTokenUtil.generateToken(userDetails);
            tokenMap.put("userInfo",member);
        } catch (AuthenticationException e) {
            LOGGER.warn("登录异常:{}", e.getMessage());

        }

        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);

        return tokenMap;

    }
    @Override
    public String refreshToken(String oldToken) {
        String token = oldToken.substring(tokenHead.length());
        if (jwtTokenUtil.canRefresh(token)) {
            return jwtTokenUtil.refreshToken(token);
        }
        return null;
    }
    @Autowired
    private WxAppletProperties wxAppletProperties;

    //替换字符串
    public String getCode(String APPID, String REDIRECT_URI, String SCOPE) {
        return String.format(wxAppletProperties.getGetCode(), APPID, REDIRECT_URI, SCOPE);
    }

    //替换字符串
    public String getWebAccess(String CODE) {

        return String.format(wxAppletProperties.getWebAccessTokenhttps(),
                wxAppletProperties.getAppId(),
                wxAppletProperties.getSecret(),
                CODE);
    }

    //替换字符串
    public String getUserMessage(String access_token, String openid) {
        return String.format(wxAppletProperties.getUserMessage(), access_token, openid);
    }

    @Resource
    private UserFormIdDao pushUserFormIdRecordDao;

    @Override
    public void insert(UserFormId entity) {
        pushUserFormIdRecordDao.insert(entity);
    }

    @Override
    public Boolean exists(String formId) {
        if(pushUserFormIdRecordDao.exists(formId) > 0)
            return true;

        return false;
    }
}
