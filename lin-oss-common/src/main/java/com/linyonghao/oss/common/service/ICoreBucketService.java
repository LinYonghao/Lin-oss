package com.linyonghao.oss.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linyonghao.oss.common.dto.CountAndSize;
import com.linyonghao.oss.common.entity.CoreBucket;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LinYonghao
 * @since 2022-06-17
 */
public interface ICoreBucketService extends IService<CoreBucket> {
    public boolean isBelongUser(String bucketId,String userId);
    public long getThisMonthGETCount(String bucketId);
    public long getThisMonthPOSTCount(String bucketId);

    CoreBucket getById(String id);


    CountAndSize insertOneObj(String bucketID, long size);
}
