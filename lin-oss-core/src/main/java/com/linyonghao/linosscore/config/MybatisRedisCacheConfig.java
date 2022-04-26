package com.linyonghao.linosscore.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class MybatisRedisCacheConfig {

    @Value("${mybatis.cache-sec}")
    private int cacheSec;


    @Autowired
    public void config(RedisTemplate<String,Object> template) {
        MybatisRedisCache.setRedisTemplate(template);
        MybatisRedisCache.setCacheSec(cacheSec);
    }
}

