package com.linyonghao.oss.common.dao.mapper.sequential;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DownloadMapper {
    public static final String INSERT_SQL = "insert into d1002 using download_log tags(#{uploadParam}) values(#{ts}," +
            "#{fileSize},#{ip},#{user_id})";
    @Insert(INSERT_SQL)
    public void insert(
            @Param("uploadParam") String uploadParam,
            @Param("ts") String datetime,
            @Param("fileSize") long fileSize,
            @Param("ip") String ip,
            @Param("user_id") long userId
    );

}
