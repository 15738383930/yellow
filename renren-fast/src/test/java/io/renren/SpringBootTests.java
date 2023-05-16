package io.renren;

import com.alibaba.fastjson.JSON;
import io.renren.modules.sys.entity.SysMenuEntity;
import io.renren.modules.sys.service.SysMenuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SpringBootTests {

    @Autowired
    private SysMenuService sysMenuService;

    @Test
    public void test(){

        List<SysMenuEntity> menuList = sysMenuService.getUserMenuList(1L);
        System.out.println(JSON.toJSONString(menuList));
    }
}
