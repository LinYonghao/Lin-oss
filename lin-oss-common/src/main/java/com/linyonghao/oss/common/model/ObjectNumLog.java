package com.linyonghao.oss.common.model;

import com.linyonghao.influxdb2.annotation.DBField;
import com.linyonghao.influxdb2.annotation.Measurement;
import com.linyonghao.influxdb2.annotation.Tag;

@Measurement(name="obj_num_log")
public class ObjectNumLog {

    @Tag
    private long userId;
    /**
     * 空间名
     */
    @Tag
    private long bucketId;

    @DBField
    private long num;

    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }

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


}
