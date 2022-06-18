package com.linyonghao.oss.manager.util;

import com.linyonghao.oss.manager.LinOssManagerApplication;
import com.linyonghao.oss.manager.utils.SMSUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@ComponentScan(basePackages = "com.linyonghao.oss")
public class SmsUtil {

    @Autowired
    SMSUtil smsUtil;

    @Test
    public void testSend(){
        boolean send = smsUtil.sendSMS("13232703423", "1234");
        System.out.println(send);
    }



}
