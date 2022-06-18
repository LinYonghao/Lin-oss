package com.linyonghao.oss.common.dao.mapper.relationship;

import com.linyonghao.oss.common.entity.CorePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LinYonghao
 * @since 2022-06-16
 */

public interface CorePermissionMapper extends BaseMapper<CorePermission> {

    @Select("SELECT p.* FROM core_user_permission up JOIN core_permission p ON up.permission_id =" +
            " p.id WHERE up.user_id = #{user_id}")
    public List<CorePermission> selectByUserId(@Param("user_id") String userId);

}
