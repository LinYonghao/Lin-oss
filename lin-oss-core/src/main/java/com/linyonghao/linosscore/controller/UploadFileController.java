package com.linyonghao.linosscore.controller;

import com.linyonghao.linosscore.model.UploadPolicy;
import com.linyonghao.linosscore.model.UserModel;
import com.linyonghao.linosscore.util.HttpResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
public class UploadFileController {
    Logger logger =  LoggerFactory.getLogger(UploadFileController.class);

    @RequestMapping("/direct")
    public String hello(@RequestAttribute("userModel") UserModel userModel, @RequestAttribute("uploadPolicy") UploadPolicy uploadPolicy, MultipartFile file) {
        System.out.println(userModel);
        System.out.println(uploadPolicy);
        logger.info("direct");
        return userModel.toString();
    }
}
