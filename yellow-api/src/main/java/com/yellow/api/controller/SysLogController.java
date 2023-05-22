package com.yellow.api.controller;

import com.yellow.api.model.SysOperateLog;
import com.yellow.api.model.request.QuerySysLogRequest;
import com.yellow.api.service.SysLogService;
import com.yellow.common.entity.response.ObjectResponseResult;
import com.yellow.common.entity.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys/log")
@Api(tags = "系统日志")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    @ApiOperation("分页查询列表数据")
    @PreAuthorize("hasAuthority('sys:log:list')")
    @GetMapping(value = "/page/list/{page}/{size}")
    public QueryResponseResult<SysOperateLog> pageList(@PathVariable("page") int page, @PathVariable("size") int size, QuerySysLogRequest request) {
        return sysLogService.pageList(page, size, request);
    }

    @ApiOperation("异常详情")
    @PreAuthorize("hasAuthority('sys:log:details')")
    @GetMapping(value = "/details/{type}/{id}")
    public ObjectResponseResult<String> details(@PathVariable("type") int type, @PathVariable("id") int id) {
        return sysLogService.details(type, id);
    }

}
