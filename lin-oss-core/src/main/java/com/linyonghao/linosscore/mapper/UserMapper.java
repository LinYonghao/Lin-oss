package com.linyonghao.linosscore.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.linyonghao.linosscore.config.MybatisRedisCache;
import com.linyonghao.linosscore.model.UserModel;
import org.apache.ibatis.annotations.CacheNamespace;
import org.springframework.stereotype.Repository;


@Repository
@CacheNamespace(implementation = MybatisRedisCache.class,eviction =MybatisRedisCache.class )
public interface UserMapper extends BaseMapper<UserModel> {

}
