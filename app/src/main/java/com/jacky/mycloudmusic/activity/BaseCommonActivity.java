package com.jacky.mycloudmusic.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.jacky.mycloudmusic.util.Constant;
import com.jacky.mycloudmusic.util.PreferencesUtil;

import java.io.Serializable;

import butterknife.ButterKnife;

public class BaseCommonActivity extends BaseActivity {

    /**
     * 偏好设置实例
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
     * 设置全透状态栏
     * 默认设置内容显示到状态栏以及状态栏颜色为白色系
     */
    protected void setStatusBarFullTransparent() {
        Window window = getCurrentActivity().getWindow();
        if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
            //去除半透明状态栏(如果有)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //背景颜色透明
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);

            //SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN：让内容显示到状态栏
            //SYSTEM_UI_FLAG_LAYOUT_STABLE：状态栏文字显示白色
            //SYSTEM_UI_FLAG_LIGHT_STATUS_BAR：状态栏文字显示黑色
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

        } else if (Build.VERSION.SDK_INT >= 19) {//19表示4.4
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //虚拟键盘也透明
            //window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 半透明状态栏
     */
    protected void setHalfTransparent() {
        Window window = getCurrentActivity().getWindow();
        if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

        } else if (Build.VERSION.SDK_INT >= 19) {//19表示4.4
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //虚拟键盘也透明
            //window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 调整内容是否紧贴着StatusBar（紧贴就是内容不显示到状态栏）
     * 应该在对应的xml布局文件中，设置根布局fitsSystemWindows=true
     */
    protected void setFitSystemWindow(boolean fitSystemWindow) {
        View contentViewGroup = ((ViewGroup) getCurrentActivity().findViewById(android.R.id.content)).getChildAt(0);
        contentViewGroup.setFitsSystemWindows(fitSystemWindow);
    }

    /**
     * 设置状态栏图标颜色  true:黑色 false：白色
     */
    //SYSTEM_UI_FLAG_LIGHT_STATUS_BAR：状态栏文字显示黑色
    protected void changeStatusIconColor(boolean setDark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = getCurrentActivity().getWindow().getDecorView();
            if (decorView != null) {
                int vis = decorView.getSystemUiVisibility();
                if (setDark) {
                    vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                } else {
                    vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }
                decorView.setSystemUiVisibility(vis);
            }
        }
    }

    /**
     * 启动界面并关闭当前界面
     * @param toActivityClass 要启动的界面
     */
    public void startActivityAndFinishThis(Class<?> toActivityClass) {
        Intent intent = new Intent(getCurrentActivity(), toActivityClass);
        startActivity(intent);
        finish();
    }

    /**
     * 启动界面
     * @param toActivityClass 要启动的界面
     */
    public void startActivity(Class<?> toActivityClass) {
        Intent intent = new Intent(getCurrentActivity(), toActivityClass);
        startActivity(intent);
    }

    /**
     * 启动界面，可以传递一个字符串参数
     */
    public void startActivityExtraId(Class<?> toActivityClass, String id) {
        //创建Intent
        Intent intent = new Intent(getCurrentActivity(), toActivityClass);

        //传递数据
        if (!TextUtils.isEmpty(id)) {
            //不为空才传递
            intent.putExtra(Constant.ID, id);
        }

        //启动界面
        startActivity(intent);
    }

    /**
     * 启动界面，可以传递一个Serializable参数
     */
    public void startActivityExtraData(Class<?> toActivityClass, Serializable data) {
        //创建intent
        Intent intent = new Intent(getCurrentActivity(), toActivityClass);

        //传递数据
        intent.putExtra(Constant.DATA, data);

        //启动界面
        startActivity(intent);
    }

    /**
     * 启动界面,并告知要添加的Fragment
     *
     * @param toActivityClass 要启动的包含Fragment的界面
     * @param fragmentTag     要启动的Fragment的标记
     */
    public void startActivityContainFragment(Class<?> toActivityClass, String fragmentTag) {
        Intent intent = new Intent(getCurrentActivity(), toActivityClass);
        intent.putExtra(Constant.FRAGMENT_TAG, fragmentTag);
        startActivity(intent);
    }

    /**
     * 启动界面,并告知要添加的Fragment
     * @param toActivityClass 要启动的包含Fragment的界面
     * @param fragmentTag 要启动的Fragment的标记
     * @param id 歌单id、用户id等
     */
    public void startActivityContainFragment(Class<?> toActivityClass, String fragmentTag, String id) {
        Intent intent = new Intent(getCurrentActivity(), toActivityClass);
        intent.putExtra(Constant.FRAGMENT_TAG, fragmentTag);
        intent.putExtra(Constant.ID, id);
        startActivity(intent);
    }

    /**
     * 启动包含WebView的界面
     *
     * @param toActivityClass 要启动的界面
     * @param title ToolBar标题
     * @param url   网址链接
     */
    public void startActivityContainWebView(Class<?> toActivityClass, String title, String url) {
        Intent intent = new Intent(getCurrentActivity(), toActivityClass);
        intent.putExtra(Constant.FRAGMENT_TAG, Constant.WEB_VIEW_FRAGMENT);
        //添加标题
        intent.putExtra(Constant.TITLE, title);
        //添加url
        intent.putExtra(Constant.URL, url);

        startActivity(intent);
    }

    public BaseCommonActivity getCurrentActivity() {
        return this;
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
        ButterKnife.bind(this);
    }
}
