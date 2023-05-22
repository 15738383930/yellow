package com.yellow.api.service;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yellow.api.mapper.SysMenuMapper;
import com.yellow.api.mapper.SysUserMapper;
import com.yellow.api.model.SysMenu;
import com.yellow.api.model.ext.SysMenuExt;
import com.yellow.api.model.response.SysCode;
import com.yellow.api.util.SecurityUtils;
import com.yellow.common.constant.Constants;
import com.yellow.common.exception.ExceptionCast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
public class SysMenuService extends ServiceImpl<SysMenuMapper, SysMenu> {

	@Autowired
	private SysUserMapper sysUserMapper;

	@Autowired
	private SysMenuMapper sysMenuMapper;
	
	public List<SysMenuExt> getUserMenuList() {
		//系统管理员，拥有最高权限
		Integer userId = SecurityUtils.getCurrentUser().getId();
		List<Integer> menuIds = null;
		if(Constants.GOD.equals(SecurityUtils.getCurrentUsername())){
			userId = null;
		}else{
			menuIds = sysUserMapper.findAllMenuIdById(userId);
		}
		
		//用户菜单列表
		return sysMenuMapper.menuTree(userId, menuIds);
	}

	public List<SysMenu> queryNotButtonList() {
		return baseMapper.selectAllMenu();
	}

	public void update(Integer menuId, SysMenu menu) {
		// 1. 合法性校验
		final SysMenu sysMenu = baseMapper.selectById(menuId);
		if (Objects.isNull(sysMenu)) {
			ExceptionCast.cast(SysCode.MENU_DOES_NOT_EXIST);
		}

		// 2. 填充修改数据
		sysMenu.setMenuName(menu.getMenuName());
		sysMenu.setParentId(menu.getParentId());
		sysMenu.setUrl(menu.getUrl());
		sysMenu.setAuthCode(menu.getAuthCode());
		sysMenu.setSort(menu.getSort());
		sysMenu.setIcon(menu.getIcon());

		// 3. 修改信息
		baseMapper.updateById(menu);
	}
}
