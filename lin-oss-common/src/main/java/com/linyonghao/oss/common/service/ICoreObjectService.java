package com.linyonghao.oss.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linyonghao.oss.common.entity.CoreObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LinYonghao
 * @since 2022-06-17
 */
public interface ICoreObjectService extends IService<CoreObject> {
    public long [] getObjectNumAndSizeSumByBucket(@Param("userId") String userId, @Param("bucketId") String bucketId);

    public List<CoreObject> getObjectByBucketId(String bucketId);


}
