package com.linyonghao.influxdb2.entity;


import com.linyonghao.influxdb2.annotation.DBField;
import com.linyonghao.influxdb2.annotation.Measurement;
import com.linyonghao.influxdb2.annotation.Tag;

@Measurement
public class User {
    @Tag
    private String username;

    @DBField
    private String password;

}
