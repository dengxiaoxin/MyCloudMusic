package com.jacky.mycloudmusic.fragment;

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
import com.jacky.mycloudmusic.manager.MusicPlayerManager;
import com.jacky.mycloudmusic.service.MusicPlayerService;
import com.jacky.mycloudmusic.util.LogUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 简单播放器界面
 */
public class SimplePlayerFragment extends BaseCommonFragment implements SeekBar.OnSeekBarChangeListener {

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
        String songUrl = "http://dev-courses-misuc.ixuea.com/assets/s1.mp3";
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
        LogUtil.d(TAG, "onProgressChanged" + progress + "," + fromUser);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        LogUtil.d(TAG, "onStartTrackingTouch");
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        LogUtil.d(TAG, "onStopTrackingTouch");
    }
}
