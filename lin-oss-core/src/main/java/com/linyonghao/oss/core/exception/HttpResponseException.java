package com.linyonghao.oss.core.exception;

import com.linyonghao.oss.core.util.HttpResult;

public class HttpResponseException extends Exception{
    public HttpResponseException(String message) {
        super(HttpResult.fail(message).toJSONString());

    }
}
