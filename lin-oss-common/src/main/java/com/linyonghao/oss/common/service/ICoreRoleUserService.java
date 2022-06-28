package com.linyonghao.oss.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linyonghao.oss.common.entity.CoreRole;
import com.linyonghao.oss.common.entity.CoreRoleUser;

import java.util.List;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LinYonghao
 * @since 2022-06-27
 */
public interface ICoreRoleUserService extends IService<CoreRoleUser> {
    List<CoreRole> getRoleByUserId(String userId);
}
