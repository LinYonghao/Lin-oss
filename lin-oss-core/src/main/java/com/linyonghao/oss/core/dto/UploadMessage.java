package com.linyonghao.oss.core.dto;

import com.linyonghao.oss.common.model.UploadPolicy;
import com.linyonghao.oss.common.entity.OSSFile;
import com.linyonghao.oss.common.model.UserModel;

import java.io.Serializable;
import java.util.Date;

public class UploadMessage implements Serializable {
    public Date getTime() {
        return time;
    }

    private final Date time;

    public UploadMessage(OSSFile ossFile, UploadPolicy uploadPolicy, UserModel userModel, Date time) {
        this.ossFile = ossFile;
        this.uploadPolicy = uploadPolicy;
        this.userModel = userModel;
        this.time = time;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
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
    private UserModel userModel;

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
