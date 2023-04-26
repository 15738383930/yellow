package com.yellow.common.entity.response;

/**
 * 响应代码规范
 * <li>10000--19999 通用代码
 * <li>20000--20999-- 认证授权代码
 * <li>21000--21999-- 系统管理代码
 * <li>22000--22999-- 企业模块
 * <li>23000--23999-- ...
 * <li>99999-- 系统繁忙
 */
public interface ResultCode {
    /**
     * 操作是否成功
     * true为成功
     * false操作失败
     */
    boolean success();

    /**
     * 操作代码
     */
    int code();

    /**
     * 提示信息
     */
    String message();
}
