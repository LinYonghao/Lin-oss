package com.linyonghao.oss.manager;

import com.linyonghao.oss.manager.service.StatisticService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest
@ComponentScan(basePackages = "com.linyonghao.oss")
public class TestInflux {

    @Autowired
    StatisticService statisticService;

    @Test
    public void test(){
        long apiNumById = statisticService.getAPITodayNumById("1");
        System.out.println(apiNumById);
    }
}
