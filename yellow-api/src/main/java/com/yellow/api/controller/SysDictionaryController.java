/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.yellow.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yellow.api.model.SysDictionary;
import com.yellow.api.model.SysRole;
import com.yellow.api.model.ext.SysDictionaryExt;
import com.yellow.api.model.request.QuerySysDictionaryRequest;
import com.yellow.api.model.request.QuerySysRoleRequest;
import com.yellow.api.service.SysDictionaryService;
import com.yellow.common.entity.response.ObjectResponseResult;
import com.yellow.common.entity.response.QueryResponseResult;
import com.yellow.common.entity.response.ResponseResult;
import com.yellow.common.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("sys/dictionary")
@Api(tags="字典管理")
public class SysDictionaryController {

    @Autowired
    private SysDictionaryService sysDictionaryService;

    @ApiOperation("分页查询列表数据")
    @PreAuthorize("hasAuthority('sys:dictionary:list')")
    @GetMapping(value = "/page/list/{page}/{size}")
    public QueryResponseResult<SysDictionary> pageList(@PathVariable("page") int page, @PathVariable("size") int size, QuerySysDictionaryRequest request) {
        return sysDictionaryService.pageList(page, size, request);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    @PreAuthorize("hasAuthority('sys:dictionary:info')")
    public ObjectResponseResult<SysDictionary> get(@PathVariable("id") Long id){
        return ObjectResponseResult.success(sysDictionaryService.findById(id));
    }

    @PostMapping
    @ApiOperation("保存")
    @PreAuthorize("hasAuthority('sys:dictionary:save')")
    public ResponseResult save(@Valid @RequestBody SysDictionary request){
        sysDictionaryService.add(request);
        return ResponseResult.success();
    }

    @PutMapping("{id}")
    @ApiOperation("修改")
    @PreAuthorize("hasAuthority('sys:dictionary:update')")
    public ResponseResult update(@PathVariable("id") Long id, @Valid @RequestBody SysDictionary request){
        sysDictionaryService.update(id, request);

        return ResponseResult.success();
    }

    @DeleteMapping
    @ApiOperation("删除")
    @PreAuthorize("hasAuthority('sys:dictionary:delete')")
    public ResponseResult delete(@RequestBody Long[] ids){
        sysDictionaryService.delete(ids);

        return ResponseResult.success();
    }

    @ApiOperation("字典列表")
    @GetMapping("/select")
    @PreAuthorize("hasAuthority('sys:dictionary:select')")
    public QueryResponseResult<SysDictionary> select(){
        List<SysDictionary> list = sysDictionaryService.list();
        return QueryResponseResult.success(list, list.size());
    }

    @GetMapping("all")
    @ApiOperation("所有字典数据")
    public QueryResponseResult<SysDictionaryExt> all(){
        List<SysDictionaryExt> list = sysDictionaryService.getAllList();

        return QueryResponseResult.success(list, list.size());
    }

}