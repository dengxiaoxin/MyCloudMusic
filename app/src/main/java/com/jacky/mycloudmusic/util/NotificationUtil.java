package com.jacky.mycloudmusic.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.jacky.mycloudmusic.R;

public class NotificationUtil {
    private static final String IMPORTANCE_LOW_CHANNEL_ID = "importance_low_channel_id";
    private static NotificationManager notificationManager;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Notification getServiceForeground(Context context) {
        //获取通知管理器
        getNotificationManager(context);

        //创建渠道(8.0以上版本才有)
        //可以多次创建
        //但Id一样只会创建一个
        NotificationChannel channel = new NotificationChannel(IMPORTANCE_LOW_CHANNEL_ID, "重要通知", NotificationManager.IMPORTANCE_LOW);

        //通知管理器中添加上面的渠道
        notificationManager.createNotificationChannel(channel);

        //创建通知
        Notification notification = new Notification.Builder(context, IMPORTANCE_LOW_CHANNEL_ID)
                //通知标题
                .setContentTitle("邓小鑫的通知")
                //通知内容
                .setContentText("早睡早起，天天向上!")
                //通知小图标
                .setSmallIcon(R.mipmap.ic_launcher)
                //通知大图标
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                //创建
                .build();

        return notification;
    }

    /**
     * 获取通知管理器
     */
    private static void getNotificationManager(Context context) {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
    }

    /**
     * 显示通知
     */
    public static void showNotification(int id, Notification notification) {
        notificationManager.notify(id, notification);
    }
}
