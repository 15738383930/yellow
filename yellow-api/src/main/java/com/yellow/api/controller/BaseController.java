package com.yellow.api.controller;

import com.yellow.api.util.SecurityUtils;
import com.yellow.common.constant.Constants;
import com.yellow.common.entity.request.RequestData;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 用于获取request、response、session等信息
 * @author Hao.
 * @version 1.0
 * @date 2022/4/18 10:57
 */
public class BaseController {
    protected HttpServletRequest request;

    protected HttpServletResponse response;

    protected HttpSession session;

    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {

        this.request = request;

        this.response = response;

        this.session = request.getSession();

    }

    public void setDataPermissions(RequestData data){
        //如果不是超级管理员，则只查询自己创建的角色列表
        final String currentUsername = SecurityUtils.getCurrentUsername();
        if(!Constants.GOD.equals(currentUsername)){
            data.setCreateBy(currentUsername);
        }
    }
}
