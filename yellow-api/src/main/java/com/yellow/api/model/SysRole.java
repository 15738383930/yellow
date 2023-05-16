package com.yellow.api.model;

import java.util.Date;

import com.yellow.common.constant.Constants;
import com.yellow.common.entity.BaseEntity;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

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
    @NotBlank(message = "角色代码不能为空！")
    private String roleCode;

    /**
    * 角色名称
    */
    @NotBlank(message = "角色名称不能为空！")
    private String roleName;

    /**
    * 角色状态：1-启用 2-禁用
    */
    private Integer status;

    /**
    * 备注
    */
    private String remark;

    public void addPrefix() {
        removePrefix();
        this.roleCode = Constants.ROLE_PREFIX + this.roleCode;
    }

    public void removePrefix() {
        this.roleCode = roleCode.replaceAll(Constants.ROLE_PREFIX, "");
    }
}