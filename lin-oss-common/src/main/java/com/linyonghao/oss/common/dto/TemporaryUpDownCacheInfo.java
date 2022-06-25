package com.linyonghao.oss.common.dto;


/**
 * 用于管理端申请临时上传权限缓存
 */
public class TemporaryUpDownCacheInfo {
    private String bucketId;
    private String token;

    private String userId;



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

    public TemporaryUpDownCacheInfo(String bucketId, String token, String userId, long expireTimeMS) {
        this.bucketId = bucketId;
        this.token = token;
        this.userId = userId;
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
