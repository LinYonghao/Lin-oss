package com.linyonghao.oss.core.interceptor;

import com.linyonghao.oss.common.config.SystemConfig;
import com.linyonghao.oss.common.dto.TemporaryUpDownCacheInfo;
import com.linyonghao.oss.common.entity.CoreBucket;
import com.linyonghao.oss.common.model.UploadPolicy;
import com.linyonghao.oss.common.model.UserModel;
import com.linyonghao.oss.common.service.ICoreBucketService;
import com.linyonghao.oss.common.service.UserService;
import com.linyonghao.oss.common.service.cache.UserCacheService;
import com.linyonghao.oss.common.service.impl.TemporaryUpDownRedisService;
import com.linyonghao.oss.core.constant.CommonConstant;
import com.linyonghao.oss.core.dto.UploadCertificationDTO;
import com.linyonghao.oss.core.exception.HttpResponseException;
import com.linyonghao.oss.core.service.certificate.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Date;

@Component
public class UploadInterceptor implements HandlerInterceptor {

    @Autowired
    private UploadAuthenticatorService authenticator;

    @Autowired
    private TemporaryUpDownRedisService temporaryUpDownRedisService;

    @Autowired
    UserService userService;

    @Autowired
    SystemConfig systemConfig;

    @Autowired
    ICoreBucketService bucketService;

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws IOException {

        UploadInterceptorValidator uploadInterceptorValidator = new UploadInterceptorValidator();

        try{
            uploadInterceptorValidator.validate(request,response);

            try{
                // Token验证
                if(request.getHeader(CommonConstant.TOKEN_NAME).isEmpty()){
                    UploadCertificationDTO uploadCertificationDTO = authenticator.decryptCertification(request.getHeader(CommonConstant.AUTHENTICATION_HEADER));
                    request.setAttribute("userModel",uploadCertificationDTO.getUserModel());
                    request.setAttribute("uploadPolicy",uploadCertificationDTO.getUploadPolicy());
                }else{
                    // 管理端临时授权
                    TemporaryUpDownCacheInfo cacheInfo = temporaryUpDownRedisService.get(request.getHeader(CommonConstant.TOKEN_NAME));
                    if(cacheInfo == null){
                        throw new HttpResponseException("Token失效");
                    }
                    String userId = cacheInfo.getUserId();
                    UserModel user = userService.getUser(userId);
                    request.setAttribute("userModel",user);
                    String key = request.getParameter("key");
                    if(key == null){
                        throw new HttpResponseException("找不到key字段");
                    }
                    UploadPolicy uploadPolicy = new UploadPolicy();
                    CoreBucket bucket = bucketService.getById(cacheInfo.getBucketId());
                    uploadPolicy.setScope(bucket.getName());
                    uploadPolicy.setKey(key);
                    uploadPolicy.setDeadline(new Date().getTime() + systemConfig.temporaryUpDownExpired);
                    request.setAttribute("uploadPolicy",uploadPolicy);
                }

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
