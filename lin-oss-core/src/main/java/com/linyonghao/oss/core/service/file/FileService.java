package com.linyonghao.oss.core.service.file;


import com.linyonghao.oss.common.model.UploadPolicy;
import com.linyonghao.oss.common.model.UserModel;
import com.linyonghao.oss.common.entity.DownloadParams;
import com.linyonghao.oss.common.entity.OSSFile;
import org.csource.common.MyException;

import java.io.IOException;

/**
 * 文件服务接口
 *
 */
public interface FileService {
    String uploadFile(OSSFile file, UploadPolicy uploadPolicy, UserModel userModel) throws FileUploadException, NotFoundBucketException;
    OSSFile downloadFile(long bucketId, String key, DownloadParams downloadParams, String clientIp) throws FileDownloadException, NotfoundFileException;

    boolean delete(String bucketId,String key) throws MyException, IOException;

}
