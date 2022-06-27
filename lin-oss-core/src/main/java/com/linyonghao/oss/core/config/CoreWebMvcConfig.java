package com.linyonghao.oss.core.config;

import com.linyonghao.oss.core.interceptor.UploadInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedMethods("*")
                .maxAge(1800)
                .allowedOriginPatterns("*");
    }



}
