package com.linyonghao.oss.core.mapper;

import com.linyonghao.influxdb2.InfluxMapper;
import com.linyonghao.oss.core.dto.DownloadMessage;
import org.springframework.stereotype.Repository;

@Repository
public class DownloadLogMapper extends InfluxMapper<DownloadMessage> {
    public DownloadLogMapper() {
        init(DownloadLogMapper.class);
    }
}
