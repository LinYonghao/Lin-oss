package com.linyonghao.oss.core.service;

import com.linyonghao.oss.common.dao.mapper.sequential.APILogMapper;
import com.linyonghao.oss.common.model.APILogModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class APILogService {
    @Autowired
    APILogMapper apiLogMapper;

    public void logOne(APILogModel apiLogModel){
        apiLogMapper.insert(apiLogModel,apiLogModel.getTime());
    }

}
