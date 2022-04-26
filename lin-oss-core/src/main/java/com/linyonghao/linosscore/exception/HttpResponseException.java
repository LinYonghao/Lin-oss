package com.linyonghao.linosscore.exception;

import com.linyonghao.linosscore.util.HttpResult;

public class HttpResponseException extends Exception{
    public HttpResponseException(String message) {
        super(HttpResult.fail(message).toJSONString());

    }
}
