package com.linyonghao.linosscore;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.linyonghao.linosscore.mapper")
public class LinOssCoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(LinOssCoreApplication.class, args);
    }
}
