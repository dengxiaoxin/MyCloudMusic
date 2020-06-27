package com.jacky.mycloudmusic.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 所有Activity父类
 */
public class BaseActivity extends AppCompatActivity {
    /**
     * 引用控件
     */
    protected void initViews() {

    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * 设置监听器
     */
    protected void initListeners() {

    }

    /**
     * 在onCreate方法后面调用
     */
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        initViews();
        initData();
        initListeners();
    }
}
