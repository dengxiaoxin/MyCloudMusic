package com.jacky.mycloudmusic.fragment;

import com.jacky.mycloudmusic.activity.BaseCommonActivity;
import com.jacky.mycloudmusic.util.PreferencesUtil;

import butterknife.ButterKnife;

public class BaseCommonFragment extends BaseFragment {
    /**
     * 偏好设置工具类
     */
    protected PreferencesUtil sp;

    @Override
    protected void initViews() {
        super.initViews();

        //初始化ButterKnife框架
        if (isBindView()) {
            bindView();
        }
    }

    @Override
    protected void initData() {
        super.initData();

        sp = PreferencesUtil.getInstance(getActivity());
    }

    /**
     * 启动界面并关闭当前界面
     */
    public void startActivityAndFinishThis(Class<?> toActivityClass) {
        getCurrentActivity().startActivityAndFinishThis(toActivityClass);
    }

    /**
     * 启动界面
     */
    public void startActivity(Class<?> toActivityClass) {
        getCurrentActivity().startActivity(toActivityClass);
    }

    /**
     * 启动界面,并告知要添加的Fragment
     *
     * @param toActivityClass 要启动的包含Fragment的界面
     * @param fragmentTag     要启动的Fragment的标记
     */
    public void startActivityContainFragment(Class<?> toActivityClass, String fragmentTag) {
        getCurrentActivity().startActivityContainFragment(toActivityClass, fragmentTag);
    }

    /**
     * 启动界面,并告知要添加的Fragment
     * @param toActivityClass 要启动的包含Fragment的界面
     * @param fragmentTag 要启动的Fragment的标记
     * @param id 歌单id、用户id等
     */
    public void startActivityContainFragment(Class<?> toActivityClass, String fragmentTag, String id) {
        getCurrentActivity().startActivityContainFragment(toActivityClass, fragmentTag, id);
    }

    /**
     * 启动包含WebView的界面
     *
     * @param toActivityClass 要启动的界面
     * @param title ToolBar标题
     * @param url   网址链接
     */
    public void startActivityContainWebView(Class<?> toActivityClass, String title, String url) {
        getCurrentActivity().startActivityContainWebView(toActivityClass, title, url);
    }

    public BaseCommonActivity getCurrentActivity() {
        return (BaseCommonActivity) getActivity();
    }

    /**
     * 是否绑定View
     */
    protected boolean isBindView() {
        return true;
    }

    /**
     * 绑定View
     */
    protected void bindView() {
        ButterKnife.bind(this, requireView());
    }
}
