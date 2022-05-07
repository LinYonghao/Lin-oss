package com.linyonghao.oss.common.entity;

import java.io.Serializable;

public class OSSFile implements Serializable {
    private String name;
    private byte[] bin;
    private String md5;
    private String author;
    private String ext;
    private String absolutePath;
    private long size;
    private String remoteFilename;
    private String mine;

    public String getMine() {
        return mine;
    }

    public void setMine(String mine) {
        this.mine = mine;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getRemoteFilename() {
        return remoteFilename;
    }

    public void setRemoteFilename(String remoteFilename) {
        this.remoteFilename = remoteFilename;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public OSSFile(String name, byte[] bin, String md5, String author) {
        this.name = name;
        this.bin = bin;
        this.md5 = md5;
        this.author = author;
    }

    public OSSFile() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getBin() {
        return bin;
    }

    public void setBin(byte[] bin) {
        this.bin = bin;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}
