package com.linyonghao.oss.core.interceptor;

import com.linyonghao.oss.core.constant.CommonConstant;
import com.linyonghao.oss.core.exception.HttpResponseException;

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

        if(request.getHeader(CommonConstant.AUTHENTICATION_HEADER) == null){
            if(request.getHeader(CommonConstant.TOKEN_NAME) == null){
                throw new HttpResponseException("缺少授权头 Authentication 或Token");
            }
        }

    }

}
