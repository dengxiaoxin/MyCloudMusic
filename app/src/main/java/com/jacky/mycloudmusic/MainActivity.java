package com.jacky.mycloudmusic;

import android.content.Intent;
import android.os.Bundle;

import com.jacky.mycloudmusic.activity.BaseCommonActivity;
import com.jacky.mycloudmusic.activity.CommonToolbarActivity;
import com.jacky.mycloudmusic.util.Constant;

public class MainActivity extends BaseCommonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //处理ACTION
        processIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        //处理ACTION
        processIntent(getIntent());
    }

    private void processIntent(Intent intent) {
        String action = intent.getAction();
        String url = intent.getStringExtra(Constant.URL);
        //广告点击
        //显示广告界面
        if (Constant.ACTION_AD.equals(action)) {
            startActivityContainWebView(CommonToolbarActivity.class, Constant.TITLE_ACTIVITY_DETAIL, url);
        }
    }
}
