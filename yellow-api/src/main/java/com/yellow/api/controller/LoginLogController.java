package com.yellow.api.controller;

import com.yellow.api.model.ext.LoginLogExt;
import com.yellow.api.model.request.QueryLoginLogRequest;
import com.yellow.api.service.LoginLogService;
import com.yellow.common.entity.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 系统登录日志
 *
 * @author Hao.
 * @email 15738383930@163.com
 * @since 2023-05-22 09:15:14
 */
@Api(tags = "系统登录日志")
@RestController
@RequestMapping("/sys/loginlog")
public class LoginLogController {

    @Resource
    private LoginLogService loginLogService;

    @ApiOperation("分页查询列表数据")
    @GetMapping("/page/list/{page}/{size}")
    @PreAuthorize("hasAuthority('sys:loginlog:list')")
    public QueryResponseResult<LoginLogExt> pageList(@PathVariable("page") int page, @PathVariable("size") int size, QueryLoginLogRequest request){
        return loginLogService.pageList(page, size, request);
    }

}
