package com.jacky.mycloudmusic.util;

import android.annotation.SuppressLint;

/**
 * 日期时间工具类
 */
public class TimeUtil {

    /**
     * 将秒格式化为分:秒，例如：15:11
     */
    @SuppressLint("DefaultLocale")
    public static String s2ms(int totalSecond) {
        if (totalSecond == 0) {
            return "00:00";
        }

        int minute = totalSecond / 60;
        int second = totalSecond % 60;

        return String.format("%02d:%02d", minute, second);
    }

    /**
     * 将毫秒格式化为分:秒，例如：15:11
     */
    @SuppressLint("DefaultLocale")
    public static String ms2ms(int millionSecond) {
        if (millionSecond == 0) {
            return "00:00";
        }

        int totalSecond = millionSecond / 1000;
        return s2ms(totalSecond);
    }
}
