package com.linyonghao.oss.common.utils;

public class EnvUtil {

    public static String getEnv(String envName,String default_){
        String value = System.getenv(envName);
        return value == null ? default_ :value;
    }

    public static String getEnv(String envName) {
        return System.getenv(envName);
    }


}
