package com.yellow.api.security;

import com.alibaba.fastjson.JSON;
import com.yellow.api.autoconfigure.SystemProperties;
import com.yellow.common.constant.Constants;
import com.yellow.common.util.CookieUtils;
import com.yellow.common.util.DateUtils;
import com.yellow.common.util.StringUtils;
import lombok.Data;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.security.interfaces.RSAPrivateKey;
import java.util.Date;
import java.util.Map;

/**
 * JWT Token工具类
 *
 * @author danny
 * @date 2020年6月2日
 */
public class JwtTokenUtils implements Serializable {

    private static final long serialVersionUID = 3920818695434453021L;

    public static final String COOKIE_ID = "uid";

    private static String generateToken(JwtInfo info) {//jwt令牌的内容
        //生成jwt令牌
        Jwt jwt = JwtHelper.encode(JSON.toJSONString(info), new RsaSigner((RSAPrivateKey) SystemProperties.PRIVATE_KEY));
        //生成jwt令牌编码
        return jwt.getEncoded();
    }

    /**
     * 根据Token获取Claims
     *
     * @param token
     * @return
     */
    private static JwtInfo getClaimsFromToken(String token) {
        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(SystemProperties.PUBLIC_KEY));
        //拿到jwt令牌中自定义的内容
        return JSON.parseObject(jwt.getClaims(), JwtInfo.class);
    }

    /**
     * 生成Token
     *
     * @param username
     * @return
     */
    public static String generateToken(String username) {
        JwtInfo info = new JwtInfo();
        info.setUsername(username);
        // 默认有效期4小时
        info.setInvalidTime(DateUtils.addDateByHour(new Date(), 4));
        return generateToken(info);
    }

    /**
     * 根据Token获取用户名
     *
     * @param token
     * @return
     */
    public static String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getUsername();
    }

    /**
     * 校验Token是否过期
     *
     * @param token
     * @return
     */
    public static Boolean isTokenExpired(String token) {
        try {
            final JwtInfo info = getClaimsFromToken(token);
            Date expiration = info.getInvalidTime();
            return expiration.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 刷新Token
     *
     * @param token
     * @return
     */
    public static String refreshToken(String token) {
        final JwtInfo info = getClaimsFromToken(token);
        info.setInvalidTime(DateUtils.addDateByHour(new Date(), 4));
        return generateToken(info);
    }

    /**
     * 校验Token
     *
     * @param token
     * @param finalUsername
     * @return
     */
    public static Boolean validateToken(String token, String finalUsername) {
        String username = getUsernameFromToken(token);
        return (username.equals(finalUsername) && !isTokenExpired(token));
    }

    /**
     * 获取请求token
     *
     * @return token
     */
    public static String getToken() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return getToken(request);
    }

    /**
     * 获取请求token
     *
     * @param request
     * @return token
     */
    public static String getToken(HttpServletRequest request) {
        String token = request.getHeader(Constants.TOKEN_HEADER);
        if (StringUtils.isNotEmpty(token) && token.startsWith(Constants.TOKEN_PREFIX)) {
            token = token.replace(Constants.TOKEN_PREFIX, "");
        }
        if (StringUtils.isEmpty(token)) {
            token = getTokenFromCookie();
        }
        return token;
    }

    /**
     * 从cookie取出身份令牌
     * @author Hao.
     * @date 2020/10/27 18:26
     * @return java.lang.String
     */
    public static String getTokenFromCookie(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Map<String, String> cookieMap = CookieUtils.readCookie(request, JwtTokenUtils.COOKIE_ID);
        String access_token = cookieMap.get(JwtTokenUtils.COOKIE_ID);
        if(StringUtils.isEmpty(access_token)){
            return null;
        }
        return access_token;
    }

    @Data
    public static class JwtInfo {

        private String username;

        private Date invalidTime;
    }
}
