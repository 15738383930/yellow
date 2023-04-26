package com.yellow.api.model.code;

import com.stars.datachange.model.code.BaseCode;


/**
 * 日志详情代码枚举
 * @author zhouhao
 * @date  2021/4/2 13:59
 */
public enum SysDetailLogCode implements BaseCode {

    /**
     * 菜单层级
     */
    LOG_TYPE_1("logType", "1", "登录日志"),
    LOG_TYPE_2("logType", "2", "操作日志"),
    ;
    private final String t;
    private final String k;
    private final String v;

    SysDetailLogCode(String t, String k, String v) {
        this.t = t;
        this.k = k;
        this.v = v;
    }

    @Override
    public String t() {
        return t;
    }

    @Override
    public String k() {
        return k;
    }

    @Override
    public String v() {
        return v;
    }

}
