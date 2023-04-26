package com.yellow.api.util;

import com.yellow.api.security.JwtUserDetails;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

/**
 * security 获取当前用户 工具类
 * @Author zhou
 * @Date 2021/4/1 14:17
 */
public class SecurityUtils {

    public static JwtUserDetails getCurrentUser(){
        SecurityContext ctx = SecurityContextHolder.getContext();
        JwtUserDetails user = new JwtUserDetails();
        if (Objects.isNull(ctx)) {
            return user;
        }
        if (Objects.isNull(ctx.getAuthentication())) {
            return user;
        }
        if (Objects.isNull(ctx.getAuthentication().getPrincipal())) {
            return user;
        }

        final Object principal = ctx.getAuthentication().getPrincipal();
        if(Objects.isNull(principal) || !(principal instanceof JwtUserDetails)){
            return user;
        }
        return (JwtUserDetails) principal;
    }

    public static String getCurrentUsername(){
        return getCurrentUser().getUsername();
    }
}
