package com.yellow.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yellow.api.model.SysMenu;
import com.yellow.api.model.ext.SysMenuExt;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysMenuMapper extends BaseMapper<SysMenu> {
    /**
     * 根据用户id查询用户权限列表
     *
     * @param userId 用户id
     * @return
     * @author zhouhao
     * @date 2021/4/2 16:08
     */
    List<SysMenu> selectPermissionByUserId(@Param("userId") Integer userId);

    List<SysMenu> selectAllMenu();

    List<SysMenuExt> menuTree(@Param("userId") Integer userId, @Param("menuIds") List<Integer> menuIds);
}