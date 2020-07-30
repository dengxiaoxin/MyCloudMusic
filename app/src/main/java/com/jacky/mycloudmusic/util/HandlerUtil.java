package com.jacky.mycloudmusic.util;

import android.os.Looper;

/**
 * Handler工具类
 */
public class HandlerUtil {

    /**
     * 是否是主线程
     *
     * @return
     */
    public static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

}
