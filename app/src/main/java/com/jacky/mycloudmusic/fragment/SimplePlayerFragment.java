package com.jacky.mycloudmusic.fragment;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.jacky.mycloudmusic.R;
import com.jacky.mycloudmusic.domain.Song;
import com.jacky.mycloudmusic.listener.MusicPlayerListener;
import com.jacky.mycloudmusic.manager.MusicPlayerManager;
import com.jacky.mycloudmusic.service.MusicPlayerService;
import com.jacky.mycloudmusic.util.LogUtil;
import com.jacky.mycloudmusic.util.TimeUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 简单播放器界面
 */
public class SimplePlayerFragment extends BaseCommonFragment implements SeekBar.OnSeekBarChangeListener, MusicPlayerListener {

    private static final String TAG = "======SimplePlayerFragment";
    /**
     * 列表
     */
    @BindView(R.id.rv)
    RecyclerView rv;

    /**
     * 音乐标题
     */
    @BindView(R.id.tv_title)
    TextView tvTitle;

    /**
     * 音乐播放进度
     */
    @BindView(R.id.tv_start)
    TextView tvStart;

    /**
     * 进度条
     */
    @BindView(R.id.sb_progress)
    SeekBar sbProgress;

    /**
     * 音乐总时长
     */
    @BindView(R.id.tv_end)
    TextView tvEnd;

    /**
     * 播放按钮
     */
    @BindView(R.id.btn_play)
    Button btnPlay;

    /**
     * 循环模式按钮
     */
    @BindView(R.id.btn_loop_model)
    Button btnLoopModel;

    private MusicPlayerManager musicPlayerManager;

    public SimplePlayerFragment() {
        // Required empty public constructor
    }

    public static SimplePlayerFragment newInstance() {
        SimplePlayerFragment fragment = new SimplePlayerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_simple_player, container, false);
    }

    @Override
    protected void initData() {
        super.initData();

        //获取播放管理器
        musicPlayerManager = MusicPlayerService.getMusicPlayerManager(getCurrentActivity());

        //测试播放音乐
        String songUrl = "http://dev-courses-misuc.ixuea.com/assets/s2.mp3";
        Song song = new Song();
        song.setUri(songUrl);
        musicPlayerManager.play(songUrl, song);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        //设置进度条监听器
        sbProgress.setOnSeekBarChangeListener(this);
    }

    /**
     * 界面恢复可见
     */
    @Override
    public void onResume() {
        super.onResume();

        LogUtil.d(TAG, "onResume");

        //设置播放监听器
        musicPlayerManager.addMusicPlayerListener(this);

        //显示音乐总时长
        showDuration();

        //显示播放进度
        showProgress();

        //显示播放状态
        showMusicPlayStatus();
    }

    /**
     * 界面进入后台不可见
     */
    @Override
    public void onPause() {
        super.onPause();

        //取消播放监听器
        musicPlayerManager.removeMusicPlayerListener(this);
    }

    /**
     * 上一曲点击
     */
    @OnClick(R.id.btn_previous)
    public void onPreviousClick() {
        LogUtil.d(TAG, "onPreviousClick");
    }

    /**
     * 播放点击
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @OnClick(R.id.btn_play)
    public void onPlayClick() {
        LogUtil.d(TAG, "onPlayClick");

        //测试通知渠道
//        Notification notification = NotificationUtil.getServiceForeground(getCurrentActivity().getApplicationContext());
//        NotificationUtil.showNotification(100, notification);

        playOrPause();
    }

    /**
     * 播放或者暂停
     */
    private void playOrPause() {
        if (musicPlayerManager.isPlaying()) {
            musicPlayerManager.pause();
        } else {
            musicPlayerManager.resume();
        }
    }

    /**
     * 下一曲点击
     */
    @OnClick(R.id.btn_next)
    public void onNextClick() {
        LogUtil.d(TAG, "onNextClick");

    }

    /**
     * 音乐循环模式点击
     */
    @OnClick(R.id.btn_loop_model)
    public void onLoopModelClick() {
        LogUtil.d(TAG, "onLoopModelClick");
    }

    /**
     * 进度条改变了
     *
     * @param fromUser 是否是用户触发的
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser)
            musicPlayerManager.seekTo(progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        LogUtil.d(TAG, "onStartTrackingTouch");
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        LogUtil.d(TAG, "onStopTrackingTouch");
    }

    //播放管理监听器
    @Override
    public void onPaused(Song data) {
        showPlayStatus();
    }

    @Override
    public void onPlaying(Song data) {
        showPauseStatus();
    }

    @Override
    public void onPrepared(MediaPlayer mp, Song data) {
        //显示时长
        showDuration();
    }

    @Override
    public void onProgress(Song data) {
        showProgress();
    }
    //end 播放管理监听器

    private void showPlayStatus() {
        btnPlay.setText("播放");
    }

    private void showPauseStatus() {
        btnPlay.setText("暂停");
    }

    // 显示音乐播放按钮等状态
    void showMusicPlayStatus() {
        if (musicPlayerManager.isPlaying()) {
            showPauseStatus();
        } else {
            showPlayStatus();
        }
    }

    private void showDuration() {
        //获取当前正在播放的音乐总时长
        long end = musicPlayerManager.getData().getDuration();

        //将毫秒格式化为分钟:秒
        tvEnd.setText(TimeUtil.formatMinuteSecond((int) end));

        //设置到进度条
        sbProgress.setMax((int) end);
    }

    /**
     * 显示进度
     */
    private void showProgress() {
        //获取当前的播放进度
        long progress = musicPlayerManager.getData().getProgress();

        //将毫秒格式化为分钟:秒
        tvStart.setText(TimeUtil.formatMinuteSecond((int) progress));

        //设置到进度条
        sbProgress.setProgress((int) progress);
    }
}
