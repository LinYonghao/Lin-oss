package com.linyonghao.oss.common.model;

import com.linyonghao.influxdb2.annotation.DBField;
import com.linyonghao.influxdb2.annotation.Measurement;
import com.linyonghao.influxdb2.annotation.Tag;

@Measurement(name="storage_log")
public class StorageLog {

    @Tag
    private long userId;
    /**
     * 空间名
     */
    @Tag
    private long bucketId;

    @DBField
    private long size;


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getBucketId() {
        return bucketId;
    }

    public void setBucketId(long bucketId) {
        this.bucketId = bucketId;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
