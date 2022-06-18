package com.linyonghao.oss.manager.entity;

public class ResponseMessage {
    public static final int CODE_SUCCESS = 200;
    public static final int CODE_WARMING = -2;
    public static final int CODE_ERROR = -1;
    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
