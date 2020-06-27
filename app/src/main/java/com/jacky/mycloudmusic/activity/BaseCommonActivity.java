package com.jacky.mycloudmusic.activity;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;

import com.jacky.mycloudmusic.util.PreferencesUtil;

public class BaseCommonActivity extends BaseActivity {

    /**
     * 偏好设置实例
     */
    protected PreferencesUtil sp;

    @Override
    protected void initData() {
        super.initData();

        //初始化偏好设置实例
        sp = PreferencesUtil.getInstance(getCurrentActivity());
    }

    /**
     * 设置全屏，隐藏状态栏以及虚拟按钮
     */
    protected void fullScreen() {
        View decorView = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT < 19) {
            decorView.setSystemUiVisibility(View.GONE);
        } else {
            int options = View.SYSTEM_UI_FLAG_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(options);
        }
    }

    /**
     * 隐藏状态栏
     */
    protected void hideStatusBar() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 启动界面并关闭当前界面
     * @param clazz 要启动的界面
     */
    protected void startActivityAndFinishThis(Class<?> clazz) {
        Intent intent = new Intent(getCurrentActivity(), clazz);
        startActivity(intent);
        finish();
    }

    /**
     * 启动界面
     * @param clazz 要启动的界面
     */
    protected void startActivity(Class<?> clazz) {
        Intent intent = new Intent(getCurrentActivity(), clazz);
        startActivity(intent);
    }

    protected BaseCommonActivity getCurrentActivity() {
        return this;
    }
}
