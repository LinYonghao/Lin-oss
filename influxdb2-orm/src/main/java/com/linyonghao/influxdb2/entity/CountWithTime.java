package com.linyonghao.influxdb2.entity;

public class CountWithTime {
    private long count;
    private long time;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public CountWithTime(long count, long time) {
        this.count = count;
        this.time = time;
    }
}
