package com.linyonghao.oss.common.utils;

import org.omg.CORBA.PUBLIC_MEMBER;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.math.BigDecimal.ROUND_CEILING;
import static java.math.BigDecimal.ROUND_HALF_DOWN;

public class StringUtil {
    private static String captureName(String name) {
        char[] cs = name.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }

    public static String formatByteSize(long length) {
        String result = null;

        int sub_string = 0;

        if (length >= 1073741824) {

            sub_string = String.valueOf((float) length / 1073741824).indexOf(

                    ".");

            result = ((float) length / 1073741824 + "000").substring(0,

                    sub_string + 3) + "GB";

        } else if (length >= 1048576) {

            sub_string = String.valueOf((float) length / 1048576).indexOf(".");

            result = ((float) length / 1048576 + "000").substring(0,

                    sub_string + 3) + "MB";

        } else if (length >= 1024) {

            sub_string = String.valueOf((float) length / 1024).indexOf(".");

            result = ((float) length / 1024 + "000").substring(0,

                    sub_string + 3) + "KB";

        } else if (length < 1024)

            result = Long.toString(length) + "B";

        return result;
    }

    public static String generateRedisKey(String... args){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            stringBuilder.append(args[i] );
            if(i != args.length -1){
                stringBuilder.append(":");
            }
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        System.out.println(StringUtil.formatByteSize(2005));
        System.out.println(StringUtil.formatByteSize(1024 * 1024 * 1024+ 1024));
    }
}
