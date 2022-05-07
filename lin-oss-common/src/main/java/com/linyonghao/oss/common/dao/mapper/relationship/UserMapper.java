package com.linyonghao.oss.common.dao.mapper.relationship;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.linyonghao.oss.common.model.UserModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Repository
public interface UserMapper extends BaseMapper<UserModel> {

}
