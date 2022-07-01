package com.linyonghao.oss.common.vo;

import java.util.Date;

public class CoreObjectVO {

    private static final long serialVersionUID = 1L;

    /**
     * id 雪花算法
     */
    private Long id;

    /**
     * 桶id
     */
    private Long bucketId;

    /**
     * 键
     */
    private String remoteKey;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * Mine类型
     */
    private String mine;

    /**
     * 服务器路径
     */
    private String localKey;

    /**
     * 文件大小
     *
     */
    private String size;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBucketId() {
        return bucketId;
    }

    public void setBucketId(Long bucketId) {
        this.bucketId = bucketId;
    }

    public String getRemoteKey() {
        return remoteKey;
    }

    public void setRemoteKey(String remoteKey) {
        this.remoteKey = remoteKey;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMine() {
        return mine;
    }

    public void setMine(String mine) {
        this.mine = mine;
    }

    public String getLocalKey() {
        return localKey;
    }

    public void setLocalKey(String localKey) {
        this.localKey = localKey;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public CoreObjectVO(Long id, Long bucketId, String remoteKey, Date createTime, String mine, String localKey, String size) {
        this.id = id;
        this.bucketId = bucketId;
        this.remoteKey = remoteKey;
        this.createTime = createTime;
        this.mine = mine;
        this.localKey = localKey;
        this.size = size;
    }
}
