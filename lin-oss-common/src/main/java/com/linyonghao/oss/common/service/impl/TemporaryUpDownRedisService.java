package com.linyonghao.oss.common.service.impl;

import com.alibaba.fastjson2.JSON;
import com.linyonghao.oss.common.config.SystemConfig;
import com.linyonghao.oss.common.service.cache.CommonCache;
import com.linyonghao.oss.common.dto.TemporaryUpDownCacheInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TemporaryUpDownRedisService extends CommonCache<TemporaryUpDownCacheInfo> {

    Logger logger = LoggerFactory.getLogger(TemporaryUpDownRedisService.class);
    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    SystemConfig systemConfig;

    public TemporaryUpDownCacheInfo get(String token) {
        String key = generateKey("temporary_token", token);
        String obj = redisTemplate.opsForValue().get(key);
        if(obj == null){
            return null;
        }
        return JSON.parseObject(obj, TemporaryUpDownCacheInfo.class);

    }

    public void set(TemporaryUpDownCacheInfo uploadCacheInfo){
        String s = JSON.toJSONString(uploadCacheInfo);
        redisTemplate.opsForValue().set(generateKey("temporary_token",uploadCacheInfo.getToken()),
                s,uploadCacheInfo.getExpireTimeMS(), TimeUnit.MILLISECONDS);
    }

    public String generateOneKey(String bucketId,String userId){
        String token = UUID.randomUUID().toString().replace("-", "");
        set(new TemporaryUpDownCacheInfo(bucketId,token,userId
                , systemConfig.temporaryUpDownExpired));
        return token;
    }

}
