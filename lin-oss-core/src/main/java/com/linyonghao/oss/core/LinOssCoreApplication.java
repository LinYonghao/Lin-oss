package com.linyonghao.oss.core;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(scanBasePackages = {"com.linyonghao.oss"})
@EnableCaching
public class LinOssCoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(LinOssCoreApplication.class, args);
    }
}
