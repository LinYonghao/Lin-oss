package com.linyonghao.oss.core;

import com.influxdb.client.service.UsersService;
import org.jetbrains.annotations.NotNull;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

@SpringBootApplication(scanBasePackages = {"com.linyonghao.oss"})
@EnableCaching
public class LinOssCoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(LinOssCoreApplication.class, args);
    }
}

