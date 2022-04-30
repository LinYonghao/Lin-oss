package com.linyonghao.linosscore.controller;

import com.linyonghao.linosscore.service.file.FileDownloadException;
import com.linyonghao.linosscore.service.file.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/download")
public class DownloadFileController {
    Logger logger = LoggerFactory.getLogger(DownloadFileController.class);
    @Autowired
    FileService fileService;
    @GetMapping("/**")
    public void download(HttpServletResponse response, HttpServletRequest request){
        // TODO 1.私有仓库 2.
        String servletPath = request.getServletPath();
        String key = servletPath.substring(servletPath.indexOf("/", 1) + 1);
        response.setHeader("Content-type","image/png");
        logger.info(key);

        try {
            byte[] bytes = fileService.downloadFile(key, null);
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(bytes);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FileDownloadException e) {
            e.printStackTrace();
        }

    }
}
