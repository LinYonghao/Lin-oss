package com.linyonghao.oss.manager.utils;

import com.alibaba.fastjson2.JSON;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson2.JSONObject;
import com.zhenzi.sms.ZhenziSmsClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "zhenzi")
public class SMSUtil {
    private String apiUrl;          //apiUrl
    private String appId;           //应用id
    private String appSecret;       //应用secret
    private String templateId;      //模板id
    private String invalidTimer;    //失效时间

    public boolean sendSMS(String telNumber, String validateCode) {


        //榛子云短信 客户端
        //请求地址，个人开发者使用https://sms_developer.zhenzikj.com，企业开发者使用https://sms.zhenzikj.com
        ZhenziSmsClient client = new ZhenziSmsClient(apiUrl, appId, appSecret);
        //存放请求参数的map集合
        Map<String, Object> params = new HashMap<String, Object>();
        //接收者手机号码
        params.put("number", telNumber);
        //短信模板ID
        params.put("templateId", templateId);
        //短信模板参数
        String[] templateParams = new String[2];
        templateParams[0] = validateCode;
        templateParams[1] = invalidTimer;
        params.put("templateParams", templateParams);
        /**
         * 1.send方法用于单条发送短信,所有请求参数需要封装到Map中;
         * 2.返回结果为json串：{ "code":0,"data":"发送成功"}
         * 3.备注：（code: 发送状态，0为成功。非0为发送失败，可从data中查看错误信息）
         */
        try {
            String result = client.send(params);
            JSONObject object = JSON.parseObject(result);
            return ((Integer) object.get("code")) == 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getInvalidTimer() {
        return invalidTimer;
    }

    public void setInvalidTimer(String invalidTimer) {
        this.invalidTimer = invalidTimer;
    }


}
