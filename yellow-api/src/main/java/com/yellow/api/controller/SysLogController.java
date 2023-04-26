package com.yellow.api.controller;

import com.yellow.api.model.SysOperateLog;
import com.yellow.api.service.SysLogService;
import com.yellow.common.entity.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys/log")
@Api(tags = "操作日志")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    @ApiOperation("分页查询列表数据")
    @PreAuthorize("hasRole('ROLE_ADMIN') and hasAuthority('sys:log:list')")
    @PostMapping(value = "/page/list/{page}/{size}")
    public QueryResponseResult<SysOperateLog> pageList(@PathVariable("page") int page, @PathVariable("size") int size) {
        return sysLogService.pageList(page, size);
    }

}
