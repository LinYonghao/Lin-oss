package com.linyonghao.oss.common.model;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
@TableName("core_users")
public class UserModel implements Serializable {
    public UserModel() {
    }

    private Long id;
    private String email;
    private String mobile;
    private String mobileAreaCode;
    private String accessKey;
    private String SecretKey;
    private String salt;
    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

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
    public void setSecretKey(String secretKey) {
        SecretKey = secretKey;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", mobileAreaCode='" + mobileAreaCode + '\'' +
                ", accessKey='" + accessKey + '\'' +
                ", SecretKey='" + SecretKey + '\'' +
                ", salt='" + salt + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
