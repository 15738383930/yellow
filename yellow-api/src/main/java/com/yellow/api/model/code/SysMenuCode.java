package com.yellow.api.model.code;

import org.apache.commons.lang3.StringUtils;


/**
 * 系统菜单代码枚举
 * @author zhouhao
 * @date  2021/4/2 13:59
 */
public enum SysMenuCode {

    /**
     * 菜单层级
     */
    MENU_LEVEL_1("menuLevel", "1", "目录"),
    MENU_LEVEL_2("menuLevel", "2", "菜单"),
    MENU_LEVEL_3("menuLevel", "3", "按钮"),

    /**
     * 菜单状态
     */
    IS_HIDDEN_0("isHidden", "0", "是"),
    IS_HIDDEN_1("isHidden", "1", "否"),

    /**
     * 是否删除
     */
    IS_DEL_0("isDel", "0", "否"),
    IS_DEL_1("isDel", "1", "是"),
    ;
    private final String t;
    private final String k;
    private final String v;

    SysMenuCode(String t, String k, String v) {
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
            SysMenuCode[] values = SysMenuCode.values();
            for (SysMenuCode code : values) {
                if (code.getT().equals(t) && code.getK().equals(k)) {
                    return code.getV();
                }
            }
        }
        return k;
    }
}
