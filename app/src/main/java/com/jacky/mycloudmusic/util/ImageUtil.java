package com.jacky.mycloudmusic.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
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
        //获取头像配置
        RequestOptions options = getCommonRequestOptions()
                .circleCrop()
                //相关图片更改为圆形图片
                .placeholder(R.drawable.default_avatar_circle)
                .error(R.drawable.error_img_circle);

        if (TextUtils.isEmpty(uri)) {
            //没有头像
            //显示默认头像
            baseShow(activity, view, R.drawable.default_avatar, options);
        } else {
            baseShow(activity, view, uri, options);
        }
    }

    /**
     * 显示小的（10dp）圆角图片
     * 处理网络传递过来的图片
     *
     * @param uri 相对路径
     */
    public static void showSmallCorners(Activity activity, ImageView view, String uri) {
        //获取通用功能配置
        RequestOptions commonOptions = getCommonRequestOptions();

        //创建两个变换，一个是从中心裁剪，另一个是圆角
        MultiTransformation<Bitmap> multiTransformation = new MultiTransformation<>(new CenterCrop(),
                new RoundedCorners(DensityUtil.dip2px(activity, 10)));

        //获取变换功能配置
        RequestOptions transformOptions = RequestOptions.bitmapTransform(multiTransformation);

        //显示图片
        Glide.with(activity)
                .load(String.format(Constant.RESOURCE_ENDPOINT, uri))
                //应用变换
                .apply(commonOptions)
                .apply(transformOptions)
                .into(view);
    }

    //=============显示圆形图片begin=============//
    /**
     * 显示圆形图片
     *
     * @param uri 绝对路径、相对路径都可传
     */
    public static void showCircle(Activity activity, ImageView view, String uri) {
        //获取圆形配置
        RequestOptions options = getCommonRequestOptions().circleCrop();

        baseShow(activity, view, uri, options);
    }

    /**
     * 显示圆形资源目录图片
     */
    public static void showCircle(Activity activity, ImageView view, @RawRes @DrawableRes @Nullable int resourceId) {
        //获取圆形配置
        RequestOptions options = getCommonRequestOptions().circleCrop();

        //显示图片
        baseShow(activity, view, resourceId, options);
    }
    //=============显示圆形图片end=============//

    //=============显示方形图片begin=============//
    /**
     * 显示图片
     *
     * @param uri 绝对路径、相对路径都可传
     */
    public static void show(Activity activity, ImageView view, String uri) {
        //获取功能配置
        RequestOptions options = getCommonRequestOptions();

        baseShow(activity, view, uri, options);
    }

    /**
     * 显示资源目录图片
     */
    public static void show(Activity activity, ImageView view, @RawRes @DrawableRes @Nullable int resourceId) {
        //获取公共配置
        RequestOptions options = getCommonRequestOptions();

        baseShow(activity, view, resourceId, options);
    }
    //=============显示方形图片end=============//

    //辅助方法

    /**
     * 显示资源目录图片
     */
    private static void baseShow(Activity activity, ImageView view, @RawRes @DrawableRes @Nullable int resourceId, RequestOptions options) {
        Glide.with(activity)
                .load(resourceId)
                .apply(options)
                .into(view);
    }

    /**
     * 显示图片
     *
     * @param uri 绝对路径、相对路径都可传
     */
    public static void baseShow(Activity activity, ImageView view, String uri, RequestOptions options) {

        if (TextUtils.isEmpty(uri)) {
            //没有值则显示默认图片
            baseShow(activity, view, R.drawable.placeholder, options);
        } else {
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
}
