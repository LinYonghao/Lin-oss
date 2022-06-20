package com.linyonghao.oss.common.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linyonghao.influxdb2.entity.CountWithTime;
import com.linyonghao.oss.common.dao.mapper.relationship.CoreBucketMapper;
import com.linyonghao.oss.common.dao.mapper.sequential.APILogMapper;
import com.linyonghao.oss.common.entity.CoreBucket;
import com.linyonghao.oss.common.model.APILogModel;
import com.linyonghao.oss.common.service.ICoreBucketService;
import com.linyonghao.oss.common.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LinYonghao
 * @since 2022-06-17
 */
@Service
public class CoreBucketServiceImpl extends ServiceImpl<CoreBucketMapper, CoreBucket> implements ICoreBucketService {
    @Autowired
    private APILogMapper apiLogMapper;

    @Override
    public boolean isBelongUser(String bucketId, String userId) {
        QueryWrapper<CoreBucket> wrapper = new QueryWrapper<>();
        wrapper.eq("id",bucketId);
        wrapper.eq("user_id",userId);
        long count = this.count(wrapper);
        return count > 0;
    }
    @Override
    public long getThisMonthGETCount(String bucketId) {

        List<CountWithTime> countWithTimes = apiLogMapper.query().range(DateUtil.getThisMonthStartDay().getTime() / 1000, new Date().getTime() / 1000)
                .filterMeasurement()
                .filterEqString("type", String.valueOf(APILogModel.DOWNLOAD))
                .count();

        if(countWithTimes.size() == 1){
            return countWithTimes.get(0).getCount();
        }
        return 0;
    }

    @Override
    public long getThisMonthPOSTCount(String bucketId) {
        List<CountWithTime> countWithTimes = apiLogMapper.query().range(DateUtil.getThisMonthStartDay().getTime() / 1000, new Date().getTime() / 1000)
                .filterMeasurement()
                .filterEqString("type", String.valueOf(APILogModel.UPLOAD))
                .count();

        if(countWithTimes.size() == 1){
            return countWithTimes.get(0).getCount();
        }
        return 0;
    }
}
