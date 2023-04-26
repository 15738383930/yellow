package com.yellow.api;

import com.alibaba.fastjson.JSONObject;
import com.yellow.api.service.IBimDatabaseSyncSchedulerService;
import com.yellow.api.service.ICjxxMfSqsTasksService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtTest {

    @Autowired
    private IBimDatabaseSyncSchedulerService iBimDatabaseSyncSchedulerService;

    @Resource
    private ICjxxMfSqsTasksService iCjxxMfSqsTasksService;

//    @Test
//    public void test1() {
//        iBimDatabaseSyncSchedulerService.syncHtqybsj();
//    }
//
//    @Test
//    public void test2() {
//        iBimDatabaseSyncSchedulerService.syncCsjsj();
//    }
//
//    @Test
//    public void test3() {
//        iBimDatabaseSyncSchedulerService.syncGcsj();
//    }
//
//    @Test
//    public void test4() {
//        iBimDatabaseSyncSchedulerService.syncSqbsj();
//    }
//
//    @Test
//    public void test5() {
//        iBimDatabaseSyncSchedulerService.syncZgdsj();
//    }
//
//    @Test
//    public void test6() {
//        iBimDatabaseSyncSchedulerService.syncZhdsj();
//    }
//
//    @Test
//    public void test7() {
//        iBimDatabaseSyncSchedulerService.syncJczxsj();
//    }
//
//    @Test
//    public void test8() {
//        iBimDatabaseSyncSchedulerService.syncJcsj();
//    }
//
    @Test
    public void test9() {
        iBimDatabaseSyncSchedulerService.syncXshtsj();
    }

//    @Test
//    public void test10() {
//        System.out.println(JSONObject.toJSONString(iCjxxMfSqsTasksService.taskListAllByApplyId("2020071409135618200119202865851978558496")));
//    }

}
