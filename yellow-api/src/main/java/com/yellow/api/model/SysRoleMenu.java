package com.yellow.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
    * 系统角色菜单关联表
    */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysRoleMenu {
    private Integer roleId;

    private Integer menuId;
}