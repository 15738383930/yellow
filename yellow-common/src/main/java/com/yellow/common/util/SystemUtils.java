package com.yellow.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 系统工具类
 * @Author zhou
 * @Date 2021/4/22 15:11
 */
@Slf4j
@Component
public final class SystemUtils {

    private static RestTemplate restTemplate;

    public SystemUtils(RestTemplate restTemplate){
        SystemUtils.restTemplate = restTemplate;
    }

    /**
     * 获取客户端IP
     *
     * @param request
     * @return
     */
    public static String getIp(HttpServletRequest request) {
        String localhost = "127.0.0.1";
        String ip = null;
        String unknown = "unknown";
        //X-Forwarded-For：Squid 服务代理
        String ipAddresses = request.getHeader("X-Forwarded-For");

        if (StringUtils.isEmpty(ipAddresses) || ipAddresses.equals(localhost) || unknown.equalsIgnoreCase(ipAddresses)) {
            //Proxy-Client-IP：apache 服务代理
            ipAddresses = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ipAddresses) || ipAddresses.equals(localhost) || unknown.equalsIgnoreCase(ipAddresses)) {
            //WL-Proxy-Client-IP：weblogic 服务代理
            ipAddresses = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ipAddresses) || ipAddresses.equals(localhost) || unknown.equalsIgnoreCase(ipAddresses)) {
            //HTTP_CLIENT_IP：有些代理服务器
            ipAddresses = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isEmpty(ipAddresses) || ipAddresses.equals(localhost) || unknown.equalsIgnoreCase(ipAddresses)) {
            //X-Real-IP：nginx服务代理
            ipAddresses = request.getHeader("X-Real-IP");
        }
        //有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
        if (ipAddresses != null && ipAddresses.length() != 0) {
            ip = ipAddresses.split(",")[0];
        }
        //还是不能获取到，最后再通过request.getRemoteAddr();获取
        if (StringUtils.isEmpty(ip)) {
            ip = request.getRemoteAddr();
        }

        ip = "0:0:0:0:0:0:0:1".equals(ip) ? localhost : ip;

        if (localhost.equals(ip)) {
            // 获取本机真正的ip地址
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                log.warn(e.getMessage(), e);
                return ip;
            }
        }
        return ip;
    }

    /**
     * 获取客户端浏览器信息
     * @param request
     * @return java.lang.String
     * @author zhouhao
     * @date  2021/4/22 15:15
     */
    public static String getBrowser(HttpServletRequest request) {
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        Browser browser = userAgent.getBrowser();
        return browser.getName();
    }

    /**
     * 获取客户端操作系统
     * @param request
     * @return java.lang.String
     * @author zhouhao
     * @date  2021/4/22 15:15
     */
    public static String getOs(HttpServletRequest request) {
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        return userAgent.getOperatingSystem().getName();
    }

    /**
     * 根据ip获取客户端详细地址
     * @param ip
     * @return java.lang.String
     * @author zhouhao
     * @date  2021/4/22 15:29
     */
    public static String getAddress(String ip) {
        ResponseEntity<String> entity = restTemplate.getForEntity(String.format("http://whois.pconline.com.cn/ipJson.jsp?ip=%s&json=true", ip), String.class);
        JSONObject jsonObject = JSON.parseObject(entity.getBody());
        return (String) jsonObject.get("addr");
    }

    /**
     * 获取堆栈信息
     */
    public static String getStackTrace(Throwable throwable){
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        }
    }
}
