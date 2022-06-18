package com.linyonghao.oss.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(scanBasePackages = {"com.linyonghao.oss"})
@EnableCaching
public class LinOssManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LinOssManagerApplication.class, args);
    }

}
