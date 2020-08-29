package com.jacky.mycloudmusic.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.jacky.mycloudmusic.R;
import com.jacky.mycloudmusic.util.LogUtil;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class SplashActivity extends BaseCommonActivity {

    private static final String TAG = "======SplashActivity";

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
                //跳转到广告页，之后再由广告页跳转到首页
                startActivityAndFinishThis(AdActivity.class);
            else
                //跳转到登录注册界面
                startActivityAndFinishThis(LoginOrRegisterActivity.class);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

        }
    }

    @Override
    protected void initData() {
        super.initData();

        //检查权限
        checkAppPermission();
    }

    /**
     * 检查权限
     */
    private void checkAppPermission() {
        SplashActivityPermissionsDispatcher.onPermissionGrantedWithPermissionCheck(this);
    }

    /**
     * 权限授权了回调方法
     */
    @NeedsPermission({
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    })
    void onPermissionGranted() {
        LogUtil.d(TAG, "onPermissionGranted");

        prepareNext();
    }

    private void prepareNext() {
        //开启倒计时
        startTimerTask(DEFAULT_COUNT_TIME);
    }

    /**
     * 显示权限授权对话框
     * 目的是让用户选择授权与否
     */
    @OnShowRationale({
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    })
    void showRequestPermission(PermissionRequest request) {
        LogUtil.d(TAG, "showRequestPermission");

        new AlertDialog.Builder(this)
                .setMessage(R.string.permission_hint)
                .setPositiveButton(R.string.allow, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton(R.string.deny, (dialog, which) -> request.cancel())
                .show();
    }

    /**
     * 拒绝了权限调用
     */
    @OnPermissionDenied({
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    })
    void showDenied() {
        LogUtil.d(TAG, "showDenied");

        //退出应用
        finish();
    }

    /**
     * 用户点击不再询问某权限时
     */
    @OnNeverAskAgain({
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    })
    void showNeverAskAgain() {
        LogUtil.d(TAG, "showNeverAskAgain");

        //继续请求权限
        //checkAppPermission();
        new AlertDialog.Builder(this)
                .setMessage("由于相关权限您没有授权，导致无法使用我们的app！您可以重新安装并授权，即可使用我们的app！")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
    }

    /**
     * 授权后回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        LogUtil.d(TAG, "onRequestPermissionsResult");

        //将授权结果传递到框架
        SplashActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
}
