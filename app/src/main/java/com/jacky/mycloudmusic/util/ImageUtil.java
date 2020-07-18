package com.jacky.mycloudmusic.util;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jacky.mycloudmusic.R;

/**
 * 图片相关工具类
 */
public class ImageUtil {
    /**
     * 显示头像
     *
     * @param uri 绝对路径、相对路径都可传
     */
    public static void showAvatar(Activity activity, ImageView view, String uri) {
        //获取头像通用配置
        RequestOptions options = getAvatarCommonRequestOptions();

        if (TextUtils.isEmpty(uri)) {
            //没有头像
            //显示默认头像
            Glide.with(activity)
                    .load(R.drawable.default_avatar)
                    .apply(options)
                    .into(view);
        } else {
            //有头像
            //处理uri
            //是相对路径则转换为绝对路径
            if (!uri.startsWith("http"))
                uri = String.format(Constant.RESOURCE_ENDPOINT, uri);

            //显示图片
            Glide.with(activity)
                    .load(uri)
                    .apply(options)
                    .into(view);
        }
    }

    /**
     * 获取头像通用配置
     */
    private static RequestOptions getAvatarCommonRequestOptions() {
        //获取通用配置
        RequestOptions options = getCircleCommonRequestOptions();

        //相关图片更改为圆形图片
        options.placeholder(R.drawable.default_avatar_circle);
        options.error(R.drawable.error_img_circle);

        return options;
    }

    //=============显示圆形图片begin=============//

    /**
     * 显示圆形图片
     *
     * @param uri 绝对路径、相对路径都可传
     */
    public static void showCircle(Activity activity, ImageView view, String uri) {
        //处理uri
        //是相对路径则转换为绝对路径
        if (!uri.startsWith("http"))
            uri = String.format(Constant.RESOURCE_ENDPOINT, uri);

        //获取圆形通用的配置
        RequestOptions options = getCircleCommonRequestOptions();

        //显示图片
        Glide.with(activity)
                .load(uri)
                .apply(options)
                .into(view);
    }

    /**
     * 显示圆形资源目录图片
     */
    public static void showCircle(Activity activity, ImageView view, @RawRes @DrawableRes @Nullable int resourceId) {
        //获取公共圆形配置
        RequestOptions options = getCircleCommonRequestOptions();

        //显示图片
        Glide.with(activity)
                .load(resourceId)
                .apply(options)
                .into(view);
    }

    /**
     * 获取圆形通用配置
     */
    private static RequestOptions getCircleCommonRequestOptions() {
        //获取通用配置
        RequestOptions options = getCommonRequestOptions();

        //圆形裁剪
        options.circleCrop();

        return options;
    }
    //=============显示圆形图片end=============//

    //=============显示方形图片begin=============//

    /**
     * 显示图片
     *
     * @param uri 绝对路径、相对路径都可传
     */
    public static void show(Activity activity, ImageView view, String uri) {
        //处理uri
        //是相对路径则转换为绝对路径
        if (!uri.startsWith("http"))
            uri = String.format(Constant.RESOURCE_ENDPOINT, uri);

        //获取功能配置
        RequestOptions options = getCommonRequestOptions();

        //显示图片
        Glide.with(activity)
                .load(uri)
                .apply(options)
                .into(view);
    }

    /**
     * 显示资源目录图片
     */
    public static void show(Activity activity, ImageView view, @RawRes @DrawableRes @Nullable int resourceId) {
        //获取公共配置
        RequestOptions options = getCommonRequestOptions();

        Glide.with(activity)
                .load(resourceId)
                .apply(options)
                .into(view);
    }

    /**
     * 获取公共配置
     */
    public static RequestOptions getCommonRequestOptions() {
        //创建配置选项
        RequestOptions options = new RequestOptions();

        //占位图
        options.placeholder(R.drawable.placeholder);

        //出错后显示的图片
        //包括：图片不存在等情况
        options.error(R.drawable.error_img);

        //从中心裁剪
        options.centerCrop();

        return options;
    }
    //=============显示方形图片end=============//
}
