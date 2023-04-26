package com.yellow.api.model;

import java.util.Date;

import com.yellow.common.entity.BaseEntity;
import lombok.*;

/**
    * 系统用户表
    */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysUser extends BaseEntity {
    private Integer id;

    /**
    * 用户名
    */
    private String username;

    /**
    * 密码
    */
    private String password;

    /**
    * 用户昵称
    */
    private String name;

    /**
    * 手机号（留作备用，后期可延伸手机号登录业务）
    */
    private Integer phone;

    /**
    * 最后一次登录系统时间
    */
    private Date lastLoginTime;

    /**
    * 用户状态：1-启用 2-禁用
    */
    private Integer status;

    /**
    * 备注
    */
    private String remark;
}