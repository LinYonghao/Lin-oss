package com.linyonghao.oss.common.model;

import com.linyonghao.oss.common.entity.CoreObject;

public class ObjectBucketDO {
    private BucketModel bucketModel;
    private CoreObject objectModel;

    public BucketModel getBucketModel() {
        return bucketModel;
    }

    public void setBucketModel(BucketModel bucketModel) {
        this.bucketModel = bucketModel;
    }




    public CoreObject getObjectModel() {
        return objectModel;
    }

    public void setObjectModel(CoreObject objectModel) {
        this.objectModel = objectModel;
    }
}
