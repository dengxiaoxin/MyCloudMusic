package com.jacky.mycloudmusic.util;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.jacky.mycloudmusic.domain.Ad;
import com.youth.banner.loader.ImageLoader;

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //将对象转为广告对象
        Ad banner = (Ad) path;

        //使用工具类方法显示图片
        ImageUtil.show((Activity) context, imageView, banner.getBanner());
    }
}
