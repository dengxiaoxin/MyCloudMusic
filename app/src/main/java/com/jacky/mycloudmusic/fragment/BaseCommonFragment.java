package com.jacky.mycloudmusic.fragment;

import android.content.Intent;

import com.jacky.mycloudmusic.activity.BaseCommonActivity;
import com.jacky.mycloudmusic.util.PreferencesUtil;

import java.util.Objects;

public class BaseCommonFragment extends BaseFragment {
    /**
     * 偏好设置工具类
     */
    protected PreferencesUtil sp;

    @Override
    protected void initData() {
        super.initData();

        sp = PreferencesUtil.getInstance(getActivity());
    }

    /**
     * 启动界面并关闭当前界面
     */
    protected void startActivityAndFinishThis(Class<?> clazz) {
        startActivity(clazz);
        Objects.requireNonNull(getCurrentActivity()).finish();
    }

    /**
     * 启动界面
     */
    protected void startActivity(Class<?> clazz){
        //创建Intent
        Intent intent = new Intent(getCurrentActivity(), clazz);

        //启动界面
        startActivity(intent);
    }

    /**
     * 启动界面,并告知要添加的Fragment
     * @param clazz 要启动的界面
     */
    protected void startActivity(Class<?> clazz, String fragmentTag) {
        Intent intent = new Intent(getCurrentActivity(), clazz);
        intent.putExtra("fragmentTag", fragmentTag);
        startActivity(intent);
    }

    protected BaseCommonActivity getCurrentActivity() {
        return (BaseCommonActivity) getActivity();
    }
}
