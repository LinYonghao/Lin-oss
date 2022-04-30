package com.linyonghao.linosscore.config;

import com.linyonghao.linosscore.constant.FileServiceName;
import com.linyonghao.linosscore.service.file.FastDFSStrategy;
import com.linyonghao.linosscore.service.file.FileService;
import com.linyonghao.linosscore.service.file.FileServiceImpl;
import com.linyonghao.linosscore.service.file.FileStrategy;
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
