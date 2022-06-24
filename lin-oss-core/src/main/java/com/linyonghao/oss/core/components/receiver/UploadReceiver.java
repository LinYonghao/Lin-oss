package com.linyonghao.oss.core.components.receiver;

import com.linyonghao.oss.common.dao.mapper.sequential.APILogMapper;
import com.linyonghao.oss.common.dao.mapper.sequential.ObjNumLogMapper;
import com.linyonghao.oss.common.dao.mapper.sequential.StorageLogMapper;
import com.linyonghao.oss.common.dto.CountAndSize;
import com.linyonghao.oss.common.model.APILogModel;
import com.linyonghao.oss.common.model.ObjectNumLog;
import com.linyonghao.oss.common.model.StorageLog;
import com.linyonghao.oss.common.service.ICoreBucketService;
import com.linyonghao.oss.core.constant.MQName;
import com.linyonghao.oss.core.dto.UploadMessage;
import com.linyonghao.oss.core.service.APILogService;
import com.linyonghao.oss.core.service.FileOperationLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UploadReceiver {
    Logger logger = LoggerFactory.getLogger(UploadReceiver.class);
    @Autowired
    FileOperationLogService fileOperationLogService;

    @Autowired
    APILogService apiLogService;

    @Autowired
    ICoreBucketService coreBucketService;

    @Autowired
    StorageLogMapper storageLogMapper;

    @Autowired
    ObjNumLogMapper objNumLogMapper;

    @RabbitListener(queues = MQName.UPLOAD_CALLBACK)
    public void uploadCallbackHandler(UploadMessage uploadMessage) {
        logger.info(uploadMessage.toString());
    }

    @RabbitListener(queues = MQName.UPLOAD_LOG)
    public void uploadLogHandler(UploadMessage uploadMessage) {
        // 文件上传记录
        fileOperationLogService.logUpload(uploadMessage);
        // API记录
        APILogModel apiLogModel = new APILogModel();
        apiLogModel.setUserId(uploadMessage.getUserModel().getId());
        apiLogModel.setIp("");
        apiLogModel.setType(APILogModel.UPLOAD);
        apiLogModel.setTime(uploadMessage.getTime().getTime());
        apiLogModel.setBucketId(uploadMessage.getBucketId());
        logger.info(String.valueOf(apiLogModel.getBucketId()));
        apiLogService.logOne(apiLogModel);
        // 空间大小设置
        CountAndSize countAndSize = coreBucketService.insertOneObj(String.valueOf(uploadMessage.getBucketId()),
                uploadMessage.getOssFile().getSize());
        // 空间存储量记录
        StorageLog storageLog = new StorageLog();
        storageLog.setSize(countAndSize.getSize());
        storageLog.setUserId(uploadMessage.getUserModel().getId());
        storageLog.setBucketId(uploadMessage.getBucketId());
        storageLogMapper.insert(storageLog,uploadMessage.getTime().getTime());
        // 对象数和存储量记录

        ObjectNumLog objectNumLog = new ObjectNumLog();
        objectNumLog.setBucketId(uploadMessage.getBucketId());
        objectNumLog.setUserId(uploadMessage.getUserModel().getId());
        objectNumLog.setNum(countAndSize.getCont());
        objNumLogMapper.insert(objectNumLog,uploadMessage.getTime().getTime());
    }
}
