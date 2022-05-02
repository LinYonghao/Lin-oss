package com.linyonghao.linosscore.receiver;

import com.linyonghao.linosscore.constant.MQName;
import com.linyonghao.linosscore.dto.UploadMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class UploadReceiver {
    Logger logger = LoggerFactory.getLogger(UploadReceiver.class);
    @RabbitListener(queues = MQName.UPLOAD_CALLBACK)
    public void uploadCallbackHandler(UploadMessage uploadMessage){
        logger.info(uploadMessage.toString());
        System.out.println(uploadMessage);
    }

    @RabbitListener(queues = MQName.UPLOAD_LOG)
    public void uploadLogHandler(UploadMessage uploadMessage){
        logger.info(uploadMessage.toString());
        long size = uploadMessage.getOssFile().getSize();
        // 上传流量
        // key upload_{user_id} 5s
        // hash key:
        // value
    }
}
