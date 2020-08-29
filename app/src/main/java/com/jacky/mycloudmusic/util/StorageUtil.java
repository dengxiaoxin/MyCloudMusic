package com.jacky.mycloudmusic.util;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class StorageUtil {

    /**
     * 保存图片到系统相册
     */
    public static boolean savePicture(Context context, File file) {
        //创建媒体路径
        Uri uri = insertPictureMediaStore(context, file);

        if (uri == null) {
            //获取路径失败
            return false;
        }

        //将原来的图片保存到目标uri
        //也就是保存到系统图片媒体库
        return saveFile(context, file, uri);
    }

    /**
     * 将file保存到uri对应的路径
     */
    private static boolean saveFile(Context context, File file, Uri uri) {
        FileOutputStream fileOutputStream = null;

        try {
            //获取内容提供者
            ContentResolver contentResolver = context.getContentResolver();

            //获取uri对应的文件描述器
            ParcelFileDescriptor parcelFileDescriptor = contentResolver.openFileDescriptor(uri, "w");

            //创建文件输出流
            fileOutputStream = new FileOutputStream(parcelFileDescriptor.getFileDescriptor());

            //将file拷贝到输出流
            FileUtils.copyFile(file, fileOutputStream);

            //操作成功
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //操作失败
        return false;
    }

    /**
     * 创建图片媒体路径
     * 固定写法，Android 10写法，也兼容低版本
     */
    private static Uri insertPictureMediaStore(Context context, File file) {
        //创建内容集合
        ContentValues contentValues = new ContentValues();

        //媒体显示的名称
        //这里设置为文件的名称
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, file.getName());

        //媒体类型
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            //媒体的拍摄时间
            contentValues.put(MediaStore.Images.Media.DATE_TAKEN, file.lastModified());
        }

        return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
    }
}
