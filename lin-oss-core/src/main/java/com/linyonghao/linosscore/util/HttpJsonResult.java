package com.linyonghao.linosscore.util;

import com.linyonghao.linosscore.model.HttpJSONResponse;

public class HttpJsonResult {

    public static HttpJSONResponse ok(Object data){
        HttpJSONResponse httpJSONResponse = new HttpJSONResponse();
        httpJSONResponse.setData(data);
        httpJSONResponse.setCode(200);
        httpJSONResponse.setMsg("success");
        return httpJSONResponse;
    }

    public static HttpJSONResponse ok(String msg){
        HttpJSONResponse httpJSONResponse = new HttpJSONResponse();
        httpJSONResponse.setData(msg);
        httpJSONResponse.setCode(200);
        httpJSONResponse.setMsg("success");
        return httpJSONResponse;
    }


    public static HttpJSONResponse ok(){
        HttpJSONResponse httpJSONResponse = new HttpJSONResponse();
        httpJSONResponse.setData(null);
        httpJSONResponse.setCode(200);
        httpJSONResponse.setMsg("success");
        return httpJSONResponse;
    }

    public static HttpJSONResponse fail(String msg){
        HttpJSONResponse httpJSONResponse = new HttpJSONResponse();
        httpJSONResponse.setData(null);
        httpJSONResponse.setCode(-1);
        httpJSONResponse.setMsg(msg);
        return httpJSONResponse;
    }


}
