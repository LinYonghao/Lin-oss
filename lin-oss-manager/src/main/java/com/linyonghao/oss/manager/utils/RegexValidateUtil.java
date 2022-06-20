package com.linyonghao.oss.manager.utils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 使用正则表达式进行数据验证
 */
public class RegexValidateUtil {

    static boolean flag = false;
    static String regex = "";

    public static boolean check(String str, String regex) {
        try {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(str);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 验证邮箱
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        String regex = "^\\w+[-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$ ";
        return check(email, regex);
    }

    /**
     * 验证手机号码,三大运营商号码均可验证(不含卫星通信1349)
     *
     * 中国电信号段 133,149,153,173,174,177,180,181,189,199
     * 中国联通号段 130,131,132,145,146,155,156,166,175,176,185,186
     * 中国移动号段 134(0-8),135,136,137,138,139,147,148,150,151,152,157,158,159,165,178,182,183,184,187,188,198
     * 上网卡专属号段（用于上网和收发短信，不能打电话）
     * 如中国联通的是145
     * 虚拟运营商
     *     电信：1700,1701,1702
     *     移动：1703,1705,1706
     *     联通：1704,1707,1708,1709,171
     * 卫星通信： 1349　　　　　
     * 未知号段：141、142、143、144、154
     *
     * @param phone
     * @return
     */
    public static boolean checkPhone(String phone) {
        String regex = "^[1](([3|5|8][\\d])|([4][4,5,6,7,8,9])|([6][2,5,6,7])|([7][^9])|([9][1,8,9]))[\\d]{8}$";
        return check(phone, regex);
    }

    /**
     * 验证固话号码
     *
     * @param telephone
     * @return
     */
    public static boolean checkTelephone(String telephone) {
        String regex = "^(0\\d{2}-\\d{8}(-\\d{1,4})?)|(0\\d{3}-\\d{7,8}(-\\d{1,4})?)$";
        return  check(telephone, regex);
    }

    /**
     * 验证传真号码
     *
     * @param fax
     * @return
     */
    public static boolean checkFax(String fax) {
        String regex = "^(0\\d{2}-\\d{8}(-\\d{1,4})?)|(0\\d{3}-\\d{7,8}(-\\d{1,4})?)$";
        return check(fax, regex);
    }

    /**
     * 验证QQ号码
     *
     * @param QQ
     * @return
     */
    public static boolean checkQQ(String QQ) {
        String regex = "^[1-9][0-9]{4,}$";
        return check(QQ, regex);
    }

    public static boolean customCheck(String pattern,String s){
        return check(s,pattern);
    }

    public static void main(String[] args) {
        System.out.println(customCheck("^[0-9a-zA-Z_]{1,}$", "linoss"));
    }
}
