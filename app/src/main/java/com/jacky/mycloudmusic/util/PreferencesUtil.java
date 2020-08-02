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
     * 音乐循环模式key
     */
    private static final String LOOP_MODE = "loop_mode";

    /**
     * 最后播放的音乐信息记录
     */
    private static final String LAST_SONG_ID = "last_song_id";
    private static final String LAST_SONG_PROGRESS = "last_song_progress";

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

    /**
     * 音乐循环模式设置与获取
     * 获取时没有值就默认返回列表模式
     */
    public int getLoopMode() {
        return getInt(LOOP_MODE, Constant.MODEL_LOOP_LIST);
    }

    public void setLoopMode(int value) {
        putInt(LOOP_MODE, value);
    }

    /**
     * 最后播放的音乐信息设置与获取
     */
    public String getLastSongId() {
        return getString(LAST_SONG_ID, null);
    }

    public void setLastSongId(String value) {
        putString(LAST_SONG_ID, value);
    }

    public long getLastSongProgress() {
        return getLong(LAST_SONG_PROGRESS, 0);
    }

    public void setLastSongProgress(long value) {
        putLong(LAST_SONG_PROGRESS, value);
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
     * 保存int
     */
    private void putInt(String key, int value) {
        preferences.edit().putInt(key, value).apply();
    }

    /**
     * 获取int
     */
    private int getInt(String key, int defaultValue) {
        return preferences.getInt(key, defaultValue);
    }

    /**
     * 保存long
     */
    private void putLong(String key, long value) {
        preferences.edit().putLong(key, value).apply();
    }

    /**
     * 获取long
     */
    private long getLong(String key, long defaultValue) {
        return preferences.getLong(key, defaultValue);
    }

    /**
     * 删除内容
     */
    private void delete(String key) {
        preferences.edit().remove(key).apply();
    }

}
