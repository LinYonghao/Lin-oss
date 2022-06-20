package com.linyonghao.oss.manager;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = {"com.linyonghao.oss"})
@EnableCaching
public class LinOssManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(LinOssManagerApplication.class, args);
    }

}
