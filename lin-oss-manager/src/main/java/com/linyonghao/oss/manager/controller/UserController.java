package com.linyonghao.oss.manager.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.linyonghao.oss.common.model.UserModel;
import com.linyonghao.oss.common.service.UserService;
import com.linyonghao.oss.manager.Constant.MQName;
import com.linyonghao.oss.manager.entity.JSONResponse;
import com.linyonghao.oss.manager.service.SMSRedisService;
import com.linyonghao.oss.manager.utils.JSONResponseUtil;
import com.linyonghao.oss.manager.utils.RegexValidateUtil;
import com.linyonghao.oss.manager.utils.ResponseUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Controller
@RequestMapping("user")
public class UserController {
    /**
     * 登录页面请求
     * @return
     */

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    SMSRedisService smsRedisService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public ModelAndView loginGET(){
        return ResponseUtil.view("login",null);
    }


    /**
     * 登录请求
     */
    @PostMapping(value = "/login")
    public ModelAndView loginPOST(String mobile,String code){
        QueryWrapper<UserModel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile",mobile);
        UserModel userModel = userService.getOne(queryWrapper);
        HashMap<String, String> sessionMap = new HashMap<>();
        sessionMap.put("mobile",mobile);
        if(userModel == null){
            return ResponseUtil.error("login",sessionMap,"用户不存在!");
        }

        // check sms code
        String realCode = smsRedisService.get(mobile);
        if(!StringUtils.hasLength(realCode) || !realCode.equals(code)){
            return ResponseUtil.error("login",sessionMap,"验证码错误,请重试");
        }
        // 移除sms
        smsRedisService.del(mobile);
        StpUtil.login(userModel.getId());
        StpUtil.getSession().set("user_info",userModel);


        return ResponseUtil.success("redirect:/index",null,"登录成功!");
    }

    /**
     *获取手机验证码
     * @param mobile
     * @return
     */
    @GetMapping("/sms/{mobile}")
    @ResponseBody
    public JSONResponse sms(@PathVariable("mobile") String mobile){
        if (!RegexValidateUtil.checkPhone(mobile)) {
            return JSONResponseUtil.error("手机号码不符合系统规范",null);
        }
//        if (!smsRedisService.isValid(mobile)) {
//            return JSONResponseUtil.error("请稍后再试.",null);
//        }

        rabbitTemplate.convertAndSend(MQName.SMS_QUEUE,mobile);
        return JSONResponseUtil.success();
    }
    /**
     * 用户注册GET
     */

    @GetMapping("register")
    public ModelAndView registerGET(){
        return ResponseUtil.view("register",null);
    }

    /**
     * 注册POST
     * @param username
     * @param mobile
     * @param code
     * @return
     */

    @PostMapping("register")
    public ModelAndView registerPOST(String username,String mobile,String code){
        HashMap<String, Object> sessionMap = new HashMap<>();
        sessionMap.put("mobile",mobile);
        sessionMap.put("username",username);
        String realCode = smsRedisService.get(mobile);
        if(!StringUtils.hasLength(realCode) || !realCode.equals(code)){
            return ResponseUtil.error("register",sessionMap,"验证码错误,请重试");
        }

        // 查重
        QueryWrapper<UserModel> queryWrapper = new QueryWrapper<UserModel>().eq("mobile", mobile);
        long count = userService.count(queryWrapper);
        if(count > 0){
            return ResponseUtil.error("register",sessionMap,"用户已注册!");
        }
        UserModel userModel = UserModel.generateOne();
        userModel.setMobile(mobile);
        userModel.setUsername(username);

        boolean isSave = userService.save(userModel);
        if(isSave){
            smsRedisService.del(mobile);
            return ResponseUtil.success("login",sessionMap,"注册成功!");
        }else{
            return ResponseUtil.error("register",sessionMap,"注册失败,请重试");
        }
    }


}
