package com.linyonghao.oss.common;

import com.linyonghao.influxdb2.entity.CountWithTime;
import com.linyonghao.oss.common.dao.mapper.sequential.APILogMapper;
import com.linyonghao.oss.common.service.StatisticService;
import com.linyonghao.oss.common.service.cache.UserCacheService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;

@SpringBootTest
@ComponentScan(basePackages = {"com.linyonghao.oss"})
class LinOssCommonApplicationTests {


    @Test
    void contextLoads() {
    }




}
