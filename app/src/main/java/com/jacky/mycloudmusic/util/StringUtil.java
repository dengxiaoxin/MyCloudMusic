package com.jacky.mycloudmusic.util;

public class StringUtil {
    /**
     * 是否是手机号
     */
    public static boolean isPhoneNum(String value) {
        return value.matches(Constant.REGEX_PHONE);
    }

    /**
     * 是否是邮箱
     */
    public static boolean isEmailAddress(String value) {
        return value.matches(Constant.REGEX_EMAIL);
    }

    /**
     * 是否符合密码格式
     */
    public static boolean isPassword(String value) {
        return value.length() >= 6 && value.length() <= 15;
    }
}
