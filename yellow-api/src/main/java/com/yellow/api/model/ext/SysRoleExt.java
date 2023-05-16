package com.yellow.api.model.ext;

import com.baomidou.mybatisplus.annotation.TableField;
import com.yellow.api.model.SysRole;
import com.yellow.api.model.SysUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 系统用户扩展类
 * @author zhouhao
 * @date  2021/3/23 15:00
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SysRoleExt extends SysRole {

    private static final long serialVersionUID = 258157731761950902L;

    @NotEmpty(message = "权限菜单不能为空！")
    @ApiModelProperty("权限菜单id列表")
    private List<Integer> menus;
}