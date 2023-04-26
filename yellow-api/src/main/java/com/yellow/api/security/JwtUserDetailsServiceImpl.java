package com.yellow.api.security;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yellow.api.mapper.SysMenuMapper;
import com.yellow.api.mapper.SysRoleMapper;
import com.yellow.api.mapper.SysUserMapper;
import com.yellow.api.model.SysMenu;
import com.yellow.api.model.SysRole;
import com.yellow.api.model.SysUser;
import com.yellow.api.model.code.SysUserCode;
import com.yellow.api.model.response.AuthCode;
import com.yellow.common.constant.Constants;
import com.yellow.common.exception.ExceptionCast;
import com.yellow.common.util.RedisUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 登陆身份认证
 *
 * @author danny
 * @date 2020年6月2日
 */
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Resource
    private RedisUtils redisUtils;

    /**
     * 加载UserDetails信息
     *
     * @param username
     * @return
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        String key = Constants.LOGIN_TOKEN_KEY + username;
        if (redisUtils.exists(key)) {
            return JSON.parseObject(redisUtils.get(key), JwtUserDetails.class);
        }

        SysUser user = sysUserMapper.selectOne(Wrappers.lambdaQuery(SysUser.class).eq(SysUser::getUsername, username)
                .eq(SysUser::getStatus, Integer.parseInt(SysUserCode.STATUS_1.getK()))
                .eq(SysUser::getIsDel, Integer.parseInt(SysUserCode.IS_DEL_0.getK())));
        if (user == null) {
            ExceptionCast.cast(AuthCode.AUTH_CREDENTIAL_ERROR);
        }
        // 当前用户所拥有的角色和权限集合
        final List<String> codes = Stream.concat(sysRoleMapper.selectRoleByUserId(user.getId()).stream().map(SysRole::getRoleCode),
                sysMenuMapper.selectPermissionByUserId(user.getId()).stream().map(SysMenu::getCode)).collect(Collectors.toList());
        return JwtUserDetails.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .id(user.getId())
                .name(user.getName())
                // 权限code集合
                .codes(codes)
                .build();
    }
}
