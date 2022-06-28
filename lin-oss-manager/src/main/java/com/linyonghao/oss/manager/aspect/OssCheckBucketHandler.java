package com.linyonghao.oss.manager.aspect;

import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.stp.StpUtil;
import com.linyonghao.oss.common.service.ICoreBucketService;
import com.linyonghao.oss.manager.annotation.OssCheckBucket;
import com.linyonghao.oss.manager.utils.RegexValidateUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Aspect
@Component
public class OssCheckBucketHandler {


    @Autowired
    private ICoreBucketService coreBucketService;
    Logger logger = LoggerFactory.getLogger(OssCheckBucketHandler.class);
    @Pointcut("@annotation(com.linyonghao.oss.manager.annotation.OssCheckBucket)")
    public void pointCut(){};


    @Before("pointCut()")
    public void before(JoinPoint joinPoint){
        // TODO 增加管理员可通行
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        String contextPath = request.getServletPath();
        logger.info(contextPath);
        Pattern pattern = Pattern.compile("\\/space\\/(\\d+)\\/.*");
        Matcher matcher = pattern.matcher(contextPath);
        matcher.find();
        int count = matcher.groupCount();
        if(count != 1){
            throw new NotPermissionException("bucket-view");
        }
        if(!coreBucketService.isBelongUser(matcher.group(1), String.valueOf(StpUtil.getLoginId()))){
            throw new NotPermissionException("bucket-view");
        }

    }
}
