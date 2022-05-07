package com.linyonghao.oss.core.components.receiver;

import com.alibaba.fastjson.JSON;
import com.linyonghao.oss.common.dao.mapper.sequential.DownloadMapper;
import com.linyonghao.oss.core.constant.MQName;
import com.linyonghao.oss.core.dto.DownloadMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DownloadReceiver {
    @Autowired
    DownloadMapper downloadMapper;
    @RabbitListener(queues = MQName.DOWNLOAD_LOG)
    public void downloadLog(DownloadMessage downloadMessage){
        String jsonParams = JSON.toJSONString(downloadMessage.getDownloadParams());
        downloadMapper.insert(
                jsonParams,
                downloadMessage.getDatetime(),
                downloadMessage.getOssFile().getSize(),
                downloadMessage.getClientIp(),
                downloadMessage.getUserId()
        );
    }
}
