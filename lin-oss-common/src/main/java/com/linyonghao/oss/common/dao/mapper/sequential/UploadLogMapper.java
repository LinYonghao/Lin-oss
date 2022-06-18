package com.linyonghao.oss.common.dao.mapper.sequential;

import com.linyonghao.influxdb2.InfluxMapper;
import com.linyonghao.oss.common.model.UploadLogModel;
import org.springframework.stereotype.Repository;

@Repository
public class UploadLogMapper extends InfluxMapper<UploadLogModel> {
    public UploadLogMapper() {
        init(UploadLogModel.class);
    }
}
