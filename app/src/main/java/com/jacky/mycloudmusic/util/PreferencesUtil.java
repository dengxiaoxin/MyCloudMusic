package com.jacky.mycloudmusic.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class PreferencesUtil {

    /**
     * 偏好设置文件名称
     */
    private static final String NAME = "dengxiaoxin_my_cloud_music";

    /**
     * 是否显示引导界面key
     */
    private static final String SHOW_GUIDE = "SHOW_GUIDE";

    private static final String USER_ID = "USER_ID";

    private static final String SESSION = "SESSION";

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
        return getBoolean(SHOW_GUIDE, true);
    }

    /**
     * 设置是否显示引导界面
     * @param value 是否显示
     */
    public void setShowGuide(boolean value) {
        putBoolean(SHOW_GUIDE, value);
    }

    /**
     * get和set userId、session
     *
     * @return
     */
    public String getUserId() {
        return getString(USER_ID, null);
    }

    public void setUserId(String value) {
        putString(USER_ID, value);
    }

    public String getSession() {
        return getString(SESSION, null);
    }

    public void setSession(String value) {
        putString(SESSION, value);
    }

    /**
     * 是否已登录
     */
    public boolean isAlreadyLogin() {
        return !TextUtils.isEmpty(getUserId());
    }

    /**
     * 退出
     */
    public void logout() {
        delete(USER_ID);
        delete(SESSION);
    }

    //辅助方法

    /**
     * 保存字符串
     */
    private void putString(String key, String value) {
        preferences.edit().putString(key, value).apply();
    }

    /**
     * 获取字符串
     */
    private String getString(String key, String defaultValue) {
        return preferences.getString(key, defaultValue);
    }

    /**
     * 保存boolean
     */
    private void putBoolean(String key, boolean value) {
        preferences.edit().putBoolean(key, value).apply();
    }

    /**
     * 获取boolean
     */
    private boolean getBoolean(String key, boolean defaultValue) {
        return preferences.getBoolean(key, defaultValue);
    }

    /**
     * 删除内容
     */
    private void delete(String key) {
        preferences.edit().remove(key).apply();
    }

}
