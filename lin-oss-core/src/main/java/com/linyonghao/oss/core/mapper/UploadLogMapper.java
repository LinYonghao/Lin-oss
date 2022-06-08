package com.linyonghao.oss.core.mapper;

import com.linyonghao.influxdb2.InfluxMapper;
import com.linyonghao.oss.core.model.UploadLogModel;
import org.springframework.stereotype.Repository;

@Repository
public class UploadLogMapper extends InfluxMapper<UploadLogModel> {
    public UploadLogMapper() {
        init(UploadLogModel.class);
    }
}
