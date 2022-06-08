package com.linyonghao.oss.common.utils;

public class StringUtil {
    private static String captureName(String name) {
        char[] cs = name.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }
}
