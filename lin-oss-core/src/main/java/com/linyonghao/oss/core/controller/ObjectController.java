package com.linyonghao.oss.core.controller;

import com.linyonghao.oss.common.model.HttpJSONResponse;
import com.linyonghao.oss.common.service.ICoreObjectService;
import com.linyonghao.oss.core.service.TokenService;
import com.linyonghao.oss.core.service.file.FileService;
import com.linyonghao.oss.core.util.HttpJsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@RestController
@RequestMapping("obj")
public class ObjectController {


    @Autowired
    private ICoreObjectService objectService;

    @Autowired
    private TokenService tokenService;
    /**
     *
     * @param key 前键
     * @param newKey 新键
     * @param Token 授权
     * @return json
     */
    @PutMapping("")
    public HttpJSONResponse updateKey(@RequestBody Map<String, String> json,String Token){
        String key = json.get("key");
        String newKey = json.get("newKey");
        String bucketId = tokenService.check(Token);
        boolean isUpdate = objectService.updateKey(bucketId, key, newKey);
        if(isUpdate){
            return HttpJsonResult.ok();
        }else{
            return HttpJsonResult.fail("删除成功");
        }


    }

}
