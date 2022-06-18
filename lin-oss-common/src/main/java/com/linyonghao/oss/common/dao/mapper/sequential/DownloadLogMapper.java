package com.linyonghao.oss.common.dao.mapper.sequential;

import com.linyonghao.influxdb2.InfluxMapper;
import com.linyonghao.oss.common.dto.DownloadMessage;
import org.springframework.stereotype.Repository;

@Repository
public class DownloadLogMapper extends InfluxMapper<DownloadMessage> {
    public DownloadLogMapper() {
        init(DownloadMessage.class);
    }
}
