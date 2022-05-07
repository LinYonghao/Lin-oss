package com.linyonghao.oss.core.config;

import com.linyonghao.oss.common.model.HttpJSONResponse;
import com.linyonghao.oss.core.constant.Environment;
import com.linyonghao.oss.core.util.HttpJsonResult;
import com.linyonghao.oss.core.util.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class DefaultExceptionHandler {

    Logger logger = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public HttpJSONResponse defaultException(HttpServletResponse httpServletResponse, Exception e){
        String profile = SpringContextUtil.getProfile();
        logger.error(e.getMessage());
        if(profile.equals(Environment.DEVELOPMENT)) {
            return HttpJsonResult.fail(e.getMessage());
        }

        if(profile.equals(Environment.PRODUCE)){
            return HttpJsonResult.fail("系统错误,请重新操作");
        }

        return HttpJsonResult.fail("系统异常");
    }



}
