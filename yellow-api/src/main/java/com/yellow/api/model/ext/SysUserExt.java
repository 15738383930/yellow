package com.yellow.api.model.ext;

import com.yellow.api.model.SysMenu;
import com.yellow.api.model.SysUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * 系统用户扩展类
 * @author zhouhao
 * @date  2021/3/23 15:00
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SysUserExt extends SysUser {

    private static final long serialVersionUID = 258157731761950902L;

    // 权限信息
    @ApiModelProperty(hidden = true)
    private List<SysMenu> permissions;

    // 角色名称
    private String roleName;
}