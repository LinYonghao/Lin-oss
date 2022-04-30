package com.linyonghao.linosscore.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.linyonghao.linosscore.config.MybatisRedisCache;
import com.linyonghao.linosscore.config.cache.MybatisRedisCacheForUserMapper;
import com.linyonghao.linosscore.model.UserModel;
import org.apache.ibatis.annotations.CacheNamespace;
import org.springframework.stereotype.Repository;


@Repository
// 自定义了一个Cache实现类，用于设定指导类型。
@CacheNamespace(implementation = MybatisRedisCacheForUserMapper.class)
public interface UserMapper extends BaseMapper<UserModel> {

}
