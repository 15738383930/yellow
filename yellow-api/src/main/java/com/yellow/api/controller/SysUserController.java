/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.yellow.api.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yellow.api.model.SysUser;
import com.yellow.api.model.SysUserRole;
import com.yellow.api.model.ext.SysUserExt;
import com.yellow.api.model.request.QuerySysUserRequest;
import com.yellow.api.model.response.SysCode;
import com.yellow.api.model.valid.AddGroup;
import com.yellow.api.model.valid.EditGroup;
import com.yellow.api.service.SysUserService;
import com.yellow.api.util.SecurityUtils;
import com.yellow.common.constant.Constants;
import com.yellow.common.entity.response.ObjectResponseResult;
import com.yellow.common.entity.response.QueryResponseResult;
import com.yellow.common.entity.response.ResponseResult;
import com.yellow.common.exception.ExceptionCast;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "用户管理")
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends BaseController {

    @Autowired
    private SysUserService sysUserService;

    @ApiOperation("分页查询列表数据")
    @PreAuthorize("hasAuthority('sys:user:list')")
    @GetMapping(value = "/page/list/{page}/{size}")
    public QueryResponseResult<SysUser> pageList(@PathVariable("page") int page, @PathVariable("size") int size, QuerySysUserRequest request) {
        super.setDataPermissions(request);
        return sysUserService.pageList(page, size, request);
    }

    /**
     * 用户信息
     */
    @ApiOperation("用户信息")
    @GetMapping("/info/{userId}")
    @PreAuthorize("hasAuthority('sys:user:info')")
    public ObjectResponseResult<SysUserExt> info(@PathVariable("userId") Integer userId){
        SysUser user = sysUserService.getById(userId);

        SysUserExt o = new SysUserExt();
        BeanUtils.copyProperties(user, o);

        //获取用户所属的角色列表
        List<Integer> roleIdList = sysUserService.queryRolesByUserId(userId).stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
        o.setRoles(roleIdList);

        return ObjectResponseResult.success(o);
    }

    /**
     * 保存用户
     */
    @ApiOperation("保存用户")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:user:save')")
    public ResponseResult save(@Validated(AddGroup.class) @RequestBody SysUserExt user){
        sysUserService.save(user);
        return ResponseResult.success();
    }

    @ApiOperation("重置密码")
    @PostMapping("/reset/{id}")
    @PreAuthorize("hasAuthority('sys:user:reset')")
    public ResponseResult reset(@PathVariable("id") Integer id){
        sysUserService.resetPassword(id);
        return ResponseResult.success();
    }

    @ApiOperation("修改用户状态")
    @PostMapping("/change/status/{id}")
    @PreAuthorize("hasAuthority('sys:user:changestatus')")
    public ResponseResult changeStatus(@PathVariable("id") Integer id){
        sysUserService.changeStatus(id);
        return ResponseResult.success();
    }

    /**
     * 修改用户
     */
    @ApiOperation("修改用户")
    @PostMapping("/update/{id}")
    @PreAuthorize("hasAuthority('sys:user:update')")
    public ResponseResult update(@PathVariable("id") Integer id, @Validated(EditGroup.class) @RequestBody SysUserExt user){
        sysUserService.update(id, user);
        return ResponseResult.success();
    }

    /**
     * 删除用户
     */
    @ApiOperation("删除用户")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sys:user:delete')")
    public ResponseResult delete(@RequestBody Integer[] userIds){
        final SysUser one = sysUserService.getOne(Wrappers.lambdaQuery(SysUser.class).eq(SysUser::getUsername, Constants.GOD));
        if(ArrayUtils.contains(userIds, one.getId())){
            ExceptionCast.cast(SysCode.SUPER_ADMIN_CANNOT_BE_DELETED);
        }

        if(ArrayUtils.contains(userIds, SecurityUtils.getCurrentUser().getId())){
            ExceptionCast.cast(SysCode.CURRENT_USER_CANNOT_BE_DELETED);
        }

        sysUserService.removeByIds(Arrays.asList(userIds));

        return ResponseResult.success();
    }

}
