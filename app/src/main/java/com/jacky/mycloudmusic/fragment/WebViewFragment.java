package com.jacky.mycloudmusic.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.jacky.mycloudmusic.R;

/**
 * 包含WebView的Fragment
 */
public class WebViewFragment extends BaseCommonFragment {

    private String url;

    private WebView webView;

    public WebViewFragment(String url) {
        this.url = url;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_web_view, container, false);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initViews() {
        super.initViews();

        webView = findViewById(R.id.web_view);

        //配置WebView基本属性

        //获取webview设置
        WebSettings webSettings = webView.getSettings();

        //允许访问文件
        webSettings.setAllowFileAccess(true);

        //支持多窗口
        webSettings.setSupportMultipleWindows(true);

        //开启js支持
        webSettings.setJavaScriptEnabled(true);

        //显示图片
        webSettings.setBlockNetworkImage(false);

        //允许显示手机中的网页
        webSettings.setAllowUniversalAccessFromFileURLs(true);

        //允许文件访问
        webSettings.setAllowFileAccessFromFileURLs(true);

        //允许dom存储
        webSettings.setDomStorageEnabled(true);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            //允许混合类型
            //也就是说支持http和https混合的网页
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

    @Override
    protected void initData() {
        super.initData();

        //加载网址
        webView.loadUrl(url);
    }
}
