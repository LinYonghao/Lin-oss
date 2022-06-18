package com.linyonghao.oss.common.dao.mapper.relationship;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.linyonghao.oss.common.entity.CoreObject;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LinYonghao
 * @since 2022-06-17
 */
@Repository
public interface CoreObjectMapper extends BaseMapper<CoreObject> {

    @Select("SELECT COUNT(*) FROM core_object o JOIN core_bucket b ON o.id = o.id " +
            "JOIN core_users u ON u.id = b.user_id WHERE u.id = #{userId}")
    public long getObjectNumByUserId(@Param("userId")String userId);

}
