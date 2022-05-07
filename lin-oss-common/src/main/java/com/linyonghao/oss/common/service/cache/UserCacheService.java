package com.linyonghao.oss.common.service.cache;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.linyonghao.oss.common.exception.DeleteRedisException;
import com.linyonghao.oss.common.model.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Component
public class UserCacheService extends CommonCache<UserModel> {
    /**
     * 用户缓存类
     * 可以使用ACCESSKEY和userId两种方法进行访问
     * 类实现的缓存命中率计算不准确 没有把accessKey 和 userId 分开计算
     */
    @Value("${redis.key.user.accessKey}")
    private String ACCESS_KEY;

    @Value("${redis.key.user.userId}")
    private String USER_ID;

    @Value("${redis.database}")
    private String DATABASE;
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;


    Logger logger = LoggerFactory.getLogger(UserCacheService.class);

    public UserCacheService() {
        setGuideType(UserModel.class);
    }

    public UserModel getUser(long userId){
        String json = (String) redisTemplate.opsForValue().get(generateKey(DATABASE, ACCESS_KEY, String.valueOf(userId)));
        incrementAccessNum();
        if(json == null){
            return null;
        }
        incrementHitNumber();
        return JSON.parseObject(json, getGuideType());
    }


    public UserModel getUser(String accessKey){
        incrementAccessNum();
        JSONObject jsonObject = (JSONObject) redisTemplate.opsForValue().get(generateKey(DATABASE, ACCESS_KEY, String.valueOf(accessKey)));
        if(jsonObject == null){
            return null;
        }
        incrementHitNumber();
        logger.info("缓存命中率:" + getHitRate());
        return jsonObject.toJavaObject(getGuideType());
    }

    public void setUserAccessKey(UserModel userModel){
        if(userModel == null || userModel.getAccessKey().isEmpty()){
            throw new IllegalArgumentException("Access key 不能为空");
        }
        String key = generateKey(DATABASE, ACCESS_KEY, String.valueOf(userModel.getAccessKey()));
        redisTemplate.opsForValue().set(key,userModel);
    }

    public Boolean delUserByUserId(long userId){
        return redisTemplate.delete(generateKey(DATABASE, USER_ID, String.valueOf(userId)));
    }

    public Boolean delUserAccessKey(String accessKey){
        return redisTemplate.delete(generateKey(DATABASE, ACCESS_KEY, String.valueOf(accessKey)));
    }

    public Boolean clearAllAccessKey() throws DeleteRedisException{
        String keyPattern = generateKey(DATABASE,ACCESS_KEY,"*");
        Set<Object> keys = redisTemplate.keys(keyPattern);
        if(keys == null || keys.isEmpty()){
            return true;
        }
        for (Object key : keys) {
            boolean isDelete = Boolean.TRUE.equals(redisTemplate.delete(key));
            if(!isDelete){
                throw new DeleteRedisException("key:" + key + "can not delete");
            }
        }
        return true;
    }





}
