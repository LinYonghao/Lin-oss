package com.linyonghao.oss.common.service;

import com.alibaba.fastjson2.JSON;
import com.linyonghao.oss.common.service.cache.CommonCache;
import com.linyonghao.oss.common.dto.TemporaryUpDownCacheInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TemporaryUpDownRedisService extends CommonCache<TemporaryUpDownCacheInfo> {

    @Autowired
    StringRedisTemplate redisTemplate;

    public TemporaryUpDownCacheInfo get(String token) {
        String obj = redisTemplate.opsForValue().get(generateKey("upload_token", token));
        if(obj == null){
            return null;
        }
        return JSON.parseObject(obj, TemporaryUpDownCacheInfo.class);

    }

    public void set(TemporaryUpDownCacheInfo uploadCacheInfo){
        redisTemplate.opsForValue().set(generateKey("temporary_token",uploadCacheInfo.getToken()),
                JSON.toJSONString(uploadCacheInfo),uploadCacheInfo.getExpireTimeMS(), TimeUnit.MILLISECONDS);
    }

}
