package com.linyonghao.oss.common.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SystemConfig {

    @Value("${system.domain}")
    public String domain;

    @Value("600000")
    public int temporaryUpDownExpired;

    @Value("${system.core.base_url}")
    public String baseUrl;


}
