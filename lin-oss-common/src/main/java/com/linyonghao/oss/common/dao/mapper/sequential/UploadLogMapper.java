package com.linyonghao.oss.common.dao.mapper.sequential;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UploadLogMapper {


    public static final String INSERT_SQL = "insert into d1001 using upload_log tags(#{userId}) values(#{ts},#{md5}," +
            "#{filename},#{uploadPolicy},#{fileSize});";
    @Insert(INSERT_SQL)
    public void insert(@Param("userId") long userId,
                       @Param("ts")  String time,
                       @Param("md5") String md5,
                       @Param("filename") String filename,
                       @Param("uploadPolicy") String uploadPolicy,
                       @Param("fileSize") long fileSize);
}
