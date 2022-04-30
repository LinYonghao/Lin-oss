package com.linyonghao.linosscore.dto;

import com.linyonghao.linosscore.entity.OSSFile;
import com.linyonghao.linosscore.model.UploadPolicy;

import java.io.Serializable;

public class UploadMessage implements Serializable {
    public UploadMessage(OSSFile ossFile, UploadPolicy uploadPolicy) {
        this.ossFile = ossFile;
        this.uploadPolicy = uploadPolicy;
    }

    @Override
    public String toString() {
        return "UploadMessage{" +
                "ossFile=" + ossFile +
                ", uploadPolicy=" + uploadPolicy +
                '}';
    }

    private OSSFile ossFile;
    private UploadPolicy uploadPolicy;

    public OSSFile getOssFile() {
        return ossFile;
    }

    public void setOssFile(OSSFile ossFile) {
        this.ossFile = ossFile;
    }

    public UploadPolicy getUploadPolicy() {
        return uploadPolicy;
    }

    public void setUploadPolicy(UploadPolicy uploadPolicy) {
        this.uploadPolicy = uploadPolicy;
    }
}
