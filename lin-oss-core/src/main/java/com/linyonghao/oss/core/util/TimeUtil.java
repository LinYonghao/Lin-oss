package com.linyonghao.oss.core.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static String format(Date date){
        return simpleDateFormat.format(date);
    }

}
