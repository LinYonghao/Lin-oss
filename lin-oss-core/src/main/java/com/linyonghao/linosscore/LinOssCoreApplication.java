package com.linyonghao.linosscore;

import org.apache.ibatis.annotations.CacheNamespace;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan("com.linyonghao.linosscore.mapper")
@EnableCaching
public class LinOssCoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(LinOssCoreApplication.class, args);
    }
}
