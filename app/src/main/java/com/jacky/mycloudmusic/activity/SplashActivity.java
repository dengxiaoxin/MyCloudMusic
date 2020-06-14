package com.jacky.mycloudmusic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jacky.mycloudmusic.R;

public class SplashActivity extends AppCompatActivity {

    /**
     * 默认倒计时时间,单位秒
     */
    private static final int DEFAULT_COUNT_TIME = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //设置界面全屏
        fullScreen();

        //开启倒计时；
        startTimerTask(DEFAULT_COUNT_TIME);
    }

    private void fullScreen() {
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

    public void startTimerTask(int time) {
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

            while (i>0){
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
            startActivityAndFinishThis(GuideActivity.class);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

        }
    }

    /**
     * 启动界面并关闭当前界面
     * @param clazz 要启动的界面
     */
    protected void startActivityAndFinishThis(Class<?> clazz) {
        Intent intent = new Intent(getApplicationContext(), clazz);
        startActivity(intent);
        finish();
    }

    /**
     * 启动界面
     * @param clazz 要启动的界面
     */
    protected void startActivity(Class<?> clazz) {
        Intent intent = new Intent(getApplicationContext(), clazz);
        startActivity(intent);
    }
}
