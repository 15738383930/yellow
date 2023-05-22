package com.yellow.api.controller;

import com.stars.datachange.utils.DataChangeUtils;
import com.yellow.api.model.Test;
import com.yellow.common.entity.response.ObjectResponseResult;
import com.yellow.common.entity.response.ResponseResult;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 控制器
 * </p>
 *
 * @author zhangz145
 * @since 2020-05-18
 */
@Api(tags = "测试测试")
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/publickey")
    @PreAuthorize("hasRole('ROLE_ADMIN') and hasAuthority('sys:log:list')")
    public ResponseResult publickey() {
        //公钥
        String publickey = "-----BEGIN PUBLIC KEY-----1IIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAm5pf3IgFo3DncyM5ub1r41NNEpj1VZwORASgo7QgN+7qYIhfvE/lFcXEnxu8ZT8usIugsvDbtocxPsiAIwIxHhs+VF/9bSbrBSGvYVlpdrwI6tIwG/Vjd1IDQGcxqaGNnGoQe1N1W+3mN0bV+tAsberop9bZYREFXoBc6skMU7D8AHqUIqRNpOjGzrMtcAgfpzwApeQeOF426AyTpsk1EaSr77lwFCP524oYK5yxKOPrnKrXTAcXMv+tH1cuQDGqsRFtiwhAUHsC9LEEuEKGP9Lz7JiILQc5/nWw8DEUS/3WfzIlAjwV17TaU7EGUJD2VEwiFVvtEb2BF8i9tyqAEQIDAQAB-----END PUBLIC KEY-----";
        //jwt令牌
        String jwtString = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpbnZhbGlkVGltZSI6MTY4MjMzMTcxNzE3OCwidXNlcm5hbWUiOiJ0ZXN0In0.ZE_SWL1yRkVYmhgyMJaB3Tm3pBSS3E2KcSEmZpXMkYrxC_wnh1vHtKOJHcqRG8fikqTlrmJMfiVQCvmXnCTkBEwPkW4GDsKLNaBJPd1UKpswMgcXcO7mn9_OmSA22haSs_CAh7G7PKjDXcdm2KDzzWG_IOMpQxgnwSxMjTbl1_Me8MSvQxI4a9jzoQVjHy62ZDdJ0qXVJD1wH1wanSeOlxJYxWSfpKrTz0_QgXFX4mshdxjwB6xf6V3y20QUbGvpWtdjm5y4RpKXHRs4gHOYlqjps1AYMPVUpRxz-UgBR_lcPfro0CYHvSN1YRZnbr7shW3PvTtoQAzOh8HmVkeLGQ";
        //校验jwt令牌
        Jwt jwt = JwtHelper.decodeAndVerify(jwtString, new RsaVerifier(publickey));
        //拿到jwt令牌中自定义的内容
        String claims = jwt.getClaims();
        System.out.println(claims);
        return ResponseResult.success();
    }

    @GetMapping("/test")
    public ObjectResponseResult test() {
        Test t = new Test();
        t.setName("静静");
        t.setType(2);
        t.setFavoriteFood("1,3,5");
        t.setTouristPlace(6);
        DataChangeUtils.dataChangeToBean(t);
        return ObjectResponseResult.success(t);
    }
}
