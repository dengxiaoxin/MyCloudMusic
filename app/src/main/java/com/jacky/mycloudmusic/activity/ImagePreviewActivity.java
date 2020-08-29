package com.jacky.mycloudmusic.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.github.chrisbanes.photoview.PhotoView;
import com.jacky.mycloudmusic.R;
import com.jacky.mycloudmusic.util.Constant;
import com.jacky.mycloudmusic.util.ImageUtil;
import com.jacky.mycloudmusic.util.LogUtil;
import com.jacky.mycloudmusic.util.ResourceUtil;
import com.jacky.mycloudmusic.util.StorageUtil;
import com.jacky.mycloudmusic.util.ToastUtil;

import java.io.File;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.OnClick;

public class ImagePreviewActivity extends BaseCommonActivity {

    private static final String TAG = "======ImagePreviewActivity";
    @BindView(R.id.pv)
    PhotoView pv;

    private String id;
    private String uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);
    }

    @Override
    protected void initViews() {
        super.initViews();

        setStatusBarFullTransparent();
    }

    @Override
    protected void initData() {
        super.initData();

        Intent intent = getIntent();
        id = intent.getStringExtra(Constant.ID);
        uri = intent.getStringExtra(Constant.URL);

        ImageUtil.show(this, pv, uri);
    }

    public static void start(Activity activity, String id, String uri) {
        Intent intent = new Intent(activity, ImagePreviewActivity.class);
        intent.putExtra(Constant.ID, id);
        intent.putExtra(Constant.URL, uri);
        activity.startActivity(intent);
    }

    @OnClick(R.id.btn_save_image)
    void onBtnSaveImageClick() {
        new Thread() {
            @Override
            public void run() {
                super.run();

                FutureTarget<File> target = Glide.with(getApplicationContext())
                        .asFile()
                        .load(ResourceUtil.resourceUri(uri))
                        .submit();

                File file = null;
                try {
                    file = target.get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //弹出提示
                            ToastUtil.errorShortToast("保存失败,请稍后再试!");
                        }
                    });
                    return;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //弹出提示
                            ToastUtil.errorShortToast("保存失败,请稍后再试!");
                        }
                    });
                    return;
                }

                LogUtil.d(TAG, "download image:" + file.getAbsolutePath());

                //将下载的文件保存到相册
                boolean result = StorageUtil.savePicture(getCurrentActivity(), file);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //弹出提示
                        if (result) {
                            ToastUtil.successShortToast("图片已成功保存到相册!");
                        } else {
                            ToastUtil.errorShortToast("保存失败,请稍后再试!");
                        }

                    }
                });
            }
        }.start();
    }
}
