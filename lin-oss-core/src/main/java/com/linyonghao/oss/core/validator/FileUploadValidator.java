package com.linyonghao.oss.core.validator;

import com.linyonghao.oss.common.model.UploadPolicy;
import com.linyonghao.oss.core.util.FileUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

public class FileUploadValidator implements Validate {

    private File file;
    private MultipartFile multipartFile;
    private UploadPolicy uploadPolicy;
    private String errMsg;

    public FileUploadValidator(File file, MultipartFile multipartFile, UploadPolicy uploadPolicy) {
        this.file = file;
        this.multipartFile = multipartFile;
        this.uploadPolicy = uploadPolicy;
    }

    @Override
    public boolean validate() {
        // 是否超时
        long deadline = uploadPolicy.getDeadline();
        if(deadline < System.currentTimeMillis() / 1000){
            errMsg = "上传凭证超时";
            return false;
        }

        // 比对mine
        if(uploadPolicy.getMINE() != null){
            String mimeType = null;
            try {
                mimeType = FileUtil.getMimeType( file.getPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            String pattern = uploadPolicy.getMINE().replaceAll(",", "|");
            pattern = pattern.replaceAll("\\*", ".*?");
            boolean isMatch = Pattern.matches(pattern, mimeType);
            if(!isMatch){
                errMsg = "Mine 类型不匹配";
                return false;
            }
        }


        // 比对后缀
        String fileExt = FileUtil.getExt(file.getPath());
        if(uploadPolicy.getExt() != null){
            boolean matches = Pattern.matches(uploadPolicy.getExt(),fileExt );
            if(!matches){
                errMsg = "文件后缀不允许";
                return false;
            }
        }

        // 文件尺寸
        long maxSize = uploadPolicy.getMaxSize();
        if(maxSize > 0 && multipartFile.getSize() >maxSize){
            errMsg = "文件过大";
            return false;
        }
        return true;
    }

    @Override
    public String getErrorMessage() {
        return errMsg;
    }
}
