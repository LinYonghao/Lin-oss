package com.linyonghao.linosscore.interceptor;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.session.SaSessionCustomUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.linyonghao.linosscore.Constant;
import com.linyonghao.linosscore.dto.UploadCertificationDTO;
import com.linyonghao.linosscore.exception.HttpResponseException;
import com.linyonghao.linosscore.service.certificate.*;
import com.linyonghao.linosscore.util.HttpResult;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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
                UploadCertificationDTO uploadCertificationDTO = authenticator.decryptCertification(request.getHeader(Constant.AUTHENTICATION_HEADER));
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
