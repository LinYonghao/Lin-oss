package com.linyonghao.oss.common.config.cache;

import com.alibaba.fastjson2.JSONArray;
import com.linyonghao.oss.common.config.MybatisRedisCache;
import com.linyonghao.oss.common.model.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
