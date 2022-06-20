package com.linyonghao.oss.common.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static Date getThisMonthStartDay(){
        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.DAY_OF_MONTH,0);
        instance.set(Calendar.HOUR_OF_DAY,0);
        instance.set(Calendar.MINUTE,0);
        instance.set(Calendar.SECOND,0);
        return instance.getTime();
    }
}
