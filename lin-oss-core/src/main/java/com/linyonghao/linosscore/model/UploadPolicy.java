package com.linyonghao.linosscore.model;

/**
 * 上传策略
 * 	max_size,
 * 	MINE,
 * 	key,
 * 	scope,
 * 	deadline,// 截至时间戳
 * 	callback,//上传成功将数据回调地址
 */
public class UploadPolicy {
   private String maxSize;
   private String MINE;
   private String key;
   private String scope;
   private long deadline;
   private String callback;

    public String getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(String maxSize) {
        this.maxSize = maxSize;
    }

    public String getMINE() {
        return MINE;
    }

    public void setMINE(String MINE) {
        this.MINE = MINE;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public long getDeadline() {
        return deadline;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    @Override
    public String toString() {
        return "UploadPolicy{" +
                "maxSize='" + maxSize + '\'' +
                ", MINE='" + MINE + '\'' +
                ", key='" + key + '\'' +
                ", scope='" + scope + '\'' +
                ", deadline=" + deadline +
                ", callback='" + callback + '\'' +
                '}';
    }
}
