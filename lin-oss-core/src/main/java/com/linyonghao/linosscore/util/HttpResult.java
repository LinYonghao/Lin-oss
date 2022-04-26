package com.linyonghao.linosscore.util;

import com.alibaba.fastjson.JSONObject;
import com.linyonghao.linosscore.dto.HttpResponseDTO;

public class HttpResult {
    public static <T> JSONObject ok(T data){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",200);
        jsonObject.put("msg","success");
        jsonObject.put("data",data);
        return jsonObject;
    }

    public static <T> JSONObject ok(T data,String message){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",200);
        jsonObject.put("msg",message);
        jsonObject.put("data",data);
        return jsonObject;
    }

    public static JSONObject ok(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",200);
        jsonObject.put("msg","success");
        jsonObject.put("data",null);
        return jsonObject;
    }

    public static JSONObject fail(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",-1);
        jsonObject.put("msg","fail");
        jsonObject.put("data",null);
        return jsonObject;
    }

    public static JSONObject fail(String msg){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",-1);
        jsonObject.put("msg",msg);
        jsonObject.put("data",null);
        return jsonObject;
    }



}