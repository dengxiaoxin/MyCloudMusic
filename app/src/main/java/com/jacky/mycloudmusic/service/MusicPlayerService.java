package com.jacky.mycloudmusic.service;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import com.jacky.mycloudmusic.manager.ListManager;
import com.jacky.mycloudmusic.manager.MusicPlayerManager;
import com.jacky.mycloudmusic.manager.impl.ListManagerImpl;
import com.jacky.mycloudmusic.manager.impl.MusicPlayerManagerImpl;
import com.jacky.mycloudmusic.util.LogUtil;
import com.jacky.mycloudmusic.util.NotificationUtil;
import com.jacky.mycloudmusic.util.ServiceUtil;

/**
 * 音乐播放service
 */
public class MusicPlayerService extends Service {
    private static final String TAG = "======MusicPlayerService";

    public MusicPlayerService() {

    }

    /**
     * 获取音乐播放管理器
     */
    public static MusicPlayerManager getMusicPlayerManager(Context context) {
        context = context.getApplicationContext();
        //尝试启动service
        ServiceUtil.startService(context, MusicPlayerService.class);

        return MusicPlayerManagerImpl.getInstance(context);
    }

    /**
     * 获取列表管理器
     */
    public static ListManager getListManager(Context context) {
        context = context.getApplicationContext();
        //尝试启动service
        ServiceUtil.startService(context, MusicPlayerService.class);

        return ListManagerImpl.getInstance(context);
    }

    /**
     * 创建时
     * onCreate之后再调用onStartCommand或onBind函数
     */
    @Override
    public void onCreate() {
        super.onCreate();

        LogUtil.d(TAG, "onCreate");
    }

    /**
     * 其他组件(如活动)通过调用startService()来请求启动服务时，系统调用该方法
     * 如果实现该方法，则要在工作完成时通过stopSelf()或者stopService()方法来停止服务
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.d(TAG, "onStartCommand");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //设置service为前台service
            Notification notification = NotificationUtil.getServiceForeground(getApplicationContext());

            //Id写0：这个通知就不会显示
            startForeground(0, notification);
        }


        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 当其他组件想要通过bindService()来绑定服务时，系统调用该方法
     *
     * @param intent 接收activity传递过来的数据
     */
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * 销毁时
     */
    @Override
    public void onDestroy() {
        super.onDestroy();

        LogUtil.d(TAG, "onDestroy");

        //停止前台服务
        //true：移除之前的通知
        stopForeground(true);
    }
}
