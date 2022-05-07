package com.linyonghao.oss.common.model;

import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

@TableName("core_bucket")
public class BucketModel {
    private long id;
    private long userId;
    private String name;
    private short ac;
    private LocalDateTime createTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getAc() {
        return ac;
    }

    public void setAc(short ac) {
        this.ac = ac;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "BucketModel{" +
                "id=" + id +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", ac=" + ac +
                ", createTime=" + createTime +
                '}';
    }
}
