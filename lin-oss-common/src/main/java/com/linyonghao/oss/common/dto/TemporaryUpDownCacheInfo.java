package com.linyonghao.oss.common.dto;


/**
 * 用于管理端申请临时上传权限缓存
 */
public class TemporaryUpDownCacheInfo {
    private String bucketId;
    private String token;

    /**
     * 过期时间
     */
    private long expireTimeMS;

    public long getExpireTimeMS() {
        return expireTimeMS;
    }

    public void setExpireTimeMS(long expireTimeMS) {
        this.expireTimeMS = expireTimeMS;
    }

    public TemporaryUpDownCacheInfo(String bucketId, String token, long expireTimeMS) {
        this.bucketId = bucketId;
        this.token = token;
        this.expireTimeMS = expireTimeMS;
    }

    public String getBucketId() {
        return bucketId;
    }

    public void setBucketId(String bucketId) {
        this.bucketId = bucketId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
