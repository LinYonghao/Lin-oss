package com.linyonghao.oss.core;

import com.linyonghao.oss.core.config.AppConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AppConfigTest {

    @Autowired
    private AppConfig appConfig;

    @Test
    public void getAppPath(){
        System.out.println(appConfig.getWorkPath());
    }
}
