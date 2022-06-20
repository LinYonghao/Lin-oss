package com.linyonghao.oss.manager.dto;

public class SpaceInfo {
    private String name;
    private long objectNum;
    private long spaceSize;
    private long apiNum;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getObjectNum() {
        return objectNum;
    }

    public void setObjectNum(long objectNum) {
        this.objectNum = objectNum;
    }

    public long getSpaceSize() {
        return spaceSize;
    }

    public void setSpaceSize(long spaceSize) {
        this.spaceSize = spaceSize;
    }

    public long getApiNum() {
        return apiNum;
    }

    public void setApiNum(long apiNum) {
        this.apiNum = apiNum;
    }
}
