package com.xtt.platform.util;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 * @Description 单元测试基础类
 * @author Tik
 * @date 2014年8月7日下午7:20:38
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/config/springContext.xml" })
public class BaseJunitTest {

    static {
        System.setProperty("BASE_PATH", System.getProperty("os.name").toLowerCase().startsWith("win") ? "c:/xtt" : "/home/publish/xtt");
    }
}
