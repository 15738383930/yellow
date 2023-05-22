package com.yellow.api.controller;

import com.yellow.api.model.request.ChangePwdRequest;
import com.yellow.api.model.request.LoginRequest;
import com.yellow.api.model.response.LoginResult;
import com.yellow.api.security.JwtUserDetails;
import com.yellow.api.service.AuthService;
import com.yellow.api.service.CaptchaService;
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

@Slf4j
@RestController
@Api(tags = "鉴权中心")
@RequestMapping("/auth")
public class AuthController {

    @Resource
    private AuthService authService;

    @Resource
    private CaptchaService captchaService;

    /**
     * 验证码
     */
    @GetMapping("captcha/{uuid}.jpg")
    public void captcha(HttpServletResponse response, @PathVariable("uuid") String uuid)throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //获取图片验证码
        BufferedImage image = captchaService.getCaptcha(uuid);

        try(ServletOutputStream out = response.getOutputStream()){
            ImageIO.write(image, "jpg", out);
            out.flush();
        }
    }

    @PostMapping("/login")
    public LoginResult login(HttpServletResponse response, @Valid @RequestBody LoginRequest loginRequest) {
        captchaService.validate(loginRequest.getUuid(), loginRequest.getCaptcha());

        return authService.login(response, loginRequest.getUsername(), loginRequest.getPassword());
    }

    @PostMapping("/logout")
    public ResponseResult logout(HttpServletRequest request, HttpServletResponse response) {
        authService.logout(request, response);
        return ResponseResult.success();
    }

    @GetMapping("/userinfo")
    @ApiOperation("当前用户信息")
    public ObjectResponseResult<JwtUserDetails> info(){
        return ObjectResponseResult.success(SecurityUtils.getCurrentUser());
    }

    @PostMapping("/change-pwd")
    @ApiOperation("修改密码")
    public ResponseResult changePassword(@Valid @RequestBody ChangePwdRequest changePwdRequest) {
        return authService.changePassword(changePwdRequest.getPassword(), changePwdRequest.getNewPassword());
    }
}
