package com.linyonghao.oss.core.service;

import com.influxdb.client.InfluxDBClient;
import com.linyonghao.oss.common.dao.mapper.sequential.DownloadLogMapper;
import com.linyonghao.oss.common.dao.mapper.sequential.UploadLogMapper;
import com.linyonghao.oss.common.model.UploadLogModel;
import com.linyonghao.oss.common.dto.DownloadMessage;
import com.linyonghao.oss.core.dto.UploadMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 文件操作日志服务
 */
@Service
public class FileOperationLogService {
    @Autowired
    InfluxDBClient influxDBClient;
    @Autowired
    UploadLogMapper uploadLogMapper;

    @Autowired
    DownloadLogMapper downloadLogMapper;

    public void logUpload(UploadMessage uploadMessage) {
        uploadLogMapper.insert(new UploadLogModel(
            uploadMessage.getUserModel().getId(),
                uploadMessage.getUploadPolicy().getKey(),
                uploadMessage.getOssFile().getSize(),
                uploadMessage.getUploadPolicy(),
                uploadMessage.getTime().getTime()
        ),uploadMessage.getTime().getTime());

    }

    public void logDownload(DownloadMessage downloadMessage) {
        downloadLogMapper.insert(downloadMessage,downloadMessage.getDatetime());
    }
}