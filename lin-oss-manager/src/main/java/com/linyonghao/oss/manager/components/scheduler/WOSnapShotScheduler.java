package com.linyonghao.oss.manager.components.scheduler;

import com.linyonghao.oss.common.config.SystemConfig;
import com.linyonghao.oss.manager.entity.CoreWo;
import com.linyonghao.oss.manager.entity.CoreWoRecord;
import com.linyonghao.oss.manager.service.ICoreWoRecordService;
import com.linyonghao.oss.manager.service.ICoreWoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WOSnapShotScheduler {

    @Autowired
    ICoreWoService woService;

    @Autowired
    ICoreWoRecordService woRecordService;


    Logger logger = LoggerFactory.getLogger(WOSnapShotScheduler.class);
    @Async
    @Scheduled(fixedDelay = 5000)
    public void backup()  {
        CoreWo wo = woService.popOneNew();
        while (wo != null){
            woService.save(wo);
            logger.info("快照了一条工单记录 id:" + wo.getId());
            wo = woService.popOneNew();
        }

        CoreWoRecord record = woRecordService.popOneNew();
        while (record != null){
            logger.info("快照了一条工单详情 id:" + record.getId());
            woRecordService.save(record);
            record = woRecordService.popOneNew();
        }

    }

}
