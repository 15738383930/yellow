package com.yellow.api.controller;

import com.yellow.api.model.request.ChangePwdRequest;
import com.yellow.api.model.request.LoginRequest;
import com.yellow.api.model.response.LoginResult;
import com.yellow.api.security.JwtUserDetails;
import com.yellow.api.service.AuthService;
import com.yellow.api.service.SysCaptchaService;
import com.yellow.api.util.SecurityUtils;
import com.yellow.common.entity.response.ObjectResponseResult;
import com.yellow.common.entity.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * <p>
 * 用户表 控制器
 * </p>
 *
 * @author zhangz145
 * @since 2020-05-18
 */
@Slf4j
@RestController
@Api(tags = "鉴权中心")
@RequestMapping("/auth")
public class AuthController {

    @Resource
    private AuthService authService;

    @Resource
    private SysCaptchaService sysCaptchaService;

    /**
     * 验证码
     */
    @GetMapping("captcha/{uuid}.jpg")
    public void captcha(HttpServletResponse response, @PathVariable("uuid") String uuid)throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //获取图片验证码
        BufferedImage image = sysCaptchaService.getCaptcha(uuid);

        try(ServletOutputStream out = response.getOutputStream()){
            ImageIO.write(image, "jpg", out);
            out.flush();
        }
    }

    @PostMapping("/login")
    public LoginResult login(HttpServletResponse response, @Valid @RequestBody LoginRequest loginRequest) {
        sysCaptchaService.validate(loginRequest.getUuid(), loginRequest.getCaptcha());

        return authService.login(response, loginRequest.getUsername(), loginRequest.getPassword());
    }

    @PostMapping("/logout")
    public ResponseResult logout(HttpServletRequest request, HttpServletResponse response) {
        authService.logout(request, response);
        return ResponseResult.success();
    }

    @GetMapping("/userinfo")
    public ObjectResponseResult<JwtUserDetails> userinfo() {
        return ObjectResponseResult.success(SecurityUtils.getCurrentUser());
    }

    @PostMapping("/change-pwd")
    @ApiOperation("修改密码")
    public ResponseResult changePassword(HttpServletRequest request, HttpServletResponse response, @Valid @RequestBody ChangePwdRequest changePwdRequest) {
        return authService.changePassword(request, response, changePwdRequest.getOldPwd(), changePwdRequest.getNewPwd());
    }
}
