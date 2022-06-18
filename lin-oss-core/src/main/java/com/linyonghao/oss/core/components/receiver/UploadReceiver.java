package com.linyonghao.oss.core.components.receiver;

import com.linyonghao.oss.common.dao.mapper.sequential.APILogMapper;
import com.linyonghao.oss.common.model.APILogModel;
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

    @RabbitListener(queues = MQName.UPLOAD_CALLBACK)
    public void uploadCallbackHandler(UploadMessage uploadMessage){
        logger.info(uploadMessage.toString());
        System.out.println(uploadMessage);
    }

    @RabbitListener(queues = MQName.UPLOAD_LOG)
    public void uploadLogHandler(UploadMessage uploadMessage){
        fileOperationLogService.logUpload(uploadMessage);
        APILogModel apiLogModel = new APILogModel();
        apiLogModel.setUserId(uploadMessage.getUserModel().getId());
        apiLogModel.setIp("");
        apiLogModel.setType(APILogModel.UPLOAD);
        apiLogModel.setTime(uploadMessage.getTime().getTime());
        apiLogService.logOne(apiLogModel);
    }
}
