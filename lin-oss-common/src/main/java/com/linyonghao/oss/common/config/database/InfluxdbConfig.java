package com.linyonghao.oss.common.config.database;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.linyonghao.influxdb2.InfluxdbGlobal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
public class InfluxdbConfig {

    @Value("${spring.datasource.influx.token}")
    private String token;

    @Value("${spring.datasource.influx.username}")
    private String username;

    @Value("${spring.datasource.influx.password}")
    private String password;

    @Value("${spring.datasource.influx.url}")
    private String url;

    @Value("${spring.datasource.influx.bucket}")
    private String bucket;

    public static final String DEFAULT_BUCKET = "lin_oss";

    public static final String DEFAULT_ORG = "lin_oss";


    @Bean
    public InfluxDBClient getClient() {
        if (!StringUtils.hasLength(url)) {
            throw new IllegalArgumentException("influxdb can not have a illegal url");
        }

        if (!StringUtils.hasLength(token)) {
            throw new IllegalArgumentException("influxdb can not have a illegal token");
        }
        InfluxDBClient influxDBClient = InfluxDBClientFactory.create(url, token.toCharArray(), DEFAULT_ORG, bucket);
        InfluxdbGlobal.init(influxDBClient,bucket);

        return influxDBClient;
    }
}
