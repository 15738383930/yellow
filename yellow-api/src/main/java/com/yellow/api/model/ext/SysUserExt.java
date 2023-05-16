package com.yellow.api.model.ext;

import com.yellow.api.model.SysUser;
import com.yellow.api.model.valid.AddGroup;
import com.yellow.api.model.valid.EditGroup;
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
public class SysUserExt extends SysUser {

    private static final long serialVersionUID = 258157731761950902L;

    @NotEmpty(message = "账号角色不能为空！", groups = {AddGroup.class, EditGroup.class})
    @ApiModelProperty("角色id列表")
    private List<Integer> roles;
}