package com.linyonghao.oss.manager.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.linyonghao.influxdb2.Query;
import com.linyonghao.oss.common.utils.StringUtil;
import com.linyonghao.oss.manager.entity.CoreWo;
import com.linyonghao.oss.manager.entity.CoreWoRecord;
import com.linyonghao.oss.manager.mapper.CoreWoRecordMapper;
import com.linyonghao.oss.manager.service.ICoreWoRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Lin Yonghao
 * @since 2022-06-27
 */
@Service
public class CoreWoRecordServiceImpl extends ServiceImpl<CoreWoRecordMapper, CoreWoRecord> implements ICoreWoRecordService {

    @Autowired
    WoRecordRedisService woRecordRedisService;

    @Override
    public List<CoreWoRecord> getRecordList(String woId) {
        List<CoreWoRecord> woList = woRecordRedisService.getWoRecordList(woId);
        if(woList == null){
            QueryWrapper<CoreWoRecord> wrapper = new QueryWrapper<>();
            wrapper.eq("wo_id",woId);
            woList = getBaseMapper().selectList(wrapper);
            woRecordRedisService.setWoRecordList(woList,woId);
        }

        return woList;
    }

    @Override
    public synchronized void insertOne(CoreWoRecord record) {
        List<CoreWoRecord> woRecordList = woRecordRedisService.getWoRecordList(record.getWoId().toString());
        if(woRecordList == null){
            woRecordList = new ArrayList<>();
        }
        woRecordList.add(record);
        woRecordRedisService.setWoRecordList(woRecordList,record.getWoId().toString());
    }
}


@Service
class WoRecordRedisService {
@Autowired
    StringRedisTemplate redisTemplate;

    @Value("${redis.key.wo.record}")
    private String KEY;

    @Value("${redis.database}")
    private String DATABASE;

    public List<CoreWoRecord> getWoRecordList(String woId){
        String s = redisTemplate.opsForValue().get(StringUtil.generateRedisKey(DATABASE, KEY, woId));
        if(s == null){
            return null;
        }
        TypeReference<List<CoreWoRecord>> typeReference = new TypeReference<List<CoreWoRecord>>() {
        };
        return JSON.parseObject(s,typeReference.getType());
    }

    public void setWoRecordList(List<CoreWoRecord> coreWoList,String woId){
        redisTemplate.opsForValue().set(StringUtil.generateRedisKey(DATABASE, KEY, woId),JSON.toJSONString(coreWoList));
    }


}
