package com.linyonghao.oss.core;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LoggerTest {


    Logger logger = LoggerFactory.getLogger(getClass());
    @Test
    public void testLog(){
        logger.trace("这是 trace");
        logger.debug("调试 debug");
        // spring boot 默认使用的是 info 级别的
        // 没有指定级别的就使用 Spring Boot 默认规定的级别，root 级别
        logger.info("这是 info");
        logger.warn("这是 warn");
        logger.error("这是 error");
    }

}
