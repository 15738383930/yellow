/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.yellow.api.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageSerializable;
import com.yellow.api.mapper.SysRoleMapper;
import com.yellow.api.mapper.SysRoleMenuMapper;
import com.yellow.api.mapper.SysUserMapper;
import com.yellow.api.mapper.SysUserRoleMapper;
import com.yellow.api.model.SysRole;
import com.yellow.api.model.SysRoleMenu;
import com.yellow.api.model.SysUserRole;
import com.yellow.api.model.code.SysRoleCode;
import com.yellow.api.model.ext.SysRoleExt;
import com.yellow.api.model.request.QuerySysRoleRequest;
import com.yellow.api.model.response.SysCode;
import com.yellow.api.util.SecurityUtils;
import com.yellow.common.constant.Constants;
import com.yellow.common.entity.response.CommonCode;
import com.yellow.common.entity.response.QueryResponseResult;
import com.yellow.common.exception.ExceptionCast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 角色
 *
 * @author Mark sunlightcs@gmail.com
 */
@Service
public class SysRoleService extends ServiceImpl<SysRoleMapper, SysRole> {

	@Autowired
	private SysRoleMenuMapper sysRoleMenuMapper;

	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;

	@Autowired
	private SysUserMapper sysUserMapper;

	public QueryResponseResult<SysRole> pageList(int page, int size, QuerySysRoleRequest request) {
		// 加载分页组件
		PageHelper.startPage(Math.max(page, 1), Math.min((size >= 1 ? size : Constants.PAGE_SIZE_DEFAULT), Constants.PAGE_SIZE_MAX));

		PageSerializable<SysRole> pageList = new PageSerializable<>(baseMapper.findList(request));

		return QueryResponseResult.success(pageList.getList(), pageList.getTotal());
	}

    @Transactional(rollbackFor = Exception.class)
    public void saveRole(SysRoleExt role) {
        //检查权限是否越权
        checkPrems(role);

		// 判断角色是否存在
		final Integer count = baseMapper.selectCount(Wrappers.lambdaQuery(SysRole.class).eq(SysRole::getStatus, Integer.parseInt(SysRoleCode.STATUS_1.k()))
				.and(o -> o.eq(SysRole::getRoleCode, role.getRoleCode()).or().eq(SysRole::getRoleName, role.getRoleName())));
		if (count > 0) {
			ExceptionCast.cast(SysCode.ROLE_ALREADY_EXISTS);
		}

		this.save(role);

		// 得到角色id
		final Integer id = role.getId();

		// 保存角色与菜单关系
		role.getMenus().forEach(o -> sysRoleMenuMapper.insert(SysRoleMenu.builder().roleId(id).menuId(o).build()));
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(int id, SysRoleExt request) {
        //检查权限是否越权
        checkPrems(request);

		final SysRole role = getById(id);

		// 2.修改用户信息
		if (!request.getRoleCode().equals(role.getRoleCode())) {
			ExceptionCast.cast(CommonCode.INVALID_PARAM);
		}
		role.setRoleName(request.getRoleName());
		role.setRemark(request.getRemark());
		this.updateById(role);

		// 3.修改角色菜单

		// 删除历史菜单
		sysRoleMenuMapper.delete(Wrappers.lambdaQuery(SysRoleMenu.class).eq(SysRoleMenu::getRoleId, id));
		// 添加新菜单
		request.getMenus().forEach(o -> sysRoleMenuMapper.insert(SysRoleMenu.builder().roleId(id).menuId(o).build()));
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Integer[] roleIds) {
        //删除角色
        this.removeByIds(Arrays.asList(roleIds));

        //删除角色与菜单关联
        sysRoleMenuMapper.delete(Wrappers.lambdaQuery(SysRoleMenu.class).in(SysRoleMenu::getRoleId, roleIds));

        //删除角色与用户关联
		sysUserRoleMapper.delete(Wrappers.lambdaQuery(SysUserRole.class).in(SysUserRole::getRoleId, roleIds));
    }

	public SysRole getById(int id) {
		final SysRole sysRole = baseMapper.selectById(id);
		ExceptionCast.cast(Objects.isNull(sysRole), SysCode.USER_DOES_NOT_EXIST);
		return sysRole;
	}

	public List<SysRoleMenu> queryMenusByRoleId(int id) {
		return sysRoleMenuMapper.selectList(Wrappers.lambdaQuery(SysRoleMenu.class).eq(SysRoleMenu::getRoleId, id));
	}

	/**
	 * 检查权限是否越权
	 */
	private void checkPrems(SysRoleExt role){
		//如果不是超级管理员，则需要判断角色的权限是否超过自己的权限
		if(Constants.GOD.equals(SecurityUtils.getCurrentUsername())){
			return;
		}

		//查询用户所拥有的菜单列表
		List<Integer> menuIdList = sysUserMapper.findAllMenuIdById(SecurityUtils.getCurrentUser().getId());

		//判断是否越权
		if(!menuIdList.containsAll(role.getMenus())){
			ExceptionCast.cast(SysCode.EDIT_ROLE_UNAUTHORIZED);
		}
	}
}
