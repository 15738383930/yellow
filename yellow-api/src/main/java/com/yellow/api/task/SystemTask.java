package com.yellow.api.task;

import com.yellow.api.mapper.SysDetailLogMapper;
import com.yellow.api.mapper.SysLoginLogMapper;
import com.yellow.api.mapper.SysOperateLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 系统定时任务
 * @Author zhou
 * @Date 2021/4/23 15:19
 */
@Slf4j
@Component
public class SystemTask {

    @Resource
    private SysDetailLogMapper sysDetailLogMapper;

    @Resource
    private SysLoginLogMapper sysLoginLogMapper;

    @Resource
    private SysOperateLogMapper sysOperateLogMapper;

    /**
     * 删除半年前的操作日志（日志保留时间最多七个月）
     * (每个月1号执行一次)
     * @return void
     * @author zhouhao
     * @date  2020/10/10 13:07
     *
     * @Scheduled(cron = "0 30 15 * * ?")
     * @Scheduled(cron = "0 0 0 4 * ?") 每月4号执行一次
     */
    @Scheduled(cron = "0 0 0 4 * ?")
    public void removeOperateLog(){
        log.info("--------|---------------------------------|--------");
        log.info("--------|                                 |--------");
        log.info("--------|--------定时删除日志启动-----------|---------");
        log.info("--------|                                 |--------");
        log.info("--------|---------------------------------|--------");
        log.info("--------|     开始删除【操作异常】日志       |---------");
        log.info("--------|---------------------------------|--------");
        log.info("--------|-------删除成功：共删除{}条--------|---------", sysDetailLogMapper.deleteOperateLog());
        log.info("--------|---------------------------------|--------");
        log.info("--------|                                 |--------");
        log.info("--------|---------------------------------|--------");
        log.info("--------|       开始删除【操作】日志         |---------");
        log.info("--------|---------------------------------|--------");
        log.info("--------|-------删除成功：共删除{}条--------|---------", sysOperateLogMapper.deleteOperateLog());
        log.info("--------|---------------------------------|--------");
    }

    /**
     * 删除一年前的登录日志（日志保留时间最多一年零二个月）
     * (每二个月执行一次)
     * @return void
     * @author zhouhao
     * @date  2020/10/10 13:07
     *
     * @Scheduled(cron = "0 30 15 * * ?")
     * @Scheduled(cron = "0 0/5 * * * ?")
     * @Scheduled(cron = "0 0 0 2 *\/2 ?") 每两个月的2号执行一次
     */
    @Scheduled(cron = "0 0 0 2 */2 ?")
    public void removeLoginLog(){
        log.info("--------|---------------------------------|--------");
        log.info("--------|                                 |--------");
        log.info("--------|--------定时删除日志启动-----------|---------");
        log.info("--------|                                 |--------");
        log.info("--------|---------------------------------|--------");
        log.info("--------|     开始删除【登录异常】日志       |---------");
        log.info("--------|---------------------------------|--------");
        log.info("--------|-------删除成功：共删除{}条--------|---------", sysDetailLogMapper.deleteLoginLog());
        log.info("--------|---------------------------------|--------");
        log.info("--------|                                 |--------");
        log.info("--------|---------------------------------|--------");
        log.info("--------|       开始删除【登录】日志         |---------");
        log.info("--------|---------------------------------|--------");
        log.info("--------|-------删除成功：共删除{}条--------|---------", sysLoginLogMapper.deleteLoginLog());
        log.info("--------|---------------------------------|--------");
    }
}
