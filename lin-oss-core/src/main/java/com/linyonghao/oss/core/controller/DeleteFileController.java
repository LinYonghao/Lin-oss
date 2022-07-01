package com.linyonghao.oss.core.controller;

import com.linyonghao.oss.common.dto.TemporaryUpDownCacheInfo;
import com.linyonghao.oss.common.model.HttpJSONResponse;
import com.linyonghao.oss.common.service.impl.TemporaryUpDownRedisService;
import com.linyonghao.oss.core.service.TokenService;
import com.linyonghao.oss.core.service.file.FileService;
import com.linyonghao.oss.core.util.HttpJsonResult;
import org.apache.ibatis.annotations.Delete;
import org.csource.common.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("delete")
public class DeleteFileController {

    @Autowired
    TemporaryUpDownRedisService temporaryUpDownRedisService;

    @Autowired
    FileService fileService;

    @Autowired
    TokenService tokenService;
    @DeleteMapping("/**")
    public HttpJSONResponse delete(HttpServletRequest request){
        // TODO 将Token验证重构成一个验证器
        String servletPath = request.getServletPath();
        String key = servletPath.substring(servletPath.indexOf("/", 1) + 1);
        String token = request.getParameter("Token");
        String bucketId = tokenService.check(token);
        if(bucketId == null){
            return HttpJsonResult.fail("授权失败,Token过期");
        }
        try {
            if (!fileService.delete(bucketId,key)) {
                return HttpJsonResult.fail("删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return HttpJsonResult.fail("删除失败");
        }
        return HttpJsonResult.ok();
    }

}
