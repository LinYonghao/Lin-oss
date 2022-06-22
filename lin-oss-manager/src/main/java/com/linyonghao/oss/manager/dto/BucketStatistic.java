package com.linyonghao.oss.manager.dto;

import com.linyonghao.influxdb2.entity.CountWithTime;
import com.linyonghao.oss.common.model.StorageLog;

import java.util.List;

public class BucketStatistic {
    List<CountWithTime> storage;
    List<CountWithTime> post;
    List<CountWithTime> get;
    List<CountWithTime> objNum;

    public List<CountWithTime> getObjNum() {
        return objNum;
    }

    public void setObjNum(List<CountWithTime> objNum) {
        this.objNum = objNum;
    }

    public List<CountWithTime> getStorage() {
        return storage;
    }

    public void setStorage(List<CountWithTime> storage) {
        this.storage = storage;
    }

    public List<CountWithTime> getPost() {
        return post;
    }

    public void setPost(List<CountWithTime> post) {
        this.post = post;
    }

    public List<CountWithTime> getGet() {
        return get;
    }

    public void setGet(List<CountWithTime> get) {
        this.get = get;
    }

}
