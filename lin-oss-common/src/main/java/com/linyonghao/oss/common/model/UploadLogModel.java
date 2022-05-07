package com.linyonghao.oss.common.model;

import java.util.Date;

public class UploadLogModel {
    @Override
    public String toString() {
        return "UploadLogModel{" +
                "ts=" + ts +
                ", speed=" + speed +
                '}';
    }

    private Date ts;
    private int speed;

    public Date getTs() {
        return ts;
    }

    public void setTs(Date ts) {
        this.ts = ts;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
