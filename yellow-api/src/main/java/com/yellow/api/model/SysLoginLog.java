package com.yellow.api.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
public class SysLoginLog implements Serializable {
    /**
     * 访问ID
     */
    private Integer id;

    /**
     * 用户账号
     */
    private String username;

    /**
     * 登录IP地址
     */
    private String ip;

    /**
     * 登录地点
     */
    private String address;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 登录状态（成功/ 失败）
     */
    private String status;

    /**
     * 访问时间
     */
    private Date loginTime;

    private static final long serialVersionUID = 1L;
}