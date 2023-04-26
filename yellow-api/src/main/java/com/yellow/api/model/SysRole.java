package com.yellow.api.model;

import java.util.Date;

import com.yellow.common.entity.BaseEntity;
import lombok.*;

/**
    * 系统角色表
    */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysRole extends BaseEntity {
    private Integer id;

    /**
    * 角色代码
    */
    private String roleCode;

    /**
    * 角色名称
    */
    private String roleName;

    /**
    * 角色状态：1-启用 2-禁用
    */
    private Integer status;

    /**
    * 备注
    */
    private String remark;
}