package com.jacky.mycloudmusic.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.google.android.material.appbar.AppBarLayout;
import com.jacky.mycloudmusic.R;
import com.jacky.mycloudmusic.domain.Video;
import com.jacky.mycloudmusic.domain.response.DetailResponse;
import com.jacky.mycloudmusic.listener.HttpObserver;
import com.jacky.mycloudmusic.networkapi.RetrofitAPI;
import com.jacky.mycloudmusic.util.Constant;
import com.jacky.mycloudmusic.util.LogUtil;
import com.jacky.mycloudmusic.util.ResourceUtil;
import com.jacky.mycloudmusic.util.ScreenUtil;
import com.jacky.mycloudmusic.util.TimeUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class VideoDetailActivity extends BaseTitleActivity implements MediaPlayer.OnPreparedListener, SeekBar.OnSeekBarChangeListener {

    private static final String TAG = "======VideoDetailActivity";

    /**
     * 视频容器
     */
    @BindView(R.id.video_container)
    RelativeLayout videoContainer;

    /**
     * 播放器
     */
    @BindView(R.id.vv)
    VideoView vv;

    /**
     * 标题容器
     */
    @BindView(R.id.abl)
    AppBarLayout abl;

    /**
     * 播放控制容器
     */
    @BindView(R.id.control_container)
    View controlContainer;

    /**
     * 开始时间
     */
    @BindView(R.id.tv_start)
    TextView tvStart;

    /**
     * 进度条
     */
    @BindView(R.id.sb_progress)
    SeekBar sbProgress;

    /**
     * 结束时间
     */
    @BindView(R.id.tv_end)
    TextView tvEnd;

    /**
     * 全屏切换按钮
     */
    @BindView(R.id.ib_screen)
    ImageButton ibScreen;

    /**
     * 播放按钮
     */
    @BindView(R.id.ib_play)
    ImageButton ibPlay;

    /**
     * 播放信息
     */
    @BindView(R.id.tv_info)
    TextView tvInfo;

    /**
     * 列表控件
     */
    @BindView(R.id.rv)
    LRecyclerView rv;

    private String id;

    /**
     * 用于显示播放进度的计时器
     */
    private CountDownTimer countDownTimer;

    /**
     * 视频容器的高度
     * 单位：像素
     */
    private int videoContainerHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
    }

    @Override
    protected void initViews() {
        super.initViews();
    }

    @Override
    protected void initData() {
        super.initData();

        Intent intent = getIntent();
        id = intent.getStringExtra(Constant.ID);

        RetrofitAPI.getInstance()
                .videoDetail(id)
                .subscribe(new HttpObserver<DetailResponse<Video>>(this, true) {
                    @Override
                    public void onSucceeded(DetailResponse<Video> videoDetailResponse) {
                        next(videoDetailResponse.getData());
                    }
                });
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        vv.setOnPreparedListener(this);

        sbProgress.setOnSeekBarChangeListener(this);
    }

    private void next(Video data) {
        LogUtil.d(TAG, "next:" + data);

        //标题
        setTitle(data.getTitle());

        //设置播放路径
        vv.setVideoURI(Uri.parse(ResourceUtil.resourceUri(data.getUri())));

        //开始播放
        play();
    }

    @Override
    protected void onDestroy() {
        vv.stopPlayback();

        super.onDestroy();
    }

    @OnClick(R.id.ib_play)
    public void onBtnPlayClick() {
        if (isPlaying()) {
            pause();
            stopShowProgress();
        } else {
            play();
            startShowProgress();
        }
    }

    @OnClick(R.id.video_touch_container)
    public void onVideoTouchContainerClick() {
        if (ibPlay.getVisibility() == View.VISIBLE) {
            setPlayControllerVisible(View.INVISIBLE);
            if (isPlaying())
                stopShowProgress();
        } else {
            setPlayControllerVisible(View.VISIBLE);
            if (isPlaying())
                startShowProgress();
        }
    }

    @OnClick(R.id.ib_screen)
    public void onChangeFullScreenClick() {
        changeOrientation();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        //重新计算视频的高
        //这样没有黑边
        //目的是有更好的体验

        //获取视频宽度
        int videoWidth = mp.getVideoWidth();

        //获取视频高度
        int videoHeight = mp.getVideoHeight();

        //获取屏幕的宽度
        int screenWidth = ScreenUtil.getScreenWidth(getCurrentActivity());

        //视频宽度和屏幕宽度计算缩放比例
        double scale = screenWidth * 1.0 / videoWidth;

        //计算出视频容器的高度
        videoContainerHeight = (int) (videoHeight * scale);

        //更新播放容器高度
        updatePlayContainerLayout();

        //获取视频时长
        int duration = mp.getDuration();
        sbProgress.setMax(duration);
        tvEnd.setText(TimeUtil.ms2ms(duration));

        startShowProgress();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            tvStart.setText(TimeUtil.ms2ms(progress));
            vv.seekTo(progress);

            if (!isPlaying()) {
                play();
            }
        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        stopShowProgress();
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        startShowProgress();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        //更新播放容器布局,全屏切换
        updatePlayContainerLayout();
    }

    /**
     * 物理返回键点击回调
     */
    @Override
    public void onBackPressed() {
        if (isFullScreen()) {
            changeOrientation();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 菜单点击回调
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //直接调用实体键返回按钮方法
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isPlaying() {
        return vv.isPlaying();
    }

    private void pause() {
        vv.pause();

        showPlayStatus();
    }

    private void play() {
        vv.start();

        showPauseStatus();
    }

    private void showPlayStatus() {
        ibPlay.setImageResource(R.drawable.ic_video_play);
    }

    private void showPauseStatus() {
        ibPlay.setImageResource(R.drawable.ic_video_pause);
    }

    private void setPlayControllerVisible(int visible) {
        abl.setVisibility(visible);
        ibPlay.setVisibility(visible);
        controlContainer.setVisibility(visible);
    }

    /**
     * 开始显示进度
     */
    private void startShowProgress() {
        CancelCountDownTimer();

        countDownTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                showProgress(vv.getCurrentPosition());
            }

            @Override
            public void onFinish() {
                setPlayControllerVisible(View.INVISIBLE);
            }
        };

        countDownTimer.start();
    }

    /**
     * 停止显示进度
     */
    private void stopShowProgress() {
        CancelCountDownTimer();
    }

    private void CancelCountDownTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

    private void showProgress(int progress) {
        tvStart.setText(TimeUtil.ms2ms(progress));
        sbProgress.setProgress(progress);
    }

    /**
     * 更新播放容器高度
     */
    private void updatePlayContainerLayout() {
        //获取视频容器布局参数
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) videoContainer.getLayoutParams();

        //获取当前屏幕方向
        int orientation = getResources().getConfiguration().orientation;

        //判断屏幕方向
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            //竖屏

            //去除全屏
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

            //设置视频容器高度
            layoutParams.height = videoContainerHeight;

            //更全屏按钮图标
            ibScreen.setImageResource(R.drawable.ic_full_screen);
        } else {
            //横屏（全屏）

            //隐藏状态栏
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

            //视频容器高度和父容器一样
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;

            //更改全屏按钮图标
            ibScreen.setImageResource(R.drawable.ic_normal_screen);
        }
    }

    /**
     * 更改屏幕方向
     */
    private void changeOrientation() {
        if (isFullScreen()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        }
    }

    private boolean isFullScreen() {
        return getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
    }
}
