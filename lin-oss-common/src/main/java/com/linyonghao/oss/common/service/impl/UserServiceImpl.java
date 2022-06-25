package com.linyonghao.oss.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linyonghao.oss.common.dao.mapper.relationship.UserMapper;
import com.linyonghao.oss.common.model.UserModel;
import com.linyonghao.oss.common.service.UserService;
import com.linyonghao.oss.common.service.cache.UserCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserModel> implements UserService {

    @Autowired
    UserCacheService userCacheService;

    @Override
    public UserModel getUser(String userId) {
        UserModel user;
        user = userCacheService.getUser(Long.parseLong(userId));
        if(user == null){
            user = this.getBaseMapper().selectById(userId);
            userCacheService.setUserById(user);
        }
        return user;
    }

    @Override
    public UserModel getByAccessKey(String accessKey) {
        UserModel user;
        user = userCacheService.getUser(accessKey);
        if(user == null){
            QueryWrapper<UserModel> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("access_key",accessKey);
            user = this.getBaseMapper().selectOne(queryWrapper);
            userCacheService.setUserAccessKey(user);
        }
        return user;
    }


}
