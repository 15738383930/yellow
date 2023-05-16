package com.yellow.common.entity.code;

import com.stars.datachange.model.code.BaseCode;

public enum RoleCode implements BaseCode {

    ADMIN("roleCode", "ROLE_ADMIN", "管理员"),
    SYSTEM("roleCode", "ROLE_SYSTEM", "系统管理员"),
    TEST("roleCode", "ROLE_TEST", "测试管理员"),
    DEMO("roleCode", "ROLE_DEMO", "演示管理员"),

    ;

    private final String t;
    private final String k;
    private final String v;

    RoleCode(String t, String k, String v) {
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
