package com.linyonghao.linosscore.dto;

import com.linyonghao.linosscore.model.UploadPolicy;
import com.linyonghao.linosscore.model.UserModel;

public class UploadCertificationDTO {
    private UserModel userModel;
    private UploadPolicy uploadPolicy;

    public UploadCertificationDTO(UserModel userModel, UploadPolicy uploadPolicy) {
        this.userModel = userModel;
        this.uploadPolicy = uploadPolicy;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public UploadPolicy getUploadPolicy() {
        return uploadPolicy;
    }

    public void setUploadPolicy(UploadPolicy uploadPolicy) {
        this.uploadPolicy = uploadPolicy;
    }
}
