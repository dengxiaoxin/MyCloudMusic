package com.jacky.mycloudmusic.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import com.jacky.mycloudmusic.MainActivity;
import com.jacky.mycloudmusic.R;

public class SplashActivity extends BaseCommonActivity {

    /**
     * 默认倒计时时间,单位秒
     */
    private static final int DEFAULT_COUNT_TIME = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void initViews() {
        super.initViews();

        //设置全屏
        fullScreen();

        //开启倒计时；
        startTimerTask(DEFAULT_COUNT_TIME);

        //测试偏好设置
//        SharedPreferences preferences = getSharedPreferences("dengxiaoxin", MODE_PRIVATE);
//
//        preferences.edit().putString("username", "我是邓小鑫").apply();
//        String username = preferences.getString("username", null);
//        Log.d("=============", "initViews: " + "第一次获取的值：" + username);
//        preferences.edit().remove("username").apply();
//        username = preferences.getString("username", null);
//        Log.d("=============", "initViews: " + "删除后再次获取的值：" + username);
    }

    private void startTimerTask(int time) {
        TimerTask timerTask = new TimerTask(SplashActivity.this);
        timerTask.execute(time);
    }

    @SuppressLint("StaticFieldLeak")
    private class TimerTask extends AsyncTask<Integer, Integer, Boolean> {
        Context context;

        TimerTask(Context context) {
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(Integer... integers) {
            int i = integers[0];

            while (i > 0) {
                //Log.i("================", i + "");
                publishProgress(i);
                i--;

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            //Log.i("================", "startMainActivity");
            if (sp.isShowGuide())
                //跳转到引导界面
                startActivityAndFinishThis(GuideActivity.class);
            else if (sp.isAlreadyLogin())
                //跳转到首页
                startActivityAndFinishThis(MainActivity.class);
            else
                //跳转到登录注册界面
                startActivityAndFinishThis(LoginOrRegisterActivity.class);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

        }
    }
}
