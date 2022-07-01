package com.linyonghao.oss.core.service;

import com.linyonghao.oss.common.dto.TemporaryUpDownCacheInfo;
import com.linyonghao.oss.common.service.impl.TemporaryUpDownRedisService;
import com.linyonghao.oss.core.util.HttpJsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    @Autowired
    TemporaryUpDownRedisService temporaryUpDownRedisService;

    /**
     * 检查token授权
     * @return bucketId
     */
    public String check(String token){
        TemporaryUpDownCacheInfo temporaryUpDownCacheInfo = temporaryUpDownRedisService.get(token);
        if (temporaryUpDownCacheInfo == null){
            return null;
        }
        return temporaryUpDownCacheInfo.getBucketId();
    }



}
