package com.yellow.api.model;

import java.util.Date;

import com.yellow.common.entity.BaseEntity;
import lombok.*;

/**
    * 系统菜单/权限表
    */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysMenu extends BaseEntity {
    private Integer id;

    /**
    * 权限代码
    */
    private String code;

    /**
    * 菜单名称
    */
    private String name;

    /**
    * 菜单路由
    */
    private String routing;

    /**
    * 菜单层级 0-目录 1-菜单 2-按钮
    */
    private Integer level;

    /**
    * 菜单图标
    */
    private String icon;

    /**
    * 排序
    */
    private Integer sort;

    /**
    * 父菜单id (0-一级菜单）
    */
    private Integer parentId;

    /**
    * 是否隐藏 1-是 0-否
    */
    private Integer isHidden;
}