package com.linyonghao.oss.manager.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.linyonghao.oss.manager.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class IndexController {
    Logger logger = LoggerFactory.getLogger(IndexController.class);
    @GetMapping("")
    public String emptyToIndex(){
        return "forward:index";
    }

    @SaCheckLogin
    @GetMapping("index")
    public ModelAndView index(){
        logger.info("into index");
        return ResponseUtil.view("index",null);
    }
}
