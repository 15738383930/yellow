package com.yellow.api.model.response;

import com.yellow.api.model.ext.SysMenuExt;
import com.yellow.common.entity.response.CommonCode;
import com.yellow.common.entity.response.ResponseResult;
import com.yellow.common.entity.response.ResultCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

/**
 * 登录响应结果模型
 * @author Hao.
 * @version 1.0
 * @since 2022/5/31 9:51
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ApiModel(description = "登录响应结果模型")
public class NavigationResult extends ResponseResult {

    private static final long serialVersionUID = -7839550833571948892L;

    @ApiModelProperty("菜单列表")
    List<SysMenuExt> menuList;

    @ApiModelProperty("权限列表")
    Set<String> permissions;

    public NavigationResult(ResultCode resultCode, List<SysMenuExt> menuList, Set<String> permissions) {
        super(resultCode);
        this.menuList = menuList;
        this.permissions = permissions;
    }

    public static NavigationResult success(List<SysMenuExt> menuList, Set<String> permissions){
        return new NavigationResult(CommonCode.SUCCESS, menuList, permissions);
    }
}
