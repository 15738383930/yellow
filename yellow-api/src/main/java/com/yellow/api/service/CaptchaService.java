/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.yellow.api.service;


import com.google.code.kaptcha.Producer;
import com.yellow.common.constant.Constants;
import com.yellow.common.entity.response.CommonCode;
import com.yellow.common.exception.ExceptionCast;
import com.yellow.common.util.RedisUtils;
import com.yellow.common.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.image.BufferedImage;

/**
 * 验证码
 *
 * @author Mark sunlightcs@gmail.com
 */
@Service
public class CaptchaService {

    @Resource
    private Producer producer;

    @Resource
    private RedisUtils redisUtils;

    public BufferedImage getCaptcha(String uuid) {
        if(StringUtils.isEmpty(uuid)){
            ExceptionCast.cast(CommonCode.VERIFICATION_ID_NOT_NULL);
        }
        //生成文字验证码
        String code = producer.createText();

        // 5分钟后过期
        redisUtils.set(Constants.CAPTCHA_CODE_KEY + uuid, code, 300L);

        return producer.createImage(code);
    }

    public void validate(String uuid, String captcha) {
        final String key = Constants.CAPTCHA_CODE_KEY + uuid;
        if(!redisUtils.exists(key) || !redisUtils.get(key).equalsIgnoreCase(captcha)){
            redisUtils.remove(key);
            ExceptionCast.cast(CommonCode.VERIFICATION_CODE_ERROR);
        }
    }
}
