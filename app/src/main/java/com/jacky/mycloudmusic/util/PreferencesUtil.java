package com.jacky.mycloudmusic.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesUtil {

    /**
     * 偏好设置文件名称
     */
    private static final String NAME = "dengxiaoxin_my_cloud_music";

    /**
     * 是否显示引导界面key
     */
    private static final String SHOW_GUIDE = "SHOW_GUIDE";

    /**
     * 实例
     */
    private static PreferencesUtil instance;

    /**
     * 上下文
     */
    private final Context context;
    private final SharedPreferences preferences;

    private PreferencesUtil(Context context) {
        this.context = context.getApplicationContext();

        //this.context = context;
        //这样写有内存泄漏
        //因为当前工具类不会马上释放
        //如果当前工具类引用了界面实例
        //当界面关闭后
        //因为界面对应在这里还有引用
        //所以会导致界面对象不会被释放

        preferences = this.context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    public static PreferencesUtil getInstance(Context context) {
        if (instance == null) {
            instance = new PreferencesUtil(context);
        }
        return instance;
    }

    /**
     * 是否显示引导界面
     * @return 是否显示
     */
    public boolean isShowGuide() {
        return preferences.getBoolean(SHOW_GUIDE, true);
    }

    /**
     * 设置是否显示引导界面
     * @param value 是否显示
     */
    public void setShowGuide(boolean value) {
        preferences.edit().putBoolean(SHOW_GUIDE, value).apply();
    }

}
