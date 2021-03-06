package com.linyonghao.oss.common.model;

import com.linyonghao.influxdb2.annotation.DBField;
import com.linyonghao.influxdb2.annotation.Measurement;
import com.linyonghao.influxdb2.annotation.Tag;

import java.io.Serializable;
@Measurement(name="api_log")
public class APILogModel implements Serializable {
    public static final int UPLOAD = 1;
    public static final int DOWNLOAD = 2;

    @Tag
    private long userId;
    /**
     * 空间名
     */
    @Tag
    private long bucketId;

    @DBField
    private String ip;

    @Tag
    private int type;

    public long getBucketId() {
        return bucketId;
    }

    public void setBucketId(long bucketId) {
        this.bucketId = bucketId;
    }

    private long time;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
