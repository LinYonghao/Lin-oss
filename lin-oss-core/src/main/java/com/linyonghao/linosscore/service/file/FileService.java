package com.linyonghao.linosscore.service.file;


import com.linyonghao.linosscore.entity.DownloadParams;
import com.linyonghao.linosscore.entity.OSSFile;
import com.linyonghao.linosscore.model.UploadPolicy;
import com.linyonghao.linosscore.model.UserModel;

/**
 * 文件服务接口
 *
 */
public interface FileService {
    String uploadFile(OSSFile file, UploadPolicy uploadPolicy, UserModel userModel) throws FileUploadException;
    byte[] downloadFile(String fileKey, DownloadParams downloadParams) throws FileDownloadException;
}
