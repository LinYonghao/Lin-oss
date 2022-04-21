package com.linyonghao.linosscore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class UploadFile {
    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }
}
