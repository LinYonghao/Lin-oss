package com.linyonghao.linosscore.service.file;

import com.linyonghao.linosscore.entity.OSSFile;
import com.linyonghao.linosscore.model.UploadPolicy;

public interface FileStrategy {
    public String uploadFile(OSSFile file) throws FileUploadException;
    public byte[] downloadFile(String fileKey) throws FileDownloadException;

}
