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
import com.yellow.api.mapper.SysUserMapper;
import com.yellow.api.mapper.SysUserRoleMapper;
import com.yellow.api.model.SysRole;
import com.yellow.api.model.SysUser;
import com.yellow.api.model.SysUserRole;
import com.yellow.api.model.code.SysUserCode;
import com.yellow.api.model.ext.SysUserExt;
import com.yellow.api.model.request.QuerySysUserRequest;
import com.yellow.api.model.response.SysCode;
import com.yellow.api.security.JwtUserDetails;
import com.yellow.api.util.SecurityUtils;
import com.yellow.common.constant.Constants;
import com.yellow.common.entity.code.RoleCode;
import com.yellow.common.entity.response.CommonCode;
import com.yellow.common.entity.response.QueryResponseResult;
import com.yellow.common.exception.ExceptionCast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * 系统用户
 *
 * @author Mark sunlightcs@gmail.com
 */
@Service
public class SysUserService extends ServiceImpl<SysUserMapper, SysUser> {

	@Autowired
	private SysRoleMapper sysRoleMapper;

	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;

	@Transactional(rollbackFor = Exception.class)
	public void save(SysUserExt request) {
		//检查角色是否越权
		checkRole(request.getRoles());
		// 1. 添加用户信息

		// 判断用户是否存在
		final Integer count = baseMapper.selectCount(Wrappers.lambdaQuery(SysUser.class)
				.eq(SysUser::getStatus, Integer.parseInt(SysUserCode.STATUS_1.getK()))
				.eq(SysUser::getIsDel, Integer.parseInt(SysUserCode.IS_DEL_0.getK()))
				.eq(SysUser::getUsername, request.getUsername()));
		if (count > 0) {
			ExceptionCast.cast(SysCode.USER_ALREADY_EXISTS);
		}

		request.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));
		baseMapper.insert(request);

		// 得到用户id
		final Integer id = request.getId();

		// 2. 添加用户角色关联信息
		request.getRoles().removeIf(o -> o <= 0);
		request.getRoles().forEach(o -> sysUserRoleMapper.insert(SysUserRole.builder().userId(id).roleId(o).build()));
	}

	@Transactional(rollbackFor = Exception.class)
	public void update(int id, SysUserExt request) {
		// 1.检查角色是否越权
		checkRole(request.getRoles());

		// 2.修改用户信息
		final SysUser sysUser = getById(id);
		if (!request.getUsername().equals(sysUser.getUsername())) {
			ExceptionCast.cast(CommonCode.INVALID_PARAM);
		}

		sysUser.setName(request.getName());
		sysUser.setPhone(request.getPhone());
		sysUser.setRemark(request.getRemark());
		this.updateById(sysUser);

		// 3.修改用户角色

		// 删除历史角色
		sysUserRoleMapper.delete(Wrappers.lambdaQuery(SysUserRole.class).eq(SysUserRole::getUserId, id));
		// 添加新角色
		request.getRoles().removeIf(o -> o <= 0);
		request.getRoles().forEach(o -> sysUserRoleMapper.insert(SysUserRole.builder().userId(id).roleId(o).build()));

	}

	/**
	 * 设置用户的最后一次登录时间
	 * @return void
	 * @author zhouhao
	 * @date  2021/4/1 15:50
	 */
	public void setLastLoginTime(){
		SysUser user = baseMapper.selectById(SecurityUtils.getCurrentUser().getId());
		user.setLastLoginTime(new Date());
		baseMapper.updateById(user);
	}
	
	/**
	 * 检查角色是否越权
	 */
	private void checkRole(List<Integer> roles){
		if(CollectionUtils.isEmpty(roles)){
			return;
		}
		// 神！创建的用户
		if(Constants.GOD.equals(SecurityUtils.getCurrentUsername())){
			return;
		}
		
		// 不是神创建的角色，需要验证是否越权（是否超过当前用户所能赋予角色的范围）
		final List<Integer> roleIdList = sysUserRoleMapper.selectList(Wrappers.lambdaQuery(SysUserRole.class).eq(SysUserRole::getUserId, SecurityUtils.getCurrentUser().getId()))
				.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());

		// 验证越权
		if(!roleIdList.containsAll(roles)){
			ExceptionCast.cast(SysCode.EDIT_USER_UNAUTHORIZED);
		}
	}

	public SysUser getById(int id) {
		final SysUser user = baseMapper.selectById(id);
		ExceptionCast.cast(Objects.isNull(user), SysCode.USER_DOES_NOT_EXIST);
		return user;
	}

	public QueryResponseResult<SysUser> pageList(int page, int size, QuerySysUserRequest request) {
		// 加载分页组件
		PageHelper.startPage(Math.max(page, 1), Math.min((size >= 1 ? size : Constants.PAGE_SIZE_DEFAULT), Constants.PAGE_SIZE_MAX));

		PageSerializable<SysUser> pageList = new PageSerializable<>(baseMapper.findList(request));

		return QueryResponseResult.success(pageList.getList(), pageList.getTotal());
	}

	public List<SysUserRole> queryRolesByUserId(Integer userId) {
		return sysUserRoleMapper.selectList(Wrappers.lambdaQuery(SysUserRole.class).eq(SysUserRole::getUserId, userId));
	}

	public void resetPassword(Integer id) {
		final JwtUserDetails user = SecurityUtils.getCurrentUser();

		// 管理员、系统管理员和神 才有权限重置用户的密码
		if (!user.getRoles().contains(RoleCode.ADMIN.k())
				&& !user.getRoles().contains(RoleCode.SYSTEM.k())
				&& !user.getUsername().equals(Constants.GOD)) {
			ExceptionCast.cast(CommonCode.UNAUTHORISE);
		}

		final Set<String> roles = sysRoleMapper.selectRoleByUserId(id).stream().map(SysRole::getRoleCode).collect(Collectors.toSet());

		// 管理员、系统管理员的密码只有神能重置
		if (roles.contains(RoleCode.ADMIN.k()) || roles.contains(RoleCode.SYSTEM.k())) {
			if (!user.getUsername().equals(Constants.GOD)) {
				ExceptionCast.cast(CommonCode.UNAUTHORISE);
			}
		}

		final SysUser one = baseMapper.selectById(id);

		// 神不可动摇！
		if(Constants.GOD.equals(one.getUsername()) ){
			ExceptionCast.cast(CommonCode.GOD_UNSHAKABLE);
		}

		// 重置密码
		one.setPassword(new BCryptPasswordEncoder().encode(Constants.DEFAULT_PASSWORD));
		baseMapper.updateById(one);

	}

	public void changeStatus(Integer id) {
		final SysUser one = baseMapper.selectById(id);

		// 神不可动摇！
		if(Constants.GOD.equals(one.getUsername()) ){
			ExceptionCast.cast(CommonCode.GOD_UNSHAKABLE);
		}

		final String status = one.getStatus() == Integer.parseInt(SysUserCode.STATUS_1.getK()) ? SysUserCode.STATUS_2.getK() : SysUserCode.STATUS_1.getK();
		one.setStatus(Integer.parseInt(status));
		baseMapper.updateById(one);

	}
}