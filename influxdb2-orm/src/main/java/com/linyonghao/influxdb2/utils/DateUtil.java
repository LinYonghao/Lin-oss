package com.linyonghao.influxdb2.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {
    /**
     * <p>Description: 本地时间转化为UTC时间</p>
     *
     * @return
     * @author wgs
     * @date 2018年10月19日 下午2:23:43
     */
    public static Date localToUTC(Date localDate) {
        long localTimeInMillis = localDate.getTime();
        /** long时间转换成Calendar */
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(localTimeInMillis);
        /** 取得时间偏移量 */
        int zoneOffset = calendar.get(java.util.Calendar.ZONE_OFFSET);
        /** 取得夏令时差 */
        int dstOffset = calendar.get(java.util.Calendar.DST_OFFSET);
        /** 从本地时间里扣除这些差量，即可以取得UTC时间*/
        calendar.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        /** 取得的时间就是UTC标准时间 */
        return new Date(calendar.getTimeInMillis());
    }

    /**
     * <p>Description:UTC时间转化为本地时间 </p>
     *
     * @param utcTime
     * @return
     * @author wgs
     * @date 2018年10月19日 下午2:23:24
     */
    public static Date utcToLocal(String utcTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date utcDate = null;
        try {
            utcDate = sdf.parse(utcTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sdf.setTimeZone(TimeZone.getDefault());
        Date locatlDate = null;
        String localTime = sdf.format(utcDate.getTime());
        try {
            locatlDate = sdf.parse(localTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return locatlDate;
    }

    public static void main(String[] args) {
        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.HOUR,0);
        instance.set(Calendar.MINUTE,0);
        instance.set(Calendar.SECOND,0);
        System.out.println(DateUtil.localToUTC(instance.getTime()));
        System.out.println(DateUtil.localToUTC(new Date()));
    }
}

