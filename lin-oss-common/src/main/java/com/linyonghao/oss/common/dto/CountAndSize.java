package com.linyonghao.oss.common.dto;

public class CountAndSize {

    private long cont;

    public CountAndSize(long cont, long size) {
        this.cont = cont;
        this.size = size;
    }

    private long size;

    public long getCont() {
        return cont;
    }

    public void setCont(long cont) {
        this.cont = cont;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
