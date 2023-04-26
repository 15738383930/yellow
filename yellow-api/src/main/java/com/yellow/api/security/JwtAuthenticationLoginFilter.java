package com.yellow.api.security;

import com.alibaba.fastjson.JSON;
import com.yellow.api.autoconfigure.SystemProperties;
import com.yellow.api.model.response.AuthCode;
import com.yellow.api.model.response.LoginResult;
import com.yellow.api.service.AuthService;
import com.yellow.api.util.SecurityUtils;
import com.yellow.common.constant.Constants;
import com.yellow.common.entity.response.ResponseResult;
import com.yellow.common.exception.BizException;
import com.yellow.common.util.RedisUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录认证过滤器
 *
 * @author Hao.
 * @date 2020年6月2日
 */
@Component
public class JwtAuthenticationLoginFilter extends AbstractAuthenticationProcessingFilter {

    @Resource
    private RedisUtils redisUtils;

    protected JwtAuthenticationLoginFilter(AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher("/auth/login", "POST"), authenticationManager);
    }

    /**
     * 执行登录认证
     * @author Hao.
     * @date 2023/4/24 11:14
     * @param request
     * @param response
     * @return Authentication
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        try {
            JwtUserDetails user = JSON.parseObject(request.getInputStream(), JwtUserDetails.class);
            final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
            this.setDetails(request, token);
            return this.getAuthenticationManager().authenticate(token);
        } catch (Exception e) {
            if(e instanceof BadCredentialsException){
                ResponseResult.output(AuthCode.AUTH_CREDENTIAL_ERROR);
            }else if(e.getCause() instanceof BizException){
                ResponseResult.output(((BizException) e.getCause()).getResultCode());
            }
            e.printStackTrace();
            ResponseResult.output(AuthCode.AUTH_LOGIN_ERROR);
        }
        return null;
    }


    /**
     * 登录成功后的业务处理
     * @author Hao.
     * @date 2023/4/24 11:14
     * @param request
     * @param response
     * @return Authentication
     */
    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                         Authentication authentication){
        UserDetails userDetails = SecurityUtils.getCurrentUser();

        // 将用户信息存入Redis
        redisUtils.set(Constants.LOGIN_TOKEN_KEY + authentication.getName(), JSON.toJSONString(userDetails), SystemProperties.auth.getUserValiditySeconds());

        // 访问令牌
        String token = JwtTokenUtils.generateToken(userDetails.getUsername());

        //将访问令牌存储到cookie
        AuthService.saveCookie(response, token);

        ResponseResult.output(response, LoginResult.success(token));
    }

    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

}
