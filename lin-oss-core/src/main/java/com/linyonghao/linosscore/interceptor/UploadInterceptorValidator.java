package com.linyonghao.linosscore.interceptor;

import com.linyonghao.linosscore.Constant;
import com.linyonghao.linosscore.exception.HttpResponseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UploadInterceptorValidator {

    public void validate(HttpServletRequest request, HttpServletResponse response) throws  HttpResponseException {


        if(!request.getMethod().equals("POST")){
            throw new HttpResponseException("请求方法应为 POST");
        }

        if (!request.getContentType().startsWith("multipart/form-data")) {
            response.setHeader("Content-type","application/json");
            throw new HttpResponseException("请求类型应为'multipart/form-data'");

        }

        if(request.getHeader(Constant.AUTHENTICATION_HEADER).isEmpty()){
            throw new HttpResponseException("缺少授权头 Authentication");
        }

    }

}
