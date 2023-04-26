package com.yellow.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yellow.api.model.SysRole;

import java.util.List;

public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 根据菜单id查询角色列表
     * @param menuId 菜单id
     * @return
     * @author zhouhao
     * @date  2021/4/2 16:08
     */
    List<SysRole> selectRoleByMenuId(Integer menuId);

    /**
     * 根据用户id查询角色列表
     * @param userId 用户id
     * @return
     * @author zhouhao
     * @date  2021/4/2 16:08
     */
    List<SysRole> selectRoleByUserId(Integer userId);
}