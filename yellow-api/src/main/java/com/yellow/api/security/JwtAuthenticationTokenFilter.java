package com.yellow.api.security;

import com.yellow.api.autoconfigure.SystemProperties;
import com.yellow.api.model.response.AuthCode;
import com.yellow.common.constant.Constants;
import com.yellow.common.exception.ExceptionCast;
import com.yellow.common.util.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * token鉴权过滤器
 *
 * @author Hao.
 * @date 2020年6月2日
 */
@Component
public class JwtAuthenticationTokenFilter extends BasicAuthenticationFilter {

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private RedisUtils redisUtils;

    public JwtAuthenticationTokenFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String token = JwtTokenUtils.getToken();
        if (StringUtils.isEmpty(token)) {
            chain.doFilter(request, response);
            return;
        }
        String username = JwtTokenUtils.getUsernameFromToken(token);
        String key = Constants.LOGIN_TOKEN_KEY + username;

        // 校验token是否过期
        if (JwtTokenUtils.isTokenExpired(token)) {
            ExceptionCast.cast(AuthCode.AUTH_TOKEN_EXPIRED);
            return;
        }

        // 校验用户是否登录超时
        if (!redisUtils.exists(key)) {
            ExceptionCast.cast(AuthCode.AUTH_LOGIN_TIMEOUT);
            return;
        }

        // 刷新过期时间
        if (redisUtils.getExpire(key, TimeUnit.MINUTES) <= 30) {
            // 刷新Redis会话时间
            redisUtils.expire(key, SystemProperties.auth.getUserValiditySeconds(), TimeUnit.SECONDS);
        }

        // 续签
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (JwtTokenUtils.validateToken(token, userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }
}
