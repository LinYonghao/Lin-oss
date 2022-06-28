package com.linyonghao.oss.manager.service;

import com.linyonghao.oss.manager.entity.CoreWo;
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
public interface ICoreWoService extends IService<CoreWo> {
    List<CoreWo> getAllWoByUserId(String userId);
    CoreWo getOne(String woId);
    void createWo(CoreWo wo);

    List<CoreWo> getDistribute();

    void removeOneDistribute(String woId);

    void insertToPending(String userId,String woId);

    List<CoreWo> getPending(String userId);

    CoreWo popOneNew();

}
