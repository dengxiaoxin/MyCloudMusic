package com.jacky.mycloudmusic;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import androidx.emoji.bundled.BundledEmojiCompatConfig;
import androidx.emoji.text.EmojiCompat;
import androidx.multidex.MultiDex;

import com.jacky.mycloudmusic.activity.LoginOrRegisterActivity;
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

        //初始化第三方toast工具类
        Toasty.Config.getInstance().apply();

        //初始化toast工具类
        ToastUtil.init(getApplicationContext());

        //初始化emoji
        BundledEmojiCompatConfig config = new BundledEmojiCompatConfig(this);
        EmojiCompat.init(config);
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

    /**
     * 退出
     */
    public void logout() {
        //清除登录相关信息
        PreferencesUtil.getInstance(getApplicationContext()).logout();

        //QQ退出
        //otherLogout(QQ.NAME);

        //微博退出
        //otherLogout(SinaWeibo.NAME);

        //清除所有界面
        //后续来补充

        //退出后跳转到登录注册界面
        Intent intent = new Intent(getApplicationContext(), LoginOrRegisterActivity.class);

        //在Activity以外启动界面（现在是在Application子类中）
        //都要写这个标识
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        //启动界面
        startActivity(intent);

        //退出之后的一些操作
        onLogout();

    }

    /**
     * 第三方平台退出
     * 因为暂未实现，所以先实现个假的
     */
    private void otherLogout(String name) {
        //清除第三方平台登录信息
//        Platform platform = ShareSDK.getPlatform(name);
//        if (platform.isAuthValid()) {
//            platform.removeAccount(true);
//        }
    }

    /**
     * 退出了通知
     * 主要用来做一些清理工作
     * 例如：关闭推送，断开聊天服务器，销毁数据库对象
     */
    private void onLogout() {

    }
}
