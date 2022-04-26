package com.linyonghao.linosscore.config;

import com.linyonghao.linosscore.interceptor.UploadInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@EnableWebMvc
@Configuration
public class CoreWebMvcConfig implements WebMvcConfigurer {
    @Bean
    public UploadInterceptor useUploadInterceptor(){
        return new UploadInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册上传拦截器
        registry.addInterceptor(useUploadInterceptor()).addPathPatterns("/upload/*");

    }


}
