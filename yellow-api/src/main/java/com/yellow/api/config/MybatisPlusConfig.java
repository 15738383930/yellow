package com.yellow.api.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.yellow.api.util.SecurityUtils;
import com.yellow.common.util.StringUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * @author jianghy
 * @Description: mybatisPlus配置
 * @date 2020/4/26 10:47
 */
@Configuration
@MapperScan( value = {"com.yellow.api.mapper"})
public class MybatisPlusConfig implements MetaObjectHandler {

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor page = new PaginationInterceptor();
        page.setDialectType("mysql");
        return page;
    }

    /**
     * 新增时填充
     * @author Hao.
     * @date 2022/4/6 14:42
     * @param metaObject
     * @return
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, getFieldNameOfCreateId(), String.class, getCreateId(metaObject));
        this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
        this.strictInsertFill(metaObject, getFieldNameOfUpdateId(), String.class, getUpdateId(metaObject));
        this.strictInsertFill(metaObject, "updateTime", Date.class, new Date());
    }

    /**
     * 修改时填充
     * @author Hao.
     * @date 2022/4/6 14:42
     * @param metaObject
     * @return
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, getFieldNameOfUpdateId(), String.class, getUpdateId(metaObject));
        this.strictInsertFill(metaObject, "updateTime", Date.class, new Date());
    }

    private String getFieldNameOfCreateId() {
        return "createBy";
    }

    private String getFieldNameOfUpdateId() {
        return "updateBy";
    }

    private String getCreateId(MetaObject metaObject) {
        String createId = StringUtils.valueOf(this.getFieldValByName(this.getFieldNameOfCreateId(), metaObject));
        if (StringUtils.isEmpty(createId)) {
            return SecurityUtils.getCurrentUsername();
        }
        return createId;
    }

    private String getUpdateId(MetaObject metaObject) {
        String updateId = StringUtils.valueOf(this.getFieldValByName(this.getFieldNameOfUpdateId(), metaObject));
        if (StringUtils.isEmpty(updateId)) {
            return SecurityUtils.getCurrentUsername();
        }
        return updateId;
    }
}

