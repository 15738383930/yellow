package com.yellow.api.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageSerializable;
import com.yellow.api.mapper.SysDetailLogMapper;
import com.yellow.api.mapper.SysLoginLogMapper;
import com.yellow.api.mapper.SysOperateLogMapper;
import com.yellow.api.model.SysDetailLog;
import com.yellow.api.model.SysLoginLog;
import com.yellow.api.model.SysOperateLog;
import com.yellow.common.constant.Constants;
import com.yellow.common.entity.response.QueryResponseResult;
import com.yellow.common.util.RedisUtils;
import com.yellow.common.util.SystemUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class SysLogService {

    @Resource
    private SysLoginLogMapper sysLoginLogMapper;

    @Resource
    private SysDetailLogMapper sysDetailLogMapper;

    @Resource
    private SysOperateLogMapper sysOperateLogMapper;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private HttpServletRequest request;

    public QueryResponseResult<SysOperateLog> pageList(int page, int size) {
        // 加载分页组件
        PageHelper.startPage(Math.max(page, 1), Math.min((size >= 1 ? size : Constants.PAGE_SIZE_DEFAULT), Constants.PAGE_SIZE_MAX));

        PageSerializable<SysOperateLog> pageList = new PageSerializable<>(sysOperateLogMapper.selectList(Wrappers.lambdaQuery(SysOperateLog.class).orderByDesc(SysOperateLog::getOperateTime)));

        return QueryResponseResult.success(pageList.getList(), pageList.getTotal());
    }

    /**
     * 保存登录日志
     *
     * @param result
     */
    public void saveLoginLog(SysLoginLog result) {
        String ip = SystemUtils.getIp(request);

        result.setIp(ip);
        result.setAddress(SystemUtils.getAddress(ip));
        result.setBrowser(SystemUtils.getBrowser(request));
        result.setOs(SystemUtils.getOs(request));
        result.setLoginTime(new Date());

        sysLoginLogMapper.insert(result);
    }

    /**
     * 保存异常日志
     *
     * @param sysDetailLog
     */
    public void saveDetailLog(SysDetailLog sysDetailLog) {
        sysDetailLogMapper.insert(sysDetailLog);
    }

}
