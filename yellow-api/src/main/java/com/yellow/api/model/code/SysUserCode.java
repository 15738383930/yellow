package com.yellow.api.model.code;

import org.apache.commons.lang3.StringUtils;

/**
 * 系统用户代码枚举
 * @author zhouhao
 * @date  2021/3/23 13:11
 */
public enum SysUserCode {

    /**
     * 用户状态
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

    SysUserCode(String t, String k, String v) {
        this.t = t;
        this.k = k;
        this.v = v;
    }

    public String getT() {
        return t;
    }

    public String getK() {
        return k;
    }

    public String getV() {
        return v;
    }

    public static String getValue(String t, String k) {
        if (StringUtils.isNotEmpty(k)) {
            SysUserCode[] values = SysUserCode.values();
            for (SysUserCode code : values) {
                if (code.getT().equals(t) && code.getK().equals(k)) {
                    return code.getV();
                }
            }
        }
        return k;
    }
}
