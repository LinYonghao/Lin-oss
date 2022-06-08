package com.linyonghao.oss.core.components.receiver;

import com.alibaba.fastjson.JSON;
import com.linyonghao.oss.core.constant.MQName;
import com.linyonghao.oss.core.dto.DownloadMessage;
import com.linyonghao.oss.core.mapper.DownloadLogMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DownloadReceiver {

    @Autowired
    DownloadLogMapper downloadLogMapper;

    @RabbitListener(queues = MQName.DOWNLOAD_LOG)
    public void downloadLog(DownloadMessage downloadMessage){
        downloadLogMapper.insert(downloadMessage,downloadMessage.getDatetime());
    }
}
