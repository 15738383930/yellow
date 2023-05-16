package com.yellow.api.model.ext;

import com.yellow.api.model.SysMenu;
import com.yellow.api.model.SysUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 系统菜单扩展模型
 * @author zhouhao
 * @date  2021/3/23 15:00
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysMenuExt extends SysMenu {

    @NotEmpty(message = "账号角色不能为空！")
    @ApiModelProperty("角色id列表")
    private List<SysMenuExt> children;

    @ApiModelProperty("角色id列表")
    private String parentName;
}