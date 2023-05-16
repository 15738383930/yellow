package com.yellow.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yellow.api.model.SysRole;
import com.yellow.api.model.SysUser;
import com.yellow.api.model.request.QuerySysRoleRequest;
import com.yellow.api.model.request.QuerySysUserRequest;
import org.apache.ibatis.annotations.Param;

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
    List<SysRole> selectRoleByUserId(@Param("userId") Integer userId);

    List<SysRole> findList(QuerySysRoleRequest request);
}