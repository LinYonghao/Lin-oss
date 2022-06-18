package com.linyonghao.oss.manager.components.receive;

import com.linyonghao.oss.manager.Constant.MQName;
import com.linyonghao.oss.manager.service.SMSRedisService;
import com.linyonghao.oss.manager.utils.RandomUtil;
import com.linyonghao.oss.manager.utils.SMSUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SMSReceiver {

    @Autowired
    SMSUtil smsUtil;

    @Autowired
    SMSRedisService smsRedisService;


    @RabbitListener(queues = MQName.SMS_QUEUE)
    public void smsQueueProcess(String mobile){
        String code = RandomUtil.generateValidateCode(4);
        smsUtil.sendSMS(mobile, code);
        smsRedisService.set(mobile,code);
    }
}
