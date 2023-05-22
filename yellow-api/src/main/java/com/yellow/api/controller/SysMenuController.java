/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.yellow.api.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yellow.api.model.SysMenu;
import com.yellow.api.model.code.SysMenuCode;
import com.yellow.api.model.ext.SysMenuExt;
import com.yellow.api.model.response.NavigationResult;
import com.yellow.api.model.response.SysCode;
import com.yellow.api.service.SysMenuService;
import com.yellow.api.util.SecurityUtils;
import com.yellow.common.entity.response.ObjectResponseResult;
import com.yellow.common.entity.response.QueryResponseResult;
import com.yellow.common.entity.response.ResponseResult;
import com.yellow.common.exception.ExceptionCast;
import com.yellow.common.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "系统菜单")
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends BaseController {

	@Resource
	private SysMenuService sysMenuService;

	@ApiOperation("导航菜单")
	@GetMapping("/nav")
	public NavigationResult nav(){
		List<SysMenuExt> menuList = sysMenuService.getUserMenuList();
		return NavigationResult.success(menuList, SecurityUtils.getCurrentUser().getCodes());
	}

	@ApiOperation("所有菜单列表")
	@GetMapping("/list")
	@PreAuthorize("hasAuthority('sys:menu:list')")
	public QueryResponseResult<SysMenuExt> list(){
		List<SysMenuExt> result = new ArrayList<>();
		List<SysMenu> menuList = sysMenuService.list(Wrappers.lambdaQuery(SysMenu.class).orderByAsc(SysMenu::getSort));
		for(SysMenu sysMenuEntity : menuList){
			SysMenuExt o = new SysMenuExt();
			BeanUtils.copyProperties(sysMenuEntity, o);
			SysMenu parentMenuEntity = sysMenuService.getById(sysMenuEntity.getParentId());
			if(parentMenuEntity != null){
				o.setParentName(parentMenuEntity.getMenuName());
			}
			result.add(o);
		}

		return QueryResponseResult.success(result, result.size());
	}

	@ApiOperation("选择菜单(添加、修改菜单)")
	@GetMapping("/select")
	@PreAuthorize("hasAuthority('sys:menu:select')")
	public QueryResponseResult<SysMenu> select(){
		//查询列表数据
		List<SysMenu> menuList = sysMenuService.queryNotButtonList();

		//添加顶级菜单
		SysMenu root = new SysMenu();
		root.setId(0);
		root.setMenuName("一级菜单");
		root.setParentId(-1);
		root.setIsHidden(Integer.parseInt(SysMenuCode.IS_HIDDEN_0.k()));
		menuList.add(root);

		return QueryResponseResult.success(menuList, menuList.size());
	}

	@ApiOperation("菜单信息")
	@GetMapping("/info/{menuId}")
	@PreAuthorize("hasAuthority('sys:menu:info')")
	public ObjectResponseResult<SysMenu> info(@PathVariable("menuId") Integer menuId){
		return ObjectResponseResult.success(sysMenuService.getById(menuId));
	}

	@ApiOperation("保存")
	@PostMapping("/save")
	@PreAuthorize("hasAuthority('sys:menu:save')")
	public ResponseResult save(@Valid @RequestBody SysMenu menu){
		//数据校验
		verifyForm(menu);

		// 唯一性校验
		final int count = sysMenuService.count(Wrappers.lambdaQuery(SysMenu.class).eq(SysMenu::getMenuName, menu.getMenuName())
				.eq(SysMenu::getType, menu.getType()).eq(SysMenu::getParentId, menu.getParentId()));
		if (count > 0) {
			ExceptionCast.cast(SysCode.MENU_ALREADY_EXISTS);
		}

		menu.setId(null);
		sysMenuService.save(menu);

		return ResponseResult.success();
	}

	@ApiOperation("修改")
	@PostMapping("/update/{menuId}")
	@PreAuthorize("hasAuthority('sys:menu:update')")
	public ResponseResult update(@PathVariable("menuId") Integer menuId, @Valid @RequestBody SysMenu menu){
		//数据校验
		verifyForm(menu);

		sysMenuService.update(menuId, menu);

		return ResponseResult.success();
	}

	@ApiOperation("删除")
	@PostMapping("/delete/{menuId}")
	@PreAuthorize("hasAuthority('sys:menu:delete')")
	public ResponseResult delete(@PathVariable("menuId") Integer menuId){
		if(menuId <= 31){
			ExceptionCast.cast(SysCode.SYS_MENU_CANNOT_BE_DELETED);
		}

		//判断是否有子菜单或按钮
		final int count = sysMenuService.count(Wrappers.lambdaQuery(SysMenu.class).eq(SysMenu::getParentId, menuId));
		if(count > 0){
			ExceptionCast.cast(SysCode.THERE_ARE_SUBMENUS_UNDER_THE_MENU);
		}

		sysMenuService.removeById(menuId);

		return ResponseResult.success();
	}

	/**
	 * 验证参数是否正确
	 */
	private void verifyForm(SysMenu menu){

		// 菜单
		if(menu.getType().toString().equals(SysMenuCode.MENU_LEVEL_1.k())){
			if(StringUtils.isEmpty(menu.getUrl())){
				ExceptionCast.cast(SysCode.MENU_URL_NOT_NULL);
			}
		}

		int parentType = Integer.parseInt(SysMenuCode.MENU_LEVEL_0.k());

		// 上级菜单类型
		if(menu.getParentId() != 0){
			SysMenu parentMenu = sysMenuService.getById(menu.getParentId());
			parentType = parentMenu.getType();
		}

		// 目录、菜单
		if(menu.getType() == Integer.parseInt(SysMenuCode.MENU_LEVEL_0.k()) ||
				menu.getType() == Integer.parseInt(SysMenuCode.MENU_LEVEL_1.k())){
			if(parentType != Integer.parseInt(SysMenuCode.MENU_LEVEL_0.k())){
				ExceptionCast.cast(SysCode.PARENT_MENU_CAN_ONLY_OF_DIRECTORY_TYPE);
			}
			return;
		}

		// 按钮
		if(menu.getType() == Integer.parseInt(SysMenuCode.MENU_LEVEL_2.k())){
			if(parentType != Integer.parseInt(SysMenuCode.MENU_LEVEL_1.k())){
				ExceptionCast.cast(SysCode.PARENT_MENU_CAN_ONLY_OF_MENU_TYPE);
			}
			return;
		}
	}
}
