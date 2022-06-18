package com.linyonghao.oss.core.dao;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.linyonghao.oss.common.config.database.InfluxdbConfig;
import com.linyonghao.oss.common.dao.mapper.sequential.UploadLogMapper;
import com.linyonghao.oss.common.model.UploadLogModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;


@SpringBootTest
@ComponentScan("com.linyonghao.oss")
public class testInfluxdb {

    @Autowired
    InfluxDBClient client;

    @Autowired
    UploadLogMapper uploadLogMapper;

    @Test
    public void testInsert(){
        String data = "mem,host=host1 used_percent=23.43234543";

        WriteApiBlocking writeApi = client.getWriteApiBlocking();
        writeApi.writeRecord(InfluxdbConfig.DEFAULT_BUCKET, InfluxdbConfig.DEFAULT_ORG, WritePrecision.NS, data);

    }

    @Test
    public void testQuery(){
//        List<UploadLogModel> all = uploadLogMapper.query().range("time(v:\"2020-06-01T00:00:00Z\")","now()") .all();
//        System.out.println(all);

    }

}
