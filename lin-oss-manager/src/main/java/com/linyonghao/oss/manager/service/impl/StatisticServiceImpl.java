package com.linyonghao.oss.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.linyonghao.influxdb2.entity.CountWithTime;
import com.linyonghao.oss.common.dao.mapper.relationship.CoreBucketMapper;
import com.linyonghao.oss.common.dao.mapper.relationship.CoreObjectMapper;
import com.linyonghao.oss.common.dao.mapper.sequential.APILogMapper;
import com.linyonghao.oss.common.dao.mapper.sequential.ObjNumLogMapper;
import com.linyonghao.oss.common.dao.mapper.sequential.StorageLogMapper;
import com.linyonghao.oss.common.entity.CoreBucket;
import com.linyonghao.oss.common.model.APILogModel;
import com.linyonghao.oss.common.model.StorageLog;
import com.linyonghao.oss.common.utils.DateUtil;
import com.linyonghao.oss.manager.dto.BucketStatistic;
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

    @Autowired
    StorageLogMapper storageLogMapper;

    @Autowired
    ObjNumLogMapper objNumLogMapper;

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

    @Override
    public BucketStatistic getBeforeNDayBucketInfoLimitDay(String bucketId, int days) {
        // 获取存储量
        /**
         *  from(bucket: "lin_oss")
         *   |> range(start: 1600827200, stop: 1655874723)
         *   |> filter(fn: (r) => r["_measurement"] == "storage_log")
         *   |> filter(fn: (r) => r["userId"] == "1")
         *   |>group()
         *   |> filter(fn: (r) => r["bucketId"] == "12312313")
         *   |> aggregateWindow(every: 10d, fn:max,createEmpty: true )
         */
        Date startDate = DateUtil.getNDayByToday(days);
        String interval = "1d";
        Date endDate =DateUtil.getNextDayStart();
        BucketStatistic bucketStatistic = new BucketStatistic();
        bucketStatistic.setStorage(this.getStorage(bucketId, startDate, endDate, interval));
        bucketStatistic.setGet(this.getGETCount(bucketId,startDate,endDate,interval));
        bucketStatistic.setPost(this.getPOSTCount(bucketId,startDate,endDate,interval));
        bucketStatistic.setObjNum(this.getObjNum(bucketId,startDate,endDate,interval));
        return bucketStatistic;

    }


    public List<CountWithTime> getStorage(String bucketId, Date startTime, Date endTime, String interval){
          return storageLogMapper.query()
                .range(startTime.getTime() / 1000, endTime.getTime() / 1000)
                .filterMeasurement()
                .filterEqString("bucketId", bucketId)
                .group()
                .aggregateWindow(interval, "max", true)
                .oneValue();
    }


    public List<CountWithTime> getAPICount(String bucketId,Date startTime,Date endTime,String interval){
        return apiLogMapper.query()
                .range(startTime.getTime()/1000,endTime.getTime()/1000)
                .filterMeasurement()
                .filterEqString("bucketId",bucketId)
                .window(interval,true)
                .group()
                .count();
    }

    public List<CountWithTime> getAPICountByType(String bucketId,Date startTime,Date endTime,String interval,int type){
        return apiLogMapper.query()
                .range(startTime.getTime()/1000,endTime.getTime()/1000)
                .filterMeasurement()
                .filterEqString("bucketId",bucketId)
                .filterEqString("type", String.valueOf(type))
                .group()
                .window(interval,true)
                .count();
    }

    public List<CountWithTime> getGETCount(String bucketId,Date startTime,Date endTime,String interval){
        return getAPICountByType(bucketId, startTime, endTime, interval, APILogModel.DOWNLOAD);
    }

    public List<CountWithTime> getPOSTCount(String bucketId,Date startTime,Date endTime,String interval){
        return getAPICountByType(bucketId, startTime, endTime, interval, APILogModel.UPLOAD);
    }

    public List<CountWithTime> getObjNum(String bucketId,Date startTime,Date endTime,String interval){
        return objNumLogMapper.query()
                .range(startTime.getTime() / 1000, endTime.getTime() / 1000)
                .filterMeasurement()
                .filterEqString("bucketId", bucketId)
                .group()
                .aggregateWindow(interval, "max", true)
                .oneValue();
    }




}