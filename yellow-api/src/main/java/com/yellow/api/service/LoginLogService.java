package com.yellow.api.service;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageSerializable;
import com.yellow.api.mapper.LoginLogMapper;
import com.yellow.api.model.LoginLog;
import com.yellow.api.model.ext.LoginLogExt;
import com.yellow.api.model.request.QueryLoginLogRequest;
import com.yellow.common.constant.Constants;
import com.yellow.common.entity.response.CommonCode;
import com.yellow.common.entity.response.QueryResponseResult;
import com.yellow.common.exception.ExceptionCast;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;


@Service
public class LoginLogService extends ServiceImpl<LoginLogMapper, LoginLog> {

    @Resource
    private LoginLogMapper loginLogMapper;

    public QueryResponseResult<LoginLogExt> pageList(int page, int size, QueryLoginLogRequest request) {
        // 加载分页组件
        PageHelper.startPage(Math.max(page, 1), Math.min((size >= 1 ? size : Constants.PAGE_SIZE_DEFAULT), Constants.PAGE_SIZE_MAX));

        PageSerializable<LoginLogExt> pageList = new PageSerializable<>(loginLogMapper.findList(request));

        return QueryResponseResult.success(pageList.getList(), pageList.getTotal());
    }

    public LoginLogExt findById(Integer id) {
        final LoginLog one = loginLogMapper.selectById(id);
        // 非法参数校验（code可自定义）
        ExceptionCast.cast(Objects.isNull(one), CommonCode.INVALID_PARAM);

        // 处理业务逻辑
        LoginLogExt result = new LoginLogExt();
        BeanUtils.copyProperties(one, result);

        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(LoginLogExt request) {
        // 唯一性校验

        // 添加
        baseMapper.insert(request);
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(int id, LoginLogExt request) {
        // 唯一性校验（根据实际需求）
        final LoginLog one = getById(id);
        if (Objects.isNull(one)) {
            // ExceptionCast.cast(XxxCode.XXX);
        }

        // 修改 (安全起见，可根据页面上能够修改的属性，逐个赋值)
        // one.setXxx(request.getXxx());
        // ...

        this.updateById(one);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(List<Integer> ids) {
        // 可进行一些逻辑处理

        // 批量删除
        this.removeByIds(ids);
    }

}