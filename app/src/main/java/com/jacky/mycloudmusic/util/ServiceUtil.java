package com.jacky.mycloudmusic.util;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import java.util.List;

/**
 * 服务相关工具类
 */
public class ServiceUtil {

    public static void startService(Context context, Class<?> clazz) {
        if (!isServiceRunning(context, clazz)) {
            Intent intent = new Intent(context, clazz);
            //这句才是真正的启动服务语句，其他都是在封装
            context.startService(intent);
        }
    }

    @SuppressLint("ServiceCast")
    public static boolean isServiceRunning(Context context, Class<?> clazz) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        assert activityManager != null;
        List<ActivityManager.RunningServiceInfo> services = activityManager.getRunningServices(Integer.MAX_VALUE);
        if (services == null || services.size() == 0) {
            return false;
        }

        for (ActivityManager.RunningServiceInfo serviceInfo :
                services) {
            //类名相等
            if (serviceInfo.service.getClassName().equals(clazz.getName()))
                return true;
        }

        return false;
    }
}
