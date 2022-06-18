package com.linyonghao.oss.manager.service;


import com.alibaba.fastjson2.JSON;
import com.linyonghao.oss.common.dao.mapper.relationship.CorePermissionMapper;
import com.linyonghao.oss.common.entity.CorePermission;
import com.linyonghao.oss.common.service.ICorePermissionService;
import com.linyonghao.oss.common.service.impl.CorePermissionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class PermissionService {
    @Autowired
    PermissionRedisService permissionRedisService;

    @Autowired
    private CorePermissionServiceImpl corePermissionService;

    public List<CorePermission> get(String userId) {
        List<CorePermission> corePermissions = permissionRedisService.get(userId);
        if (corePermissions != null) {
            return corePermissions;
        }
        corePermissions = corePermissionService.getBaseMapper().selectByUserId(userId);
        if(corePermissions == null){
            return null;
        }

        permissionRedisService.set(userId,corePermissions);
        return corePermissions;
    }


}

@Service
class PermissionRedisService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public List<CorePermission> get(String userId) {
        String json = redisTemplate.opsForValue().get(String.format("user:permission:%s", userId));
        if (!StringUtils.hasLength(json)) {
            return null;
        }
        return JSON.parseArray(json, CorePermission.class);
    }

    public void set(String userId, List<CorePermission> permissionList) {
        redisTemplate.opsForValue().set(String.format("user:permission:%s", userId), JSON.toJSONString(permissionList));
    }


}
