package com.jacky.mycloudmusic.activity;

import android.os.Bundle;

import com.jacky.mycloudmusic.R;

public class LoginOrRegisterActivity extends BaseCommonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_register);
    }

    @Override
    protected void initViews() {
        super.initViews();

        //设置透明状态栏
        setStatusBarFullTransparent();
        //状态栏颜色调整为黑色系
        changStatusIconColor(true);
    }
}
