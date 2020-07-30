package com.jacky.mycloudmusic.util;

import android.annotation.SuppressLint;

/**
 * 日期时间工具类
 */
public class TimeUtil {

    /**
     * 将毫秒转化成分：秒
     */
    @SuppressLint("DefaultLocale")
    public static String formatMinuteSecond(int millionSecond) {
        if (millionSecond == 0) {
            return "00:00";
        }

        int s = millionSecond / 1000;
        int minute = s / 60;
        int second = s % 60;
        return String.format("%02d:%02d", minute, second);
    }
}
