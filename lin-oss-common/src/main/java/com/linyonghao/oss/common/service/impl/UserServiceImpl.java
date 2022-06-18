package com.linyonghao.oss.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linyonghao.oss.common.dao.mapper.relationship.UserMapper;
import com.linyonghao.oss.common.model.UserModel;
import com.linyonghao.oss.common.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserModel> implements UserService {


}
