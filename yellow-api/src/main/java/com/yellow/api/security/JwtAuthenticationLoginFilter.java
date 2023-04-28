package com.yellow.api.security;

import com.alibaba.fastjson.JSON;
import com.yellow.api.autoconfigure.SystemProperties;
import com.yellow.api.exception.LoginException;
import com.yellow.api.exception.LoginExceptionCast;
import com.yellow.api.exception.LoginParameterException;
import com.yellow.api.model.request.LoginRequest;
import com.yellow.api.model.response.AuthCode;
import com.yellow.api.model.response.LoginResult;
import com.yellow.api.service.AuthService;
import com.yellow.api.service.SysCaptchaService;
import com.yellow.api.util.SecurityUtils;
import com.yellow.common.constant.Constants;
import com.yellow.common.entity.response.MethodArgumentNotValidResponseResult;
import com.yellow.common.entity.response.ResponseResult;
import com.yellow.common.exception.ValidationParameterException;
import com.yellow.common.util.RedisUtils;
import com.yellow.common.util.ThrowableUtils;
import com.yellow.common.util.ValidateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.CollectionUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 登录认证过滤器
 *
 * @author Hao.
 * @date 2020年6月2日
 */
@Slf4j
public class JwtAuthenticationLoginFilter extends AbstractAuthenticationProcessingFilter {

    private SysCaptchaService sysCaptchaService;

    private RedisUtils redisUtils;

    public JwtAuthenticationLoginFilter(AuthenticationManager authenticationManager, RedisUtils redisUtils, SysCaptchaService sysCaptchaService) {
        super(new AntPathRequestMatcher("/auth/login", "POST"), authenticationManager);
        this.sysCaptchaService = sysCaptchaService;
        this.redisUtils = redisUtils;
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
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 1. 得到登录请求数据
        LoginRequest param = JSON.parseObject(request.getInputStream(), LoginRequest.class);
        // 参数校验
        final List<ValidationParameterException.Parameter> parameters = ValidateUtils.parameters(param);
        if (!CollectionUtils.isEmpty(parameters)) {
            throw new LoginParameterException(parameters);
        }

        // 2. 验证码校验
        sysCaptchaService.validate(param.getUuid(), param.getCaptcha());

        // 3. 登录认证
        final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(param.getUsername(), param.getPassword());
        token.setDetails(authenticationDetailsSource.buildDetails(request));
        final Authentication authentication = this.getAuthenticationManager().authenticate(token);

        // 4. 认证信息存储
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }


    /**
     * 登录认证成功后，可以把认证信息存入Redis和cookie中保存
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

        // 将访问令牌存储到cookie
        AuthService.saveCookie(response, token);

        ResponseResult.output(response, LoginResult.success(token));
    }

    /**
     * 登录认证失败，异常可在这里抛出<p>
     *     前提是抛出的异常要继承{@link AuthenticationException}，让SpringSecurity感知到，否则一律进入{@link ExceptionHandlingConfigurer#authenticationEntryPoint}处理
     * @author Hao.
     * @date 2023/4/27 14:21
     */
    @Override
    public void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws ServletException, IOException {
        if(e instanceof LoginParameterException){
            final Map<String, String> collect = ((LoginParameterException) e).getParameters().stream()
                    .collect(Collectors.toMap(ValidationParameterException.Parameter::getFieldName, ValidationParameterException.Parameter::getMessage));
            ResponseResult.output(response, MethodArgumentNotValidResponseResult.fail(collect));
            return;
        }

        if(e instanceof LoginException){
            ResponseResult.output(((LoginException) e).getResultCode());
            return;
        }

        if(e.getCause() instanceof LoginException){
            ResponseResult.output(((LoginException) e.getCause()).getResultCode());
            return;
        }

        if(e instanceof BadCredentialsException){
            ResponseResult.output(AuthCode.AUTH_CREDENTIAL_ERROR);
            return;
        }

        // 未知异常，这里只是为了兜底，一般不走这里
        // 如果真走了这里，那么会进入预先设定好的场景：
        // org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer.authenticationEntryPoint
        log.error("catch AuthenticationException: {}", ThrowableUtils.getStackTrace(e));
        super.unsuccessfulAuthentication(request, response, e);
    }
}
