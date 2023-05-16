package com.yellow.api.model.valid;

/**
 * 添加用户校验分组
 * 1. 用户名不能为空且格式由 4到16位（字母，数字，下划线，减号）组成
 * 2. 密码不能为空且格式由 8到16位（大写字母，小写字母，数字，!.@#$%^&*?）且至少包含大写字母、小写字母、数字 组成
 * 3. 用户至少拥有一个或以上角色
 * @Author zhou
 * @Date 2021/4/8 17:23
 */
public interface AddGroup {
}
