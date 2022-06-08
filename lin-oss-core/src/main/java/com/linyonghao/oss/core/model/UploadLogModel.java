package com.linyonghao.oss.core.model;

import com.linyonghao.influxdb2.InfluxMapper;
import com.linyonghao.influxdb2.annotation.DBField;
import com.linyonghao.influxdb2.annotation.Measurement;
import com.linyonghao.influxdb2.annotation.Tag;
import com.linyonghao.oss.common.model.UploadPolicy;

@Measurement(name="upload_log")
public class UploadLogModel extends InfluxMapper<UploadLogModel> {
    @Tag
    public long userId;
    @Tag
    public String fileKey;
    @DBField
    public long size;
    @DBField
    public UploadPolicy uploadPolicy;

    public long time;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public UploadLogModel() {
    }

    public UploadLogModel(long userId, String fileKey, long size, UploadPolicy uploadPolicy, long time) {
        this.userId = userId;
        this.fileKey = fileKey;
        this.size = size;
        this.uploadPolicy = uploadPolicy;
        this.time = time;
    }

    @Override
    public String toString() {
        return "UploadLogModel{" +
                "userId=" + userId +
                ", fileKey='" + fileKey + '\'' +
                ", size=" + size +
                ", uploadPolicy=" + uploadPolicy +
                ", time=" + time +
                '}';
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public UploadPolicy getUploadPolicy() {
        return uploadPolicy;
    }

    public void setUploadPolicy(UploadPolicy uploadPolicy) {
        this.uploadPolicy = uploadPolicy;
    }
}
