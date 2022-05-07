package com.linyonghao.oss.core;

import com.linyonghao.oss.common.dao.mapper.sequential.UploadLogMapper;
import com.linyonghao.oss.common.model.UploadLogModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest
@ComponentScan(basePackages = "com.linyonghao.oss")
public class TDengineTest {
    @Autowired
    UploadLogMapper uploadLogMapper;
    @Test
    public void testSelect(){
//        UploadLogModel uploadLogModel = uploadLogMapper.select();
//        System.out.println("uploadLogModels = " + uploadLogModel);
    }
}
