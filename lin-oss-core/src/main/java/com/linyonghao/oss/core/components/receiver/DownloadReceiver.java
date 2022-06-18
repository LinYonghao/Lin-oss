package com.linyonghao.oss.core.components.receiver;

import com.linyonghao.oss.common.model.APILogModel;
import com.linyonghao.oss.core.constant.MQName;
import com.linyonghao.oss.common.dto.DownloadMessage;
import com.linyonghao.oss.core.service.APILogService;
import com.linyonghao.oss.core.service.FileOperationLogService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DownloadReceiver {

    @Autowired
    FileOperationLogService fileOperationLogService;

    @Autowired
    APILogService apiLogService;

    @RabbitListener(queues = MQName.DOWNLOAD_LOG)
    public void downloadLog(DownloadMessage downloadMessage){
        fileOperationLogService.logDownload(downloadMessage);
        APILogModel apiLogModel = new APILogModel();
        apiLogModel.setIp(downloadMessage.getClientIp());
        apiLogModel.setType(APILogModel.DOWNLOAD);
        apiLogModel.setUserId(downloadMessage.getUserId());
        apiLogModel.setTime(downloadMessage.getDatetime());
        apiLogService.logOne(apiLogModel);
    }
}
