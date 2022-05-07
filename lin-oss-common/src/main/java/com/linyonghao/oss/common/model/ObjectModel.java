package com.linyonghao.oss.common.model;

import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalTime;

@TableName("core_object")
public class ObjectModel {
//    idbigint(20) unsigned NOT NULLid 雪花算法
//    bucket_idbigint(20) unsigned NOT NULL桶id
//    keyvarchar(512) NOT NULL键
//    create_timedatetime NOT NULL创建时间
//    minevarchar(32) NULLMine类型
//    local_keyvarchar(512) NOT NULL服务器路径

    private long id;
    private long bucketId;
    private String key;
    private LocalTime createTime;
    private String mine;
    private String localKey;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBucketId() {
        return bucketId;
    }

    public void setBucketId(long bucketId) {
        this.bucketId = bucketId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public LocalTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalTime createTime) {
        this.createTime = createTime;
    }

    public String getMine() {
        return mine;
    }

    public void setMine(String mine) {
        this.mine = mine;
    }

    @Override
    public String toString() {
        return "ObjectModel{" +
                "id=" + id +
                ", bucketId=" + bucketId +
                ", key='" + key + '\'' +
                ", createTime=" + createTime +
                ", mine='" + mine + '\'' +
                ", localKey='" + localKey + '\'' +
                '}';
    }

    public String getLocalKey() {
        return localKey;
    }

    public void setLocalKey(String localKey) {
        this.localKey = localKey;
    }
}
