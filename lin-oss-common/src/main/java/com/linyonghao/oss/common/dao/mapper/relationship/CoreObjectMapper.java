package com.linyonghao.oss.common.dao.mapper.relationship;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.linyonghao.oss.common.entity.CoreObject;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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

    @Select("SELECT COUNT(*) FROM core_object o JOIN core_bucket b ON o.bucket_id = b.id " +
            "JOIN core_users u ON u.id = b.user_id WHERE u.id = #{userId}")
    public long getObjectNumByUserId(@Param("userId")String userId);

    @Select("SELECT o.* FROM core_object o JOIN core_bucket b ON o.id = o.id" +
            " JOIN core_users u ON u.id = b.user_id WHERE u.id = ${userId} and b.id = #{bucketId}")

    public List<CoreObject> getObjectByUserBucket(@Param("userId") String userId,@Param("bucketId") long bucketId);

    /**
     * 通过bucket获取文件数量和文件总大小
     * count(*)	sum(o.size)
     * 30	340872
     * @param userId
     * @param bucketId
     * @return
     */
    @Select("SELECT COUNT(*) as count,SUM(o.size) as size FROM core_object o JOIN core_bucket b ON o.bucket_id = b.id  " +
            "JOIN core_users u ON u.id = b.user_id WHERE u.id = #{userId} AND b.id = #{bucketId}")
    public Map<String,Object> selectObjectNumAndSizeSumByBucket(@Param("userId") String userId, @Param("bucketId") String bucketId);
}
