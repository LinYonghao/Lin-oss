package com.linyonghao.linosscore.model;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class UserModel implements Serializable {
    private Long id;
    private String email;
    private String mobile;
    private String mobileAreaCode;
    private String accessKey;
    private String SecretKey;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobileAreaCode() {
        return mobileAreaCode;
    }

    public void setMobileAreaCode(String mobileAreaCode) {
        this.mobileAreaCode = mobileAreaCode;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return SecretKey;
    }
    @NotNull
    public void setSecretKey(String secretKey) {
        SecretKey = secretKey;
    }
}
