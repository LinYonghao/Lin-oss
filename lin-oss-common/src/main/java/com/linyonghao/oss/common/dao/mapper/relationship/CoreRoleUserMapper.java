package com.linyonghao.oss.common.dao.mapper.relationship;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.linyonghao.oss.common.entity.CoreRole;
import com.linyonghao.oss.common.entity.CoreRoleUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LinYonghao
 * @since 2022-06-27
 */
public interface CoreRoleUserMapper extends BaseMapper<CoreRoleUser> {

    @Select("SELECT r.* FROM core_role_user ru JOIN core_role r ON r.id = ru.role_id" +
            " JOIN core_users u ON u.id = ru.user_id WHERE u.id = #{userId}")
    List<CoreRole> selectRolesByUserId(@Param("userId") String userId);


}
