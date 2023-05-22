/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.yellow.api.controller;

import com.yellow.api.model.SysDictionaryData;
import com.yellow.api.service.SysDictionaryDataService;
import com.yellow.common.entity.response.ObjectResponseResult;
import com.yellow.common.entity.response.QueryResponseResult;
import com.yellow.common.entity.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("sys/dictionary/data")
@Api(tags="字典数据")
public class SysDictionaryDataController {
    @Autowired
    private SysDictionaryDataService sysDictionaryDataService;


    @ApiOperation("分页查询列表数据")
    @PreAuthorize("hasAuthority('sys:dictionarydata:list')")
    @GetMapping(value = "/page/list/{dictionaryid}/{page}/{size}")
    public QueryResponseResult<SysDictionaryData> pageList(@PathVariable("dictionaryid") int dictionaryid, @PathVariable("page") int page, @PathVariable("size") int size) {
        return sysDictionaryDataService.pageList(dictionaryid, page, size);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    @PreAuthorize("hasAuthority('sys:dictionarydata:info')")
    public ObjectResponseResult<SysDictionaryData> get(@PathVariable("id") Long id){
        return ObjectResponseResult.success(sysDictionaryDataService.findById(id));
    }

    @PostMapping
    @ApiOperation("保存")
    @PreAuthorize("hasAuthority('sys:dictionarydata:save')")
    public ResponseResult save(@Valid @RequestBody SysDictionaryData request){
        sysDictionaryDataService.add(request);

        return ResponseResult.success();
    }

    @PutMapping("{id}")
    @ApiOperation("修改")
    @PreAuthorize("hasAuthority('sys:dictionarydata:update')")
    public ResponseResult update(@PathVariable("id") Long id, @Valid @RequestBody SysDictionaryData request){
        sysDictionaryDataService.update(id, request);
        return ResponseResult.success();
    }

    @DeleteMapping
    @ApiOperation("删除")
    @PreAuthorize("hasAuthority('sys:dictionarydata:delete')")
    public ResponseResult delete(@RequestBody Long[] ids){
        sysDictionaryDataService.delete(ids);
        return ResponseResult.success();
    }

}