package com.linyonghao.linosscore.config.cache;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.linyonghao.linosscore.config.MybatisRedisCache;
import com.linyonghao.linosscore.model.UserModel;
import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MybatisRedisCacheForUserMapper extends MybatisRedisCache {


    Logger logger = LoggerFactory.getLogger(MybatisRedisCacheForUserMapper.class);

    public MybatisRedisCacheForUserMapper(String id) {
        super(id);
    }

    @Override
    public Object getObject(Object key) {
        JSONArray val = (JSONArray) redisTemplate.opsForValue().get(NAMESPACE + key.toString());
        if(val == null){
            return null;
        }
        return val.toJavaList(UserModel.class);
    }


}
