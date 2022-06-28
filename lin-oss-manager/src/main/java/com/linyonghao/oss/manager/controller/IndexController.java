package com.linyonghao.oss.manager.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import com.linyonghao.oss.manager.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;


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
        List<String> roleList = StpUtil.getRoleList();
        HashMap<String, Object> model = new HashMap<>();
        model.put("role_list",roleList);
        return ResponseUtil.view("index",model);
    }
}
