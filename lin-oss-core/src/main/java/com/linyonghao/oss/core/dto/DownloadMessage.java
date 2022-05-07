package com.linyonghao.oss.core.dto;

import com.linyonghao.oss.common.entity.OSSFile;
import com.linyonghao.oss.core.entity.DownloadParams;

import java.io.Serializable;

public class DownloadMessage implements Serializable {
    private DownloadParams downloadParams;
    private OSSFile ossFile;
    private String clientIp;
    private long userId;
    private String datetime;

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }




    public DownloadMessage(DownloadParams downloadParams, OSSFile ossFile,String clientIp,long userId,String datetime) {
        this.downloadParams = downloadParams;
        this.ossFile = ossFile;
        this.clientIp = clientIp;
        this.userId = userId;
        this.datetime = datetime;
    }

    public DownloadParams getDownloadParams() {
        return downloadParams;
    }

    public void setDownloadParams(DownloadParams downloadParams) {
        this.downloadParams = downloadParams;
    }

    public OSSFile getOssFile() {
        return ossFile;
    }

    public void setOssFile(OSSFile ossFile) {
        this.ossFile = ossFile;
    }
}
