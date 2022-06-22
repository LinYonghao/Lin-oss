package com.linyonghao.oss.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linyonghao.oss.common.entity.CoreDomain;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LinYonghao
 * @since 2022-06-22
 */
public interface ICoreDomainService extends IService<CoreDomain> {
    public List<CoreDomain> getByBucketID(String bucketId);
}
