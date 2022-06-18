package com.linyonghao.oss.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.linyonghao.influxdb2.entity.CountWithTime;
import com.linyonghao.oss.common.dao.mapper.relationship.CoreBucketMapper;
import com.linyonghao.oss.common.dao.mapper.relationship.CoreObjectMapper;
import com.linyonghao.oss.common.dao.mapper.sequential.APILogMapper;
import com.linyonghao.oss.common.entity.CoreBucket;
import com.linyonghao.oss.common.entity.CoreObject;
import com.linyonghao.oss.common.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
        instance.set(Calendar.HOUR,0);
        instance.set(Calendar.MINUTE,0);
        instance.set(Calendar.SECOND,0);
        List<CountWithTime> count = apiLogMapper.query()
                .range(instance.getTime(),date)
                .filterMeasurement()
                .filterEqString("userId",id).count();
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
        return apiLogMapper.query()
                .range(instance.getTime().getTime()/1000,date.getTime()/1000)
                .filterMeasurement()
                .filterEqString("userId",id)
                .filterEqString("_field","ip")
                .window(every,true)
                .count();

    }


    @Override
    public long getAPINumBySpace(String id) {
        return 0;
    }

    @Override
    public Long getObjectNumById(String id) {
        return coreObjectMapper.getObjectNumByUserId(id);
    }
}
