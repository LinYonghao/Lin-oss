package com.linyonghao.oss.common.dao.mapper.sequential;

import com.linyonghao.influxdb2.InfluxMapper;
import com.linyonghao.oss.common.model.StorageLog;
import org.springframework.stereotype.Repository;

@Repository
public class StorageLogMapper extends InfluxMapper<StorageLog> {
    public StorageLogMapper() {
        init(StorageLog.class);
    }
}
