package com.linyonghao.oss.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${app.path}")
    private String workPath;


    public String getWorkPath() {
        return workPath;
    }
}
