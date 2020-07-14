package com.jacky.mycloudmusic.fragment;

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
    public void startActivityAndFinishThis(Class<?> clazz) {
        getCurrentActivity().startActivityAndFinishThis(clazz);
    }

    /**
     * 启动界面
     */
    public void startActivity(Class<?> clazz) {
        getCurrentActivity().startActivity(clazz);
    }

    /**
     * 启动界面,并告知要添加的Fragment
     * @param clazz 要启动的界面
     */
    public void startActivityContainFragment(Class<?> clazz, String fragmentTag) {
        getCurrentActivity().startActivityContainFragment(clazz, fragmentTag);
    }

    /**
     * 启动包含WebView的界面
     *
     * @param clazz 要启动的界面
     * @param title ToolBar标题
     * @param url   网址链接
     */
    public void startActivityContainWebView(Class<?> clazz, String title, String url) {
        getCurrentActivity().startActivityContainWebView(clazz, title, url);
    }

    public BaseCommonActivity getCurrentActivity() {
        return (BaseCommonActivity) getActivity();
    }
}
