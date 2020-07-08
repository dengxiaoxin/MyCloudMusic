package com.jacky.mycloudmusic;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.jacky.mycloudmusic.util.ToastUtil;

import es.dmoral.toasty.Toasty;

public class AppContext extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Toasty.Config.getInstance().apply();

        ToastUtil.init(getApplicationContext());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
