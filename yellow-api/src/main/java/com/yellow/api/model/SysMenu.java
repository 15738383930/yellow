package com.yellow.api.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;

import com.yellow.common.entity.BaseEntity;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 系统菜单/权限表
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_menu")
public class SysMenu extends BaseEntity {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 权限代码
     */
    private String authCode;

    /**
     * 菜单名称
     */
    @TableField(value = "menu_name")
    @NotBlank(message = "菜单名称不能为空！")
    private String menuName;

    /**
     * 菜单URL
     */
    @TableField(value = "url")
    private String url;

    /**
     * 菜单类型 0-目录 1-菜单 2-按钮
     */
    @TableField(value = "`type`")
    @NotNull(message = "菜单类型不能为空！")
    private Integer type;

    /**
     * 菜单图标
     */
    @TableField(value = "icon")
    private String icon;

    /**
     * 排序
     */
    @TableField(value = "sort")
    private Integer sort;

    /**
     * 父菜单id (0-一级菜单）
     */
    @TableField(value = "parent_id")
    @NotNull(message = "上级菜单不能为空！")
    private Integer parentId;

    /**
     * 是否隐藏 1-是 0-否
     */
    @TableField(value = "is_hidden")
    private Integer isHidden;
}