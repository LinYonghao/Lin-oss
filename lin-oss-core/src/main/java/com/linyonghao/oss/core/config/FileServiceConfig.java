package com.linyonghao.oss.core.config;

import com.linyonghao.oss.core.constant.FileServiceName;
import com.linyonghao.oss.core.service.file.FastDFSStrategy;
import com.linyonghao.oss.core.service.file.FileService;
import com.linyonghao.oss.core.service.file.FileServiceImpl;
import com.linyonghao.oss.core.service.file.FileStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileServiceConfig {

    @Value("${file.service.name}")
    private String fileServiceName;


    @Bean
    public FileStrategy fileStrategy() {
        if (fileServiceName.equals(FileServiceName.FASTDFS)) {
            return new FastDFSStrategy();
        }
        return new FastDFSStrategy();

    }

    @Bean
    public FileService fileService(){
        return new FileServiceImpl(fileStrategy());
    }

}
