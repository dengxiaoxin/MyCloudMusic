package com.jacky.mycloudmusic;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.jacky.mycloudmusic.domain.Session;
import com.jacky.mycloudmusic.domain.event.LoginSuccessEvent;
import com.jacky.mycloudmusic.util.PreferencesUtil;
import com.jacky.mycloudmusic.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import es.dmoral.toasty.Toasty;

public class AppContext extends Application {

    /**
     * 上下文
     */
    private static AppContext context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;

        Toasty.Config.getInstance().apply();

        ToastUtil.init(getApplicationContext());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * 获取当前上下文
     */
    public static AppContext getInstance() {
        return context;
    }

    /**
     * 当用户登录成功后
     */
    public void loginSuccess(PreferencesUtil sp, Session data) {
        sp.setUserId(data.getUser());
        sp.setSession(data.getSession());

        //发送登录成功通知
        //目的是一些界面需要接收该事件
        EventBus.getDefault().post(new LoginSuccessEvent());

        //初始化其他登录后才需要初始化的内容
        onLogin();
    }

    //初始化其他登录后才需要初始化的内容
    private void onLogin() {

    }
}
