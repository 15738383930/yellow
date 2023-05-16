package com.yellow.api.model.code;

import com.stars.datachange.model.code.BaseCode;

/**
 * 系统角色代码枚举
 * @author zhouhao
 * @date  2021/3/23 13:11
 */
public enum SysRoleCode implements BaseCode {

    /**
     * 角色状态
     */
    STATUS_1("status", "1", "启用"),
    STATUS_2("status", "2", "禁用"),

    /**
     * 是否删除
     */
    IS_DEL_0("isDel", "0", "否"),
    IS_DEL_1("isDel", "1", "是"),
    ;
    private final String t;
    private final String k;
    private final String v;

    SysRoleCode(String t, String k, String v) {
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
