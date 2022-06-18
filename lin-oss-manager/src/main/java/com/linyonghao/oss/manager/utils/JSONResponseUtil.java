package com.linyonghao.oss.manager.utils;

import com.linyonghao.oss.manager.entity.JSONResponse;
import reactor.util.annotation.Nullable;

public class JSONResponseUtil {
    public static JSONResponse success(@Nullable Object data){
        JSONResponse jsonResponse = new JSONResponse();
        jsonResponse.setCode(200);
        jsonResponse.setMsg("成功");
        jsonResponse.setData(data);
        return jsonResponse;
    }

    public static JSONResponse success(String msg, @Nullable Object data ){
        JSONResponse jsonResponse = new JSONResponse();
        jsonResponse.setCode(200);
        jsonResponse.setMsg("成功");
        jsonResponse.setData(data);
        return jsonResponse;
    }

    public static JSONResponse success(){
        JSONResponse jsonResponse = new JSONResponse();
        jsonResponse.setCode(200);
        jsonResponse.setMsg("成功");
        return jsonResponse;
    }

    public static JSONResponse error(){
        JSONResponse jsonResponse = new JSONResponse();
        jsonResponse.setCode(-1);
        jsonResponse.setMsg("成功");
        return jsonResponse;
    }
    public static JSONResponse error(@Nullable Object data){
        JSONResponse jsonResponse = new JSONResponse();
        jsonResponse.setCode(-1);
        jsonResponse.setMsg("成功");
        jsonResponse.setData(data);
        return jsonResponse;
    }

    public static JSONResponse error(String msg, @Nullable Object data ){
        JSONResponse jsonResponse = new JSONResponse();
        jsonResponse.setCode(-1);
        jsonResponse.setMsg("失败");
        jsonResponse.setData(data);
        return jsonResponse;
    }


}
