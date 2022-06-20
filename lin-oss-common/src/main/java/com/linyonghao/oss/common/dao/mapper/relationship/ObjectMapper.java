package com.linyonghao.oss.common.dao.mapper.relationship;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.linyonghao.oss.common.model.ObjectBucketDO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
public interface ObjectMapper extends BaseMapper<ObjectMapper> {

    @Results({@Result(property = "objectModel.id",column = "o_id"),
            @Result(property = "objectModel.createTime",column = "o_create_time"),
            @Result(property = "objectModel.bucketId",column = "o_bucket_id"),
            @Result(property = "objectModel.remoteKey",column = "o_key"),
            @Result(property = "objectModel.mine",column = "o_mine"),
            @Result(property = "objectModel.localKey",column = "o_local_key"),
            @Result(property = "bucketModel.id",column = "b_id"),
            @Result(property = "bucketModel.userId",column = "b_user_id"),
            @Result(property = "bucketModel.name",column = "b_name"),
            @Result(property = "bucketModel.ac",column = "b_ac"),
            @Result(property = "bucketModel.createTime",column = "b_create_time")})
    @Select("SELECT o.id AS o_id, o.create_time AS o_create_time,o.bucket_id AS o_bucket_id,o.remote_key AS o_key,o.mine " +
            " AS o_mine,o.local_key AS o_local_key, " +
            "b.id AS b_id,b.user_id AS b_user_id,b.name AS b_name,b.ac AS b_ac,b.create_time AS b_create_time " +
            " FROM core_object o " +
            "JOIN core_bucket b ON b.id = o.bucket_id " +
            "WHERE b.id = #{bucketId} AND o.remote_key = #{key}"
            )
    public ObjectBucketDO selectObjectByPath(@Param("bucketId") long bucketId, @Param("key") String key);

}
