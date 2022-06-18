package com.linyonghao.oss.manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class SMSRedisService {

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @Value("${zhenzi.invalidTimer}")
    private int invalidTimer;

    public void set(String mobile,String code){
        redisTemplate.opsForValue().set(getKey(mobile),code,invalidTimer, TimeUnit.MINUTES);
    }

    public String get(String mobile){
        return redisTemplate.opsForValue().get(getKey(mobile));
    }

    public boolean isValid(String mobile){
        return redisTemplate.opsForValue().get(getKey(mobile)) == null;
    }

    public boolean del(String mobile){
        return Boolean.TRUE.equals(redisTemplate.delete(getKey(mobile)));
    }
    private String getKey(String mobile){
        return String.format("sms:%s", mobile);
    }

}
