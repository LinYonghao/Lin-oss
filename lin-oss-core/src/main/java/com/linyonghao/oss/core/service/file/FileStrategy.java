package com.linyonghao.oss.core.service.file;

import com.linyonghao.oss.common.entity.OSSFile;

public interface FileStrategy {
    public String uploadFile(OSSFile file) throws FileUploadException;
    public byte[] downloadFile(String fileKey) throws FileDownloadException;

}
