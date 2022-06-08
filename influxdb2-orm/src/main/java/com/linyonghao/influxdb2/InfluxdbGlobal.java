package com.linyonghao.influxdb2;

import com.influxdb.client.InfluxDBClient;

public class InfluxdbGlobal {
    public static InfluxDBClient influxDBClient;
    private static String bucketName;

    public static String getBucketName() {
        return bucketName;
    }

    public static void init(InfluxDBClient influxDBClient, String bucketName){
        InfluxdbGlobal.influxDBClient = influxDBClient;
        InfluxdbGlobal.bucketName = bucketName;
    }

    public static InfluxDBClient getInstance(){
        if(influxDBClient == null){
            throw new NullPointerException();
        }
        return influxDBClient;
    }


}
