package com.jacky.mycloudmusic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.jacky.mycloudmusic.MainActivity;
import com.jacky.mycloudmusic.R;
import com.jacky.mycloudmusic.util.Constant;
import com.jacky.mycloudmusic.util.LogUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class AdActivity extends BaseCommonActivity {

    //数据
    private static final String TAG = "======AdActivity";
    //private static final int MSG_TICK = 0x01;
    int[] adImgIds = new int[]{R.drawable.splash_ad_1, R.drawable.splash_ad_2, R.drawable.splash_ad_3};

    /**
     * 倒计时时间,单位秒
     */
    private static final int COUNT_DOWN_TIME = 3;
    //private int time;

    //控件
    @BindView(R.id.iv_ad_banner)
    ImageView ivAdBanner;

    @BindView(R.id.btn_skip)
    Button btnSkip;

    @BindView(R.id.cl_bottom)
    ConstraintLayout clBottom;

    private CountDownTimer countDownTimer;

    //功能
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);
    }

    @Override
    protected void initViews() {
        super.initViews();

        //设置全屏
        fullScreen();

        //设置显示的广告海报
        int index = ((int) (Math.random() * 10)) % 3;
        ivAdBanner.setImageResource(adImgIds[index]);
        if (index == 0)
            clBottom.setVisibility(View.GONE);
        else
            clBottom.setVisibility(View.VISIBLE);

        //设置跳过控件初始秒数
        btnSkip.setText(getString(R.string.skip_second, COUNT_DOWN_TIME));
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        //创建并启动倒计时
        countDownTimer = new CountDownTimer(COUNT_DOWN_TIME * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                btnSkip.setText(getString(R.string.skip_second, millisUntilFinished / 1000 + 1));
            }

            @Override
            public void onFinish() {
                startActivityAndFinishThis(MainActivity.class);
            }
        };

        countDownTimer.start();
//        time = COUNT_DOWN_TIME;

//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                while (time > 0) {
//                    Message msg = handler.obtainMessage(MSG_TICK, time);
//                    handler.sendMessage(msg);
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    time--;
//                }
//            }
//        }, 1000);

//        new Thread(){
//            @Override
//            public void run() {
//                super.run();
//
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                while (time > 0) {
//                    Message msg = handler.obtainMessage(MSG_TICK, time);
//                    handler.sendMessage(msg);
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    time--;
//                }
//            }
//        }.start();
    }

//    @SuppressLint("HandlerLeak")
//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            super.handleMessage(msg);
//
//            switch (msg.what) {
//                case MSG_TICK:
//                    btnSkip.setText(getString(R.string.skip_second, (int)msg.obj));
//                    if ((int)msg.obj == 1)
//                        startActivityAndFinishThis(MainActivity.class);
//                    break;
//                default:
//                    break;
//            }
//        }
//    };

    @Override
    protected void onDestroy() {
        cancelCountDown();

        super.onDestroy();
    }

    @OnClick(R.id.iv_ad_banner)
    void onAdBannerClick() {
        LogUtil.d(TAG, "onAdBannerClick");

        cancelCountDown();

        Intent intent = new Intent(getCurrentActivity(), MainActivity.class);
        intent.putExtra(Constant.URL, "https://www.baidu.com");
        intent.setAction(Constant.ACTION_AD);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.btn_skip)
    void onBtnSkipClick() {
        LogUtil.d(TAG, "onBtnSkipClick");

        cancelCountDown();
        startActivityAndFinishThis(MainActivity.class);
    }

    /**
     * 取消倒计时
     */
    private void cancelCountDown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }
}
