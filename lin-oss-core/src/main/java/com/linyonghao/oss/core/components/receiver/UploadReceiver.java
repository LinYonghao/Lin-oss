package com.linyonghao.oss.core.components.receiver;

import com.linyonghao.oss.core.constant.MQName;
import com.linyonghao.oss.core.dto.UploadMessage;
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

    @RabbitListener(queues = MQName.UPLOAD_CALLBACK)
    public void uploadCallbackHandler(UploadMessage uploadMessage){
        logger.info(uploadMessage.toString());
        System.out.println(uploadMessage);
    }

    @RabbitListener(queues = MQName.UPLOAD_LOG)
    public void uploadLogHandler(UploadMessage uploadMessage){
        fileOperationLogService.logUpload(uploadMessage);
    }
}
