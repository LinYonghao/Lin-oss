package com.linyonghao.oss.common.dto;

import com.linyonghao.influxdb2.annotation.DBField;
import com.linyonghao.influxdb2.annotation.Measurement;
import com.linyonghao.influxdb2.annotation.Tag;
import com.linyonghao.oss.common.entity.DownloadParams;

import java.io.Serializable;
@Measurement(name = "download_log")
public class DownloadMessage implements Serializable {
    @DBField
    private DownloadParams downloadParams;
    @DBField
    private String fileKey;
    @DBField
    private long size;
    @DBField
    private String clientIp;
    @Tag
    private long userId;
    private long datetime;

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public DownloadMessage(DownloadParams downloadParams, String fileKey, long size, String clientIp, long userId, long datetime) {
        this.downloadParams = downloadParams;
        this.fileKey = fileKey;
        this.size = size;
        this.clientIp = clientIp;
        this.userId = userId;
        this.datetime = datetime;
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

    public DownloadParams getDownloadParams() {
        return downloadParams;
    }

    public void setDownloadParams(DownloadParams downloadParams) {
        this.downloadParams = downloadParams;
    }


}
