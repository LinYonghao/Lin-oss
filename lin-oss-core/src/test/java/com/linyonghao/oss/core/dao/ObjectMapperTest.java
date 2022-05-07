package com.linyonghao.oss.core.dao;

import com.linyonghao.oss.common.dao.mapper.relationship.ObjectMapper;
import com.linyonghao.oss.common.model.ObjectBucketDO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest
@ComponentScan("com.linyonghao.oss")
public class ObjectMapperTest {
    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void testSelect(){
        ObjectBucketDO bucket1 = objectMapper.selectObjectByPath(12312313, "abcd.png");
        System.out.println("bucket1 = " + bucket1.getObjectModel());
        System.out.println("bucket1 = " + bucket1.getBucketModel());

    }

}
