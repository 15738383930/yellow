package com.yellow.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
    * 系统用户角色关联表
    */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysUserRole {
    private Integer userId;

    private Integer roleId;
}