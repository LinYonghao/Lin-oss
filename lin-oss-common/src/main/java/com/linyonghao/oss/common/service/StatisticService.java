package com.linyonghao.oss.common.service;

import com.linyonghao.influxdb2.entity.CountWithTime;

import java.util.List;

public interface StatisticService {
    /**
     * 获取空间数量 通过id
     * @param id
     * @return
     */
    long getSpaceNumById(String id);

    /**
     * 获取当天API调用总数数量
     * @param id
     * @return
     */
    long getAPITodayNumById(String id);

    /**
     * 获取当天API调用时间
     * @param id
     * @param every
     * @return
     */
    List<CountWithTime> getTodayAPINumById(String id,String every);

    /**
     * 获取API调用数量 通过空间名
     * @param id
     * @return
     */
    long getAPINumBySpace(String id);

    /**
     * 通过id获取对象数量
     *
     * @return
     */
    Long getObjectNumById(String id);


}
