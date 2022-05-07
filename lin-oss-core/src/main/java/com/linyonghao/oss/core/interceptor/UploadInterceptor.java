package com.linyonghao.oss.core.interceptor;

import com.linyonghao.oss.core.constant.CommonConstant;
import com.linyonghao.oss.core.dto.UploadCertificationDTO;
import com.linyonghao.oss.core.exception.HttpResponseException;
import com.linyonghao.oss.core.service.certificate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UploadInterceptor implements HandlerInterceptor {

    @Autowired
    private UploadAuthenticator authenticator;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        UploadInterceptorValidator uploadInterceptorValidator = new UploadInterceptorValidator();

        try{
            uploadInterceptorValidator.validate(request,response);

            try{
                UploadCertificationDTO uploadCertificationDTO = authenticator.decryptCertification(request.getHeader(CommonConstant.AUTHENTICATION_HEADER));
                request.setAttribute("userModel",uploadCertificationDTO.getUserModel());
                request.setAttribute("uploadPolicy",uploadCertificationDTO.getUploadPolicy());
                return true;

            } catch (Exception e){
                e.printStackTrace();
                throw new HttpResponseException(e.getMessage());
            }

        }catch (HttpResponseException e){
            response.getWriter().write(e.getMessage());
            return false;
        }


    }


}
