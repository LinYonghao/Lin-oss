package com.linyonghao.oss.manager.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.linyonghao.oss.common.utils.StringUtil;
import com.linyonghao.oss.manager.entity.CoreWo;
import com.linyonghao.oss.manager.mapper.CoreWoMapper;
import com.linyonghao.oss.manager.service.ICoreWoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson2.TypeReference;
import org.springframework.util.IdGenerator;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Lin Yonghao
 * @since 2022-06-27
 */
@Service
public class CoreWoServiceImpl extends ServiceImpl<CoreWoMapper, CoreWo> implements ICoreWoService {

    @Autowired
    WoRedisService woRedisService;

    @Override
    public List<CoreWo> getAllWoByUserId(String userId) {
        List<CoreWo> woList = woRedisService.getWoListByUserId(userId);
        if (woList == null){
            QueryWrapper<CoreWo> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id",userId);
            woList = getBaseMapper().selectList(wrapper);
            woRedisService.setWoList(woList,userId);
        }
        return woList;
    }


    @Override
    public CoreWo getOne(String woId) {
        CoreWo wo = woRedisService.getById(woId);
        if(wo == null){
            wo = getBaseMapper().selectById(woId);
            woRedisService.setOne(wo);
        }
        return wo;
    }

    @Override
    public void createWo(CoreWo wo) {
        wo.setId(IdWorker.getId());
        woRedisService.createOne(wo);
    }

    @Override
    public List<CoreWo> getDistribute() {
        return woRedisService.getDistribute();
    }

    @Override
    public synchronized void removeOneDistribute(String woId) {
        List<CoreWo> distribute = woRedisService.getDistribute();
        distribute.removeIf(wo -> String.valueOf(wo.getId()).equals(woId));
        woRedisService.setDistribute(distribute);
    }

    @Override
    public synchronized void insertToPending(String userId, String woId) {
        List<String> mangerIds = woRedisService.getPendingIds(userId);
        if(mangerIds == null){
            mangerIds = new ArrayList<>();
        }
        mangerIds.add(woId);
        woRedisService.setPending(userId,mangerIds);
    }

    @Override
    public List<CoreWo> getPending(String userId) {
        return woRedisService.getPendingWO(userId);
    }

    @Override
    public CoreWo popOneNew() {
        return woRedisService.popOneNew();
    }


}
@Service
class WoRedisService {
    @Autowired
    StringRedisTemplate redisTemplate;

    @Value("${redis.key.wo.list}")
    private String LIST_KEY;

    @Value("${redis.key.wo.id}")
    private String ID_KEY;

    @Value("${redis.key.wo.distribute}")
    private String DISTRIBUTE_KEY;

    @Value("${redis.key.wo.manager}")
    private String MANAGER_KEY;

    @Value("${redis.database}")
    private String DATABASE;

    @Value("${redis.key.wo.new.id}")
    private String NEW_KEY;


    public List<CoreWo> getWoListByUserId(String userId){
        String s = redisTemplate.opsForValue().get(StringUtil.generateRedisKey(DATABASE, LIST_KEY, userId));
        if(s == null){
            return null;
        }
        TypeReference<List<CoreWo>> typeReference = new TypeReference<List<CoreWo>>() {
        };
        return JSON.parseObject(s,typeReference.getType());
    }

    public CoreWo getById(String woId){
        String s = redisTemplate.opsForValue().get(StringUtil.generateRedisKey(DATABASE, ID_KEY, woId));
        if(s == null){
            return null;
        }

        return JSON.parseObject(s,CoreWo.class);
    }

    public synchronized void setOne(CoreWo wo){

        redisTemplate.opsForValue().set(StringUtil.generateRedisKey(DATABASE, ID_KEY, wo.getId().toString()),JSON.toJSONString(wo));
    }

    public synchronized void createOne(CoreWo wo){
        List<CoreWo> coreWoList = this.getWoListByUserId(wo.getUserId().toString());
        coreWoList.add(wo);
        setWoList(coreWoList,wo.getUserId().toString());
        setOne(wo);
        List<CoreWo> distribute = getDistribute();
        if(distribute == null){
            distribute = new ArrayList<>();
        }
        distribute.add(wo);
        redisTemplate.opsForValue().set((StringUtil.generateRedisKey(DATABASE, DISTRIBUTE_KEY)),JSON.toJSONString(distribute));
        redisTemplate.opsForList().leftPush((StringUtil.generateRedisKey(DATABASE, NEW_KEY)),wo.getId().toString());
    }

    public List<CoreWo> getDistribute(){
        String s = redisTemplate.opsForValue().get(StringUtil.generateRedisKey(DATABASE, DISTRIBUTE_KEY));
        if(s == null){
            return null;
        }
        TypeReference<List<CoreWo>> typeReference = new TypeReference<List<CoreWo>>() {
        };
        return JSON.parseObject(s,typeReference.getType());
    }

    public void setDistribute(List<CoreWo> woList){
        redisTemplate.opsForValue().set(StringUtil.generateRedisKey(DATABASE, DISTRIBUTE_KEY),JSON.toJSONString(woList));
    }


    public void setWoList(List<CoreWo> coreWoList,String userId){
        redisTemplate.opsForValue().set(StringUtil.generateRedisKey(DATABASE, LIST_KEY, userId),JSON.toJSONString(coreWoList));
    }

    public void setPending(String userId,List<String> ids){
        redisTemplate.opsForValue().set(StringUtil.generateRedisKey(DATABASE,MANAGER_KEY,userId),JSON.toJSONString(ids));
    }

    public List<CoreWo> getPendingWO(String userId){
        String s = redisTemplate.opsForValue().get(StringUtil.generateRedisKey(DATABASE, MANAGER_KEY, userId));
        if(s == null){
            return null;
        }

        TypeReference<List<String>> typeReference = new TypeReference<List<String>>() {
        };

        List<String> woIds = JSON.parseObject(s, typeReference.getType());
        ArrayList<CoreWo> coreWos = new ArrayList<>();
        for (String woId : woIds) {
            coreWos.add(getById(woId));
        }
        return coreWos;
    }

    public List<String> getPendingIds(String userId){
        String s = redisTemplate.opsForValue().get(StringUtil.generateRedisKey(DATABASE, MANAGER_KEY, userId));
        if(s == null){
            return null;
        }

        TypeReference<List<String>> typeReference = new TypeReference<List<String>>() {
        };
        return JSON.parseObject(s,typeReference.getType());
    }

    /**
     * 弹出一个新插入数据
     * @return
     */
    public CoreWo popOneNew(){
        String s = redisTemplate.opsForList().rightPop(StringUtil.generateRedisKey(DATABASE, NEW_KEY));
        if(s == null){
            return null;
        }

        return getById(s);
    }
}
