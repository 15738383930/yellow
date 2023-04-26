package com.yellow.common.interceptor;

import com.yellow.common.entity.response.CommonCode;
import com.yellow.common.entity.response.ResponseResult;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * 通过拦截器处理sql注入、跨站XSS攻击风险
 *
 * @author Hao.
 * @date 2020年5月30日
 */
@Component
public class SqlInjectInterceptor implements HandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
        // TODO Auto-generated method stub
    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
            throws Exception {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) {
        Enumeration<String> names = arg0.getParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            String[] values = arg0.getParameterValues(name);
            for (String value : values) {
                //sql注入直接拦截
                if (judgeSqlInject(value.toLowerCase())) {
                    ResponseResult.output(arg1, ResponseResult.get(CommonCode.INVALID_PARAM, "参数含有非法攻击字符，已禁止继续访问！"));
                    return false;
                }
                //跨站xss清理
                clearXss(value);
            }
        }
        return true;
    }

    /**
     * 判断参数是否含有攻击串
     *
     * @param value
     * @return
     */
    private boolean judgeSqlInject(String value) {
        if (value == null || "".equals(value)) {
            return false;
        }

        // 2021-6-15: 去除了“-”符号拦截  原因：阻挡日期参数（例：2020-01-01）
        // 2021-6-16: 去除了“ ”符号拦截  原因：阻挡日期时间参数（例：2020-01-01 12:12:12）
        String xssStr = "and|or|select|update|delete|drop|truncate|%20|=|--|;|'|%|#|+|,|//|/|\\|!=|(|)";
        String[] xssArr = xssStr.split("\\|");
        for (String s : xssArr) {
            if (value.contains(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 处理跨站xss字符转义
     *
     * @param value
     */
    private void clearXss(String value) {
        if (value == null || "".equals(value)) {
            return;
        }
        value = value.replaceAll("<", "<").replaceAll(">", ">");
        value = value.replaceAll("\\(", "(").replace("\\)", ")");
        value = value.replaceAll("'", "'");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\"'][\\s]*javascript:(.*)[\"']", "\"\"");
        value = value.replace("script", "");
    }
}