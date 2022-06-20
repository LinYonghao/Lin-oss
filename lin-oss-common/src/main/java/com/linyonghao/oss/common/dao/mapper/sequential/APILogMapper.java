package com.linyonghao.oss.common.dao.mapper.sequential;

import com.linyonghao.influxdb2.InfluxMapper;
import com.linyonghao.oss.common.model.APILogModel;
import org.springframework.stereotype.Repository;

@Repository
public class APILogMapper extends InfluxMapper<APILogModel> {
    public APILogMapper() {
        init(APILogModel.class);
    }


    public long getApiNumByBucket(String userId,String bucketId){
        return 0;
    }
}
