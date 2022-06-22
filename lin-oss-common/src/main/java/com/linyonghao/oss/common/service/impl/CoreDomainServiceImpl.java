package com.linyonghao.oss.common.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linyonghao.oss.common.dao.mapper.relationship.CoreDomainMapper;
import com.linyonghao.oss.common.entity.CoreDomain;
import com.linyonghao.oss.common.service.ICoreDomainService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  空间域名实现类
 * </p>
 *
 * @author LinYonghao
 * @since 2022-06-22
 */
@Service
public class CoreDomainServiceImpl extends ServiceImpl<CoreDomainMapper, CoreDomain> implements ICoreDomainService {
    public List<CoreDomain> getByBucketID(String bucketId){
        QueryWrapper<CoreDomain> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bucket_id",bucketId);
        return this.getBaseMapper().selectList(queryWrapper);
    }
}
