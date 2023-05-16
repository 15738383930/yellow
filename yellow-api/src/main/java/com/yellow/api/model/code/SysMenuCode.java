package com.yellow.api.model.code;

import com.stars.datachange.model.code.BaseCode;
import org.apache.commons.lang3.StringUtils;


/**
 * 系统菜单代码枚举
 * @author zhouhao
 * @date  2021/4/2 13:59
 */
public enum SysMenuCode implements BaseCode {

    /**
     * 菜单层级
     */
    MENU_LEVEL_0("menuLevel", "0", "目录"),
    MENU_LEVEL_1("menuLevel", "1", "菜单"),
    MENU_LEVEL_2("menuLevel", "2", "按钮"),

    /**
     * 是否隐藏
     */
    IS_HIDDEN_1("isHidden", "1", "是"),
    IS_HIDDEN_0("isHidden", "0", "否"),

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
