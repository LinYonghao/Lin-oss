package com.linyonghao.oss.common.model;

import java.io.Serializable;

/**
 * 上传策略
 * 	max_size,
 * 	MINE,
 * 	key,
 * 	scope,
 * 	deadline,// 截至时间戳
 * 	callback,//上传成功将数据回调地址
 */
public class UploadPolicy implements Serializable {
   private long maxSize;
   private String MINE;
   private String key;
   private String scope;
   private long deadline;
   private String callback;
   private String ext;

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public long getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(long maxSize) {
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
