package com.linyonghao.oss.common.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linyonghao.oss.common.dao.mapper.relationship.CoreRoleUserMapper;
import com.linyonghao.oss.common.entity.CoreRole;
import com.linyonghao.oss.common.entity.CoreRoleUser;
import com.linyonghao.oss.common.service.ICoreRoleUserService;
import com.linyonghao.oss.common.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LinYonghao
 * @since 2022-06-27
 */
@Service
public class CoreRoleUserServiceImpl extends ServiceImpl<CoreRoleUserMapper, CoreRoleUser> implements ICoreRoleUserService {

    @Autowired
    RoleUserRedisService roleUserRedisService;

    @Override
    public List<CoreRole> getRoleByUserId(String userId) {
        List<CoreRole> coreRoles = roleUserRedisService.get(userId);
        if(coreRoles == null){
            coreRoles = getBaseMapper().selectRolesByUserId(userId);
            roleUserRedisService.set(userId,coreRoles);
        }
        return coreRoles;
    }
}

@Service
class RoleUserRedisService{
    @Value("${redis.key.role.user}")
    private String KEY;

    @Value("${redis.database}")
    private String DATABASE;

    @Autowired
    private StringRedisTemplate redisTemplate;

    public List<CoreRole> get(String userId){
        String s = redisTemplate.opsForValue().get(StringUtil.generateRedisKey(DATABASE, KEY, userId));
        if(s == null){
            return null;
        }
        TypeReference<List<CoreRole>> typeReference = new TypeReference<List<CoreRole>>() {};
        return JSON.parseObject(s,typeReference.getType());
    }


    public void set(String userId,List<CoreRole> coreRoleList){
        redisTemplate.opsForValue().set(StringUtil.generateRedisKey(DATABASE, KEY, userId),
                JSON.toJSONString(coreRoleList));
    }



}
