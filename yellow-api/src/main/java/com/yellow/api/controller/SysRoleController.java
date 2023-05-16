/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.yellow.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yellow.api.model.SysRole;
import com.yellow.api.model.SysRoleMenu;
import com.yellow.api.model.ext.SysRoleExt;
import com.yellow.api.model.request.QuerySysRoleRequest;
import com.yellow.api.service.SysRoleService;
import com.yellow.common.constant.Constants;
import com.yellow.common.entity.response.ObjectResponseResult;
import com.yellow.common.entity.response.QueryResponseResult;
import com.yellow.common.entity.response.ResponseResult;
import com.yellow.common.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色管理
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends BaseController {

	@Autowired
	private SysRoleService sysRoleService;

	/**
	 * 角色列表
	 */
	@PreAuthorize("hasAuthority('sys:role:list')")
	@GetMapping(value = "/page/list/{page}/{size}")
	public QueryResponseResult<SysRole> pageList(@PathVariable("page") int page, @PathVariable("size") int size, QuerySysRoleRequest request) {
		super.setDataPermissions(request);
		return sysRoleService.pageList(page, size, request);
	}
	
	/**
	 * 角色列表
	 */
	@GetMapping("/select")
	@PreAuthorize("hasAuthority('sys:role:select')")
	public QueryResponseResult<SysRole> select(){
		QuerySysRoleRequest request = new QuerySysRoleRequest();
		super.setDataPermissions(request);
		final LambdaQueryWrapper<SysRole> lambdaQuery = Wrappers.lambdaQuery(SysRole.class);
		if (StringUtils.isNotEmpty(request.getCreateBy())) {
			lambdaQuery.eq(SysRole::getCreateBy, request.getCreateBy());
		}
		List<SysRole> list = sysRoleService.list(lambdaQuery);
		return QueryResponseResult.success(list, list.size());
	}
	
	/**
	 * 角色信息
	 */
	@GetMapping("/info/{roleId}")
	@PreAuthorize("hasAuthority('sys:role:info')")
	public ObjectResponseResult<SysRoleExt> info(@PathVariable("roleId") Integer roleId){
		SysRole role = sysRoleService.getById(roleId);

		SysRoleExt o = new SysRoleExt();
		BeanUtils.copyProperties(role, o);
		o.removePrefix();
		
		//查询角色对应的菜单
		List<Integer> menuIdList = sysRoleService.queryMenusByRoleId(roleId).stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());
		o.setMenus(menuIdList);
		
		return ObjectResponseResult.success(o);
	}
	
	/**
	 * 保存角色
	 */
	@PostMapping("/save")
	@PreAuthorize("hasAuthority('sys:role:save')")
	public ResponseResult save(@Valid @RequestBody SysRoleExt role){
		role.addPrefix();
		sysRoleService.saveRole(role);

		return ResponseResult.success();
	}
	
	/**
	 * 修改角色
	 */
	@PostMapping("/update/{id}")
	@PreAuthorize("hasAuthority('sys:role:update')")
	public ResponseResult update(@PathVariable("id") Integer id, @Valid @RequestBody SysRoleExt role){
		role.addPrefix();
		sysRoleService.update(id, role);

		return ResponseResult.success();
	}
	
	/**
	 * 删除角色
	 */
	@PostMapping("/delete")
	@PreAuthorize("hasAuthority('sys:role:delete')")
	public ResponseResult delete(@RequestBody Integer[] roleIds){
		sysRoleService.deleteBatch(roleIds);
		return ResponseResult.success();
	}
}
