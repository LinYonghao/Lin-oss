package com.linyonghao.oss.core.service.file;

import com.linyonghao.oss.common.entity.OSSFile;
import org.csource.common.MyException;

import java.io.IOException;

/**
 * 文件操作策略接口
 */
public interface FileStrategy {
    public String uploadFile(OSSFile file) throws FileUploadException;
    public byte[] downloadFile(String fileKey) throws FileDownloadException;

    public boolean deleteFile(String fileKey) throws MyException, IOException;

}
