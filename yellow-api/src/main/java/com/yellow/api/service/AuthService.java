package com.yellow.api.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yellow.api.autoconfigure.SystemProperties;
import com.yellow.api.mapper.SysUserMapper;
import com.yellow.api.model.SysUser;
import com.yellow.api.model.response.AuthCode;
import com.yellow.api.model.response.LoginResult;
import com.yellow.api.security.JwtTokenUtils;
import com.yellow.api.util.SecurityUtils;
import com.yellow.common.constant.Constants;
import com.yellow.common.entity.response.ResponseResult;
import com.yellow.common.exception.BizException;
import com.yellow.common.exception.ExceptionCast;
import com.yellow.common.util.CookieUtils;
import com.yellow.common.util.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 认证授权业务实现类
 * @Author zhou
 * @Date 2021/3/23 15:38
 */
@Service
public class AuthService extends ServiceImpl<SysUserMapper, SysUser> {

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private RedisUtils redisUtils;

    public LoginResult login(HttpServletResponse response, String username, String password) {
        try {
            // 登录认证
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }catch (Exception e) {
            if(e instanceof BadCredentialsException){
                ExceptionCast.cast(AuthCode.AUTH_CREDENTIAL_ERROR);
            }else if(e.getCause() instanceof BizException){
                ExceptionCast.cast(((BizException) e.getCause()).getResultCode());
            }
            e.printStackTrace();
            ExceptionCast.cast(AuthCode.AUTH_LOGIN_ERROR);
        }
        UserDetails userDetails = SecurityUtils.getCurrentUser();

        // 将用户信息存入Redis
        redisUtils.set(Constants.LOGIN_TOKEN_KEY + userDetails.getUsername(), JSON.toJSONString(userDetails), SystemProperties.auth.getUserValiditySeconds());

        // 访问令牌
        String token = JwtTokenUtils.generateToken(userDetails.getUsername());

        //将访问令牌存储到cookie
        AuthService.saveCookie(response, token);

        return LoginResult.success(token);
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        String token = JwtTokenUtils.getToken();
        if(StringUtils.isEmpty(token)){
            return;
        }
        String name = JwtTokenUtils.getUsernameFromToken(token);
        if(redisUtils.exists(Constants.LOGIN_TOKEN_KEY + name)){

            // 删除用户信息
            redisUtils.delete(Constants.LOGIN_TOKEN_KEY + name);

            //清除cookie
            clearCookie(response, token);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if(authentication != null){
                new SecurityContextLogoutHandler().logout(request, response, authentication);
            }
        }
    }

    public ResponseResult changePassword(HttpServletRequest request, HttpServletResponse response, String oldPwd, String newPwd) {
        // 1. 校验原密码是否正确
        if(!new BCryptPasswordEncoder().matches(oldPwd, SecurityUtils.getCurrentUser().getPassword())){
            ExceptionCast.cast(AuthCode.AUTH_PWD_ERROR);
        }

        SysUser user = sysUserMapper.selectById(SecurityUtils.getCurrentUser().getId());

        // 2. 修改密码
        user.setPassword(new BCryptPasswordEncoder().encode(newPwd));
        sysUserMapper.updateById(user);

        // 3. 刷新token和Redis中的密码缓存
        this.logout(request, response);
        this.login(response, user.getUsername(), newPwd);

        return ResponseResult.success();
    }

    /**
     * 将令牌保存到cookie
     * @param token 用户身份令牌
     * @return void
     * @author zhouhao
     * @date  2020/9/24 14:06
     */
    public static void saveCookie(HttpServletResponse response, String token){
        // 添加cookie 认证令牌，最后一个参数设置为false，表示允许浏览器获取
        CookieUtils.addCookie(response, SystemProperties.auth.getCookieDomain(), "/", JwtTokenUtils.COOKIE_ID, token, SystemProperties.auth.getCookieMaxAge(), false);
    }

    /**
     * 清除cookie中的token
     * @param token 用户身份令牌
     * @return void
     * @author zhouhao
     * @date  2020/9/24 14:06
     */
    public static void clearCookie(HttpServletResponse response, String token){
        // 清除cookie （maxAge置0，即：清除）
        CookieUtils.addCookie(response, SystemProperties.auth.getCookieDomain(), "/", JwtTokenUtils.COOKIE_ID, token, 0, false);
    }
}