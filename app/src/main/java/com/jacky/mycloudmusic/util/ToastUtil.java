package com.jacky.mycloudmusic.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import es.dmoral.toasty.Toasty;

public class ToastUtil {
    
    private static Context context;

    public static void init(@NonNull Context context) {
        ToastUtil.context = context;
    }
    
    public static void errorShortToast(@StringRes int id) {
        Toasty.error(context, id, Toasty.LENGTH_SHORT).show();
    }

    public static void errorShortToast(String message) {
        Toasty.error(context, message, Toasty.LENGTH_SHORT).show();
    }

    public static void errorLongToast(@StringRes int id) {
        Toasty.error(context, id, Toasty.LENGTH_LONG).show();
    }

    public static void successShortToast(@StringRes int id) {
        Toasty.success(context, id, Toasty.LENGTH_SHORT).show();
    }

    public static void successLongToast(@StringRes int id) {
        Toasty.success(context, id, Toasty.LENGTH_LONG).show();
    }

    public static void infoShortToast(@NonNull CharSequence message) {
        Toasty.info(context, message, Toasty.LENGTH_SHORT).show();
    }
}
