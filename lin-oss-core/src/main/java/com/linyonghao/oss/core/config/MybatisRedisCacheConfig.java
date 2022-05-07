package com.linyonghao.oss.core.config;

import com.linyonghao.oss.common.config.MybatisRedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class MybatisRedisCacheConfig {

    @Value("${mybatis.cache-sec}")
    private int cacheSec;


    @Autowired
    public void config(RedisTemplate<Object,Object> template) {
        MybatisRedisCache.setRedisTemplate(template);
        MybatisRedisCache.setCacheSec(cacheSec);
    }
}

