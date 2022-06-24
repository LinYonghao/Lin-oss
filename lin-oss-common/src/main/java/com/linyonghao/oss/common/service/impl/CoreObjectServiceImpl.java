package com.linyonghao.oss.common.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linyonghao.influxdb2.entity.CountWithTime;
import com.linyonghao.oss.common.dao.mapper.relationship.CoreObjectMapper;
import com.linyonghao.oss.common.dao.mapper.sequential.APILogMapper;
import com.linyonghao.oss.common.entity.CoreObject;
import com.linyonghao.oss.common.model.APILogModel;
import com.linyonghao.oss.common.service.ICoreObjectService;
import com.linyonghao.oss.common.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LinYonghao
 * @since 2022-06-17
 */
@Service
public class CoreObjectServiceImpl extends ServiceImpl<CoreObjectMapper, CoreObject> implements ICoreObjectService {

    @Autowired
    private APILogMapper apiLogMapper;
    /**
     * 获取某个bucket的对象数量和对象总存储量
     * @param userId
     * @param bucketId
     * @return int[objectNum,sizeSum]
     */
    @Override
    public long[] getObjectNumAndSizeSumByBucket(String userId, String bucketId) {
        Map<String, Object> resultMap = this.getBaseMapper().selectObjectNumAndSizeSumByBucket(userId, bucketId);
        if(resultMap.size() != 2){
            return new long[]{0L,0L};
        }
        String size = resultMap.get("size").toString();
        String count = resultMap.get("count").toString();
        return new long[]{Long.parseLong(count), Long.parseLong(size)};
    }

    @Override
    public List<CoreObject> getObjectByBucketId(String bucketId) {
        QueryWrapper<CoreObject> wrapper = new QueryWrapper<>();
        wrapper.eq("bucket_id",bucketId);
        return this.getBaseMapper().selectList(wrapper);
    }


}
