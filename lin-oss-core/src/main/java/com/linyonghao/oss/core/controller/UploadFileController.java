package com.linyonghao.oss.core.controller;

import com.linyonghao.oss.common.model.HttpJSONResponse;
import com.linyonghao.oss.common.model.UploadPolicy;
import com.linyonghao.oss.common.model.UserModel;
import com.linyonghao.oss.common.entity.OSSFile;
import com.linyonghao.oss.core.service.file.FileService;
import com.linyonghao.oss.core.service.file.FileUploadException;
import com.linyonghao.oss.core.service.file.NotFoundBucketException;
import com.linyonghao.oss.core.util.FileUtil;
import com.linyonghao.oss.core.util.HttpJsonResult;
import com.linyonghao.oss.core.validator.FileUploadValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/upload")
public class UploadFileController {
    private FileService fileService;
    @Autowired
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    Logger logger =  LoggerFactory.getLogger(UploadFileController.class);


    @RequestMapping("/direct")
    public HttpJSONResponse hello(@RequestAttribute("userModel") UserModel userModel, @RequestAttribute("uploadPolicy") UploadPolicy uploadPolicy, MultipartFile file) throws IOException, NoSuchAlgorithmException {

        if (file == null){
            return HttpJsonResult.fail("file字段不能为空");
        }

        // 保存文件到系统临时目录
        File filename = new File(FileUtil.getTempDir() + file.getOriginalFilename());
        file.transferTo(filename);

        FileUploadValidator fileUploadValidator = new FileUploadValidator(filename, file, uploadPolicy);
        if(!fileUploadValidator.validate()){
            return HttpJsonResult.fail(fileUploadValidator.getErrorMessage());
        }

        String fileExt = FileUtil.getExt(filename.getAbsolutePath());
        OSSFile OSSFile = new OSSFile();
        OSSFile.setExt(fileExt);
        OSSFile.setAbsolutePath(filename.getPath());
        OSSFile.setSize(file.getSize());
        String fileRemoteKey;
        try {
            fileRemoteKey = fileService.uploadFile(OSSFile,uploadPolicy,userModel);
        } catch (FileUploadException e) {
            return HttpJsonResult.fail(e.getMessage());
        } catch (NotFoundBucketException e) {
            return HttpJsonResult.fail(e.getMessage());
        }
        return HttpJsonResult.ok(fileRemoteKey);
    }

}
