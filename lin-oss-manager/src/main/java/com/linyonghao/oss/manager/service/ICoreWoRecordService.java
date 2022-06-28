package com.linyonghao.oss.manager.service;

import com.linyonghao.oss.manager.entity.CoreWoRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Lin Yonghao
 * @since 2022-06-27
 */
public interface ICoreWoRecordService extends IService<CoreWoRecord> {
    List<CoreWoRecord> getRecordList(String woId);

    void insertOne(CoreWoRecord record);
}
