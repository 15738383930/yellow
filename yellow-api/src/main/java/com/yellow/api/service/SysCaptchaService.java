/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.yellow.api.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.code.kaptcha.Producer;
import com.yellow.api.exception.LoginExceptionCast;
import com.yellow.api.mapper.SysCaptchaMapper;
import com.yellow.api.model.SysCaptcha;
import com.yellow.common.entity.response.CommonCode;
import com.yellow.common.exception.ExceptionCast;
import com.yellow.common.util.DateUtils;
import com.yellow.common.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.image.BufferedImage;
import java.util.Date;

/**
 * 验证码
 *
 * @author Mark sunlightcs@gmail.com
 */
@Service
public class SysCaptchaService extends ServiceImpl<SysCaptchaMapper, SysCaptcha> {

    @Resource
    private Producer producer;

    public BufferedImage getCaptcha(String uuid) {
        if(StringUtils.isEmpty(uuid)){
            ExceptionCast.cast(CommonCode.VERIFICATION_ID_NOT_NULL);
        }
        //生成文字验证码
        String code = producer.createText();

        SysCaptcha captchaEntity = new SysCaptcha();
        captchaEntity.setUuid(uuid);
        captchaEntity.setCode(code);
        //5分钟后过期
        captchaEntity.setExpireTime(DateUtils.addDateByMinute(new Date(), 5));
        this.save(captchaEntity);

        return producer.createImage(code);
    }

    public void validate(String uuid, String code) {
        SysCaptcha captchaEntity = this.getOne(new QueryWrapper<SysCaptcha>().eq("uuid", uuid));
        if(captchaEntity == null){
            LoginExceptionCast.cast(CommonCode.VERIFICATION_CODE_ERROR);
        }

        //删除验证码
        this.removeById(uuid);

        if(!captchaEntity.getCode().equalsIgnoreCase(code)){
            LoginExceptionCast.cast(CommonCode.VERIFICATION_CODE_ERROR);
        }

        if(captchaEntity.getExpireTime().getTime() < System.currentTimeMillis()){
            LoginExceptionCast.cast(CommonCode.VERIFICATION_CODE_EXPIRED);
        }
    }
}
