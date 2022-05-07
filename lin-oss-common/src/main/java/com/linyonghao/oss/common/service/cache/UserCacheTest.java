package com.linyonghao.oss.common.service.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserCacheTest {

    @Autowired
    RedisTemplate<Object,Object> redisTemplate;


    public void test(){
        redisTemplate.opsForValue().get("123");
    }

}
