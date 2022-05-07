package com.linyonghao.oss.core;


import com.linyonghao.oss.common.model.UserModel;
import com.linyonghao.oss.common.service.cache.UserCacheService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@SpringBootTest
@ComponentScan(basePackages = {"com.linyonghao.oss"})
public class RedisTest {

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Test
    public void testRedis(){
        redisTemplate.opsForValue().set("abc","123");
    }

    @Test
    public void testMyBatisEnableCache(){
        String key = "abc:cba: abc \n jj";
        System.out.println("redisTemplate.opsForValue().get(key) = " + redisTemplate.opsForValue().get(key));
    }

    @Test
    public void testWriteFastJson(){
        UserModel userModel = new UserModel();
        userModel.setAccessKey("123");
        userModel.setSecretKey("123");

        redisTemplate.opsForValue().set("userModel",userModel);
    }

    @Test
    public void testReadFastJson(){
        UserModel userModel = (UserModel) redisTemplate.opsForValue().get("userModel");
        System.out.println(userModel);
    }

    @Test
    public void testCache(){

    }
}
