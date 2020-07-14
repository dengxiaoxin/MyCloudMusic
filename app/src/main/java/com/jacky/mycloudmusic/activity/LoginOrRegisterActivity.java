package com.jacky.mycloudmusic.activity;

import android.os.Bundle;

import com.jacky.mycloudmusic.R;
import com.jacky.mycloudmusic.domain.event.LoginSuccessEvent;
import com.jacky.mycloudmusic.util.Constant;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

    @Override
    protected void initListeners() {
        super.initListeners();

        //注册通知
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        //解除注册
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @OnClick(R.id.btn_login)
    void onBtnLoginClick() {
        startActivityContainFragment(CommonToolbarActivity.class, Constant.LOGIN_FRAGMENT);
    }

    @OnClick(R.id.btn_register)
    void onBtnRegisterClick() {
        startActivityContainFragment(CommonToolbarActivity.class, Constant.REGISTER_FRAGMENT);
    }

    /**
     * 登录成功事件回调函数
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginSuccessEvent(LoginSuccessEvent event) {
        finish();
    }
}
