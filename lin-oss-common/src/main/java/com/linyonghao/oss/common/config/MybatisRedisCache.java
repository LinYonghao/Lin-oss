package com.linyonghao.oss.common.config;

import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MybatisRedisCache implements Cache {
    protected static RedisTemplate<Object, Object> redisTemplate;

    Logger logger = LoggerFactory.getLogger(MybatisRedisCache.class);
    private final String id;
    private static int cacheSec;
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    protected static final String NAMESPACE = "mybatis-cache:";
    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }

    public MybatisRedisCache(String id) {
        if (id == null) {
            logger.error("cache id 不能为空");
            throw new IllegalArgumentException("cache id 不能为空");
        }
        this.id = id;

    }

    public static void setCacheSec(int cacheSec) {
        MybatisRedisCache.cacheSec = cacheSec;
    }

    public static void setRedisTemplate(RedisTemplate<Object, Object> redisTemplate) {
        MybatisRedisCache.redisTemplate = redisTemplate;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
        public void putObject(Object key, Object value) {
        logger.debug("set cache key->" + key + " values-> " + value);
        if(value != null){
            if (cacheSec > 0){
                redisTemplate.opsForValue().set(NAMESPACE + key.toString() ,value,cacheSec, TimeUnit.SECONDS);
            }else{
                redisTemplate.opsForValue().set(NAMESPACE + key.toString() ,value);
            }
        }
    }

    @Override
    public Object getObject(Object key) {
        return redisTemplate.opsForValue().get(NAMESPACE + key.toString());
    }


    @Override
    public Object removeObject(Object key) {
        return redisTemplate.delete(NAMESPACE + key.toString());
    }

    @Override
    public void clear() {
        Set<Object> keys = redisTemplate.keys(NAMESPACE + "*");
        if(!CollectionUtils.isEmpty(keys)){
            redisTemplate.delete(keys);
        }
    }

    @Override
    public int getSize() {
        Set<Object> keys = redisTemplate.keys(NAMESPACE + "*");
        if (CollectionUtils.isEmpty(keys)){
            return 0;
        }
        return keys.size();
    }
}
