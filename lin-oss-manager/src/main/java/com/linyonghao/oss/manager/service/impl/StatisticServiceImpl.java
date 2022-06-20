package com.linyonghao.oss.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.linyonghao.influxdb2.entity.CountWithTime;
import com.linyonghao.oss.common.dao.mapper.relationship.CoreBucketMapper;
import com.linyonghao.oss.common.dao.mapper.relationship.CoreObjectMapper;
import com.linyonghao.oss.common.dao.mapper.relationship.ObjectMapper;
import com.linyonghao.oss.common.dao.mapper.sequential.APILogMapper;
import com.linyonghao.oss.common.entity.CoreBucket;
import com.linyonghao.oss.common.entity.CoreObject;
import com.linyonghao.oss.manager.dto.SpaceInfo;
import com.linyonghao.oss.manager.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StatisticServiceImpl implements StatisticService {
    @Autowired
    CoreBucketMapper coreBucketMapper;

    @Autowired
    CoreObjectMapper coreObjectMapper;
    @Autowired
    APILogMapper apiLogMapper;

    @Override
    public long getSpaceNumById(String id) {
        QueryWrapper<CoreBucket> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",id);
        return coreBucketMapper.selectCount(wrapper);
    }

    @Override
    public long getAPITodayNumById(String id) {
        Date date = new Date();
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        instance.set(Calendar.HOUR_OF_DAY,0);
        instance.set(Calendar.MINUTE,0);
        instance.set(Calendar.SECOND,0);
        List<CountWithTime> count = apiLogMapper.query()
                .range(instance.getTime().getTime()/1000,date.getTime()/1000)
                .filterMeasurement()
                .filterEqString("userId",id)
                .group()
                .count();

        long c = 0;
        for (CountWithTime countWithTime : count) {
            c += countWithTime.getCount();
        }

        return c;
    }

    @Override
    public List<CountWithTime> getTodayAPINumById(String id, String every) {
        Date date = new Date();
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        instance.set(Calendar.HOUR_OF_DAY,0);
        instance.set(Calendar.MINUTE,0);
        instance.set(Calendar.SECOND,0);
        List<CountWithTime> count = apiLogMapper.query()
                .range(instance.getTime().getTime() / 1000, date.getTime() / 1000)
                .filterMeasurement()
                .filterEqString("userId", id)
                .group()
                .filterEqString("_field", "ip")
                .window(every, true)
                .count();
        return count;
    }


    @Override
    public long getAPINumBySpace(String id) {
        return 0;
    }

    @Override
    public Long getObjectNumById(String id) {
        return coreObjectMapper.getObjectNumByUserId(id);
    }

    @Override
    public List<SpaceInfo> getSpaceInfoById(String id) {
        /**
         *    private long objectNum;
         *     private long spaceSize;
         */
        QueryWrapper<CoreBucket> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",id);
        List<CoreBucket> coreBuckets = coreBucketMapper.selectList(wrapper);
        ArrayList<SpaceInfo> spaceInfos = new ArrayList<>();
        for (CoreBucket coreBucket : coreBuckets) {
            SpaceInfo spaceInfo = new SpaceInfo();
            spaceInfo.setName(coreBucket.getName());
            Map<String, Object> result = coreObjectMapper.selectObjectNumAndSizeSumByBucket(id, coreBucket.getId().toString());
            if(result.size() == 2){
                spaceInfo.setSpaceSize(Integer.parseInt(result.get("size").toString()));
                spaceInfo.setObjectNum(Integer.parseInt(result.get("count").toString()));
            }
            spaceInfos.add(spaceInfo);
        }
        return spaceInfos;
    }
}
