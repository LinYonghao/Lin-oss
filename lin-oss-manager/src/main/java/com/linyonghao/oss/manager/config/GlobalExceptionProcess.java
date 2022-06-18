package com.linyonghao.oss.manager.config;


import cn.dev33.satoken.exception.NotLoginException;
import com.linyonghao.oss.manager.utils.ResponseUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalExceptionProcess {

    @ExceptionHandler(NotLoginException.class)
    public ModelAndView notLoginProcess(HttpServletResponse httpServletResponse, Exception e){
        return ResponseUtil.error("forward:/user/login",null,"请先登录哦,宝!");
    }

}
