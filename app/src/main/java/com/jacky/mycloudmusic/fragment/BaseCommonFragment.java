package com.jacky.mycloudmusic.fragment;

import android.content.Intent;

import com.jacky.mycloudmusic.activity.BaseCommonActivity;
import com.jacky.mycloudmusic.util.PreferencesUtil;

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
        getActivity().finish();
    }

    /**
     * 启动界面
     */
    protected void startActivity(Class<?> clazz){
        //创建Intent
        Intent intent = new Intent(getActivity(), clazz);

        //启动界面
        startActivity(intent);
    }
}
