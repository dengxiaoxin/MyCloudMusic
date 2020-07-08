package com.jacky.mycloudmusic.activity;

import android.os.Bundle;

import com.jacky.mycloudmusic.R;
import com.jacky.mycloudmusic.util.Constant;

import butterknife.OnClick;

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
        changeStatusIconColor(true);
    }

    @OnClick(R.id.btn_login)
    void onBtnLoginClick() {
        startActivity(Toolbar1Activity.class, Constant.LOGIN_FRAGMENT);
    }

    @OnClick(R.id.btn_register)
    void onBtnRegisterClick() {
        startActivity(Toolbar1Activity.class, Constant.REGISTER_FRAGMENT);
    }
}
