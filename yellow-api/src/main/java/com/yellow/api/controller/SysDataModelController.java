/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.yellow.api.controller;

import com.yellow.api.model.SysDataModel;
import com.yellow.api.model.ext.SysDataModelExt;
import com.yellow.api.service.SysDataModelService;
import com.yellow.common.entity.response.ObjectResponseResult;
import com.yellow.common.entity.response.QueryResponseResult;
import com.yellow.common.entity.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;

@RestController
@RequestMapping("sys/datamodel")
@Api(tags="数据模型")
public class SysDataModelController {

    @Autowired
    private SysDataModelService sysDictionaryService;

    @ApiOperation("分页查询列表数据")
    @PreAuthorize("hasAuthority('sys:datamodel:list')")
    @GetMapping(value = "/page/list/{page}/{size}")
    public QueryResponseResult<SysDataModel> pageList(@PathVariable("page") int page, @PathVariable("size") int size) {
        return sysDictionaryService.pageList(page, size);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    @PreAuthorize("hasAuthority('sys:datamodel:info')")
    public ObjectResponseResult<SysDataModelExt> get(@PathVariable("id") Integer id){
        return ObjectResponseResult.success(sysDictionaryService.findById(id, true));
    }

    @PostMapping
    @ApiOperation("保存")
    @PreAuthorize("hasAuthority('sys:datamodel:save')")
    public ResponseResult save(@Valid @RequestBody SysDataModelExt request){
        sysDictionaryService.add(request);
        return ResponseResult.success();
    }

    @PutMapping("{id}")
    @ApiOperation("修改")
    @PreAuthorize("hasAuthority('sys:datamodel:update')")
    public ResponseResult update(@PathVariable("id") Integer id, @Valid @RequestBody SysDataModelExt request){
        sysDictionaryService.update(id, request.getModelType(), request);

        return ResponseResult.success();
    }

    @DeleteMapping
    @ApiOperation("删除")
    @PreAuthorize("hasAuthority('sys:datamodel:delete')")
    public ResponseResult delete(@RequestBody Long[] ids){
        sysDictionaryService.removeByIds(Arrays.asList(ids));

        return ResponseResult.success();
    }

}