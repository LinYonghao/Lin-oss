package com.linyonghao.oss.core.controller;

import com.linyonghao.oss.common.dto.TemporaryUpDownCacheInfo;
import com.linyonghao.oss.common.model.HttpJSONResponse;
import com.linyonghao.oss.common.entity.OSSFile;
import com.linyonghao.oss.common.service.impl.TemporaryUpDownRedisService;
import com.linyonghao.oss.core.service.file.FileDownloadException;
import com.linyonghao.oss.core.service.file.FileService;
import com.linyonghao.oss.core.util.HttpJsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * 下载控制器类 用于处理下载事件
 */
@RestController
@RequestMapping("/download")
public class DownloadFileController {
    Logger logger = LoggerFactory.getLogger(DownloadFileController.class);
    @Autowired
    FileService fileService;

    @Autowired
    TemporaryUpDownRedisService temporaryUpDownRedisService;

    @GetMapping("/**")
    public HttpJSONResponse download(HttpServletResponse response, HttpServletRequest request){
        //从host 获取bucket name
        String host = request.getHeader("Host");
        String[] hostSplit = host.split("-");
        Long bucketId =null;
        if(hostSplit.length !=2  || host.isEmpty()){
            // 临时权限
            String token = request.getParameter("Token");
            if(!StringUtils.hasLength(token)){
                return HttpJsonResult.fail("找不到指定的bucket");
            }
            TemporaryUpDownCacheInfo temporaryUpDownCacheInfo = temporaryUpDownRedisService.get(token);
            if (temporaryUpDownCacheInfo == null){
                return HttpJsonResult.fail("token 失效");
            }
            System.out.println("temporaryUpDownCacheInfo.getBucketId() = " + temporaryUpDownCacheInfo.getBucketId());
            bucketId = Long.parseLong(temporaryUpDownCacheInfo.getBucketId());
        }else{
            bucketId = Long.parseLong(hostSplit[0]);
        }


        // TODO 1.私有仓库 2.
        String servletPath = request.getServletPath();
        String key = servletPath.substring(servletPath.indexOf("/", 1) + 1);
        logger.info(key);
        try {
            OSSFile ossFile = fileService.downloadFile(bucketId,key, null,request.getRemoteHost());
            ServletOutputStream outputStream = response.getOutputStream();
            response.setHeader("Content-type",ossFile.getMine());
            outputStream.write(ossFile.getBin());
            outputStream.close();
            return null;
        } catch (IOException | FileDownloadException e) {
            e.printStackTrace();
        }

        return HttpJsonResult.fail("no object");

    }
}
