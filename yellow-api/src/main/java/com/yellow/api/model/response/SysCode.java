package com.yellow.api.model.response;

import com.yellow.common.entity.response.ResultCode;
import lombok.ToString;

/**
 * 系统管理代码
 * @author zhouhao
 * @date  2021/3/23 11:28
 */
@ToString
public enum SysCode implements ResultCode {

    THERE_ARE_SUBMENUS_UNDER_THE_MENU(false, 21000, "该菜单下还有子菜单,无法删除！"),
    PARENT_MENU_ERROR(false, 21001, "父级菜单错误！"),
    VISIBLE_STATUS_ERROR(false, 21002, "是否可见错误！"),
    MENU_DOES_NOT_EXIST(false, 21003, "该菜单不存在！"),
    THE_ROLE_CANNOT_BE_DELETED(false, 21004, "该角色已绑定用户,无法删除！"),
    ROLE_NOT_EXIST(false, 21005, "该角色不存在！"),
    SUPER_ADMIN_CANNOT_BE_DELETED(false, 21006, "超级管理员不可删除！"),
    USER_DOES_NOT_EXIST(false, 21007, "该用户不存在！"),
    WRONG_USER_NAME_OR_PASSWORD(false, 21008, "用户名或密码错误！"),
    USER_DISABLED(false, 21009, "用户已停用,请联系管理员！"),
    USER_NAME_ALREADY_EXISTS(false, 21010, "用户已经存在！"),
    USERNAME_OR_PHONE_ALREADY_EXISTS(false, 21011, "账号或手机号已存在！"),
    PHONE_ALREADY_EXISTS(false, 21012, "手机号已存在！"),
    USER_INFO_NOT_FIND(false, 21013, "用户信息获取失败！"),
    ROLE_ALREADY_EXISTS(false, 21004, "角色已存在！"),


    ;

    private boolean success;

    private int code;

    private String message;

    SysCode(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean success() {
        return success;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}