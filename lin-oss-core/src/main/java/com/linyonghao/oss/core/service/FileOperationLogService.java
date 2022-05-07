package com.linyonghao.oss.core.service;

import com.alibaba.fastjson.JSON;
import com.linyonghao.oss.common.dao.mapper.sequential.DownloadMapper;
import com.linyonghao.oss.common.dao.mapper.sequential.UploadLogMapper;
import com.linyonghao.oss.core.dto.DownloadMessage;
import com.linyonghao.oss.core.dto.UploadMessage;
import com.linyonghao.oss.core.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class FileOperationLogService {
    @Autowired
    UploadLogMapper uploadLogMapper;

    @Autowired
    DownloadMapper downloadMapper;

    public void logUpload(UploadMessage uploadMessage){
        uploadLogMapper.insert(
                uploadMessage.getUserModel().getId(),
                TimeUtil.format(uploadMessage.getTime()),
                uploadMessage.getOssFile().getMd5(),
                uploadMessage.getOssFile().getRemoteFilename(),
                JSON.toJSONString(uploadMessage.getUploadPolicy()),
                uploadMessage.getOssFile().getSize()
        );
    }

    public void logDownload(DownloadMessage downloadMessage){
        downloadMapper.insert(
                JSON.toJSONString(downloadMessage),
                TimeUtil.format(new Date()),
                downloadMessage.getOssFile().getSize(),
                downloadMessage.getClientIp(),
                downloadMessage.getUserId());
    }
}