package com.yellow.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yellow.api.model.SysUser;
import com.yellow.api.model.request.QuerySysUserRequest;

import java.util.List;

public interface SysUserMapper extends BaseMapper<SysUser> {


    List<Integer> findAllMenuIdById(Integer id);

    List<SysUser> findList(QuerySysUserRequest request);
}