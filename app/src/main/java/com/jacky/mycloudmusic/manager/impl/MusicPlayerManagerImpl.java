package com.jacky.mycloudmusic.manager.impl;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import com.jacky.mycloudmusic.domain.Song;
import com.jacky.mycloudmusic.listener.MusicPlayerListener;
import com.jacky.mycloudmusic.manager.MusicPlayerManager;
import com.jacky.mycloudmusic.util.Constant;
import com.jacky.mycloudmusic.util.ListUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * MusicPlayerManager接口的实现类
 */
public class MusicPlayerManagerImpl implements MusicPlayerManager {
    private static final String TAG = "======MusicPlayerManagerImpl";
    /**
     * 单例实例对象
     */
    private static MusicPlayerManagerImpl instance;

    /**
     * 保存APP的上下文
     */
    private final Context context;

    /**
     * 播放器
     */
    private final MediaPlayer player;

    /**
     * 当前播放的音乐对象
     */
    private Song data;

    /**
     * 播放器状态监听器
     */
    private List<MusicPlayerListener> listeners = new ArrayList<>();

    /**
     * 定时器任务TimerTask：定时器每tick一次要进行的动作
     * 定时器Timer
     */
    private TimerTask timerTask;
    private Timer timer;

    private MusicPlayerManagerImpl(Context context) {
        //保存context
        //因为后面可能用到
        this.context = context.getApplicationContext();

        //初始化播放器
        player = new MediaPlayer();

        //设置播放器监听
        initMediaPlayerListener();
    }

    /**
     * 设置播放器监听
     */
    private void initMediaPlayerListener() {
        //设置播放器准备完毕监听器,应该是在player.prepare()后触发
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            /**
             * 播放器准备开始播放
             *
             * 这里可以获取到音乐时长
             * 如果是视频还能获取到视频宽高等信息
             */
            @Override
            public void onPrepared(MediaPlayer mp) {

                //将音乐总时长保存到音乐对象
                data.setDuration(mp.getDuration());

                //回调监听器
                ListUtil.eachListener(listeners, listener -> listener.onPrepared(mp, data));
            }
        });
    }

    /**
     * 获取播放管理器的单例对象
     * <p>
     * getInstance：方法名可以随便取
     * 只是在Java这边大部分项目都取这个名字
     * synchronized简单解决多线程同步问题
     */
    public static synchronized MusicPlayerManager getInstance(Context context) {
        if (instance == null) {
            instance = new MusicPlayerManagerImpl(context);
        }

        return instance;
    }

    @Override
    public void play(String uri, Song data) {
        try {
            //保存音乐对象
            this.data = data;

            //释放player
            player.reset();

            //设置数据源
            player.setDataSource(uri);

            //同步准备
            //真实项目中可能会使用异步，因为如果网络不好，同步可能会卡住
            player.prepare();

            //开始播放
            player.start();

            //回调监听器
            publishMusicStatus();

            //启动播放进度通知
            startPublishProgress();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isPlaying() {
        return player.isPlaying();
    }

    @Override
    public void pause() {
        if (isPlaying()) {
            player.pause();

            //回调监听器
            publishMusicStatus();

            //停止播放进度通知
            stopPublishProgress();
        }
    }

    @Override
    public void resume() {
        if (!isPlaying()) {
            player.start();

            //回调监听器
            publishMusicStatus();

            //启动播放进度通知
            startPublishProgress();
        }
    }

    @Override
    public void addMusicPlayerListener(MusicPlayerListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }

        //启动播放进度通知
        startPublishProgress();
    }

    @Override
    public void removeMusicPlayerListener(MusicPlayerListener listener) {
        listeners.remove(listener);
    }

    @Override
    public Song getData() {
        return data;
    }

    @Override
    public void seekTo(int progress) {
        player.seekTo(progress);
    }

    private void publishMusicStatus() {
        if (isPlaying())
            ListUtil.eachListener(listeners, listener -> listener.onPlaying(data));
        else
            ListUtil.eachListener(listeners, listener -> listener.onPaused(data));
    }

    /**
     * 启动播放进度通知
     */
    private void startPublishProgress() {
        if (listeners.isEmpty()) {
            return;
        }

        if (!isPlaying()) {
            //没有播放音乐就不用启动
            return;
        }

        if (timerTask != null) {
            return;
        }

        timerTask = new TimerTask() {
            @Override
            public void run() {
                if (listeners.isEmpty()) {
                    stopPublishProgress();
                    return;
                }

                handler.obtainMessage(Constant.MESSAGE_PROGRESS).sendToTarget();
            }
        };

        timer = new Timer();
        timer.schedule(timerTask, 0, Constant.DEFAULT_TIME);
    }

    /**
     * 停止播放进度通知
     */
    private void stopPublishProgress() {
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }

        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    /**
     * 创建Handler
     * 用来将事件转换到主线程
     */
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            if (msg.what == Constant.MESSAGE_PROGRESS) {
                //将进度设置到音乐对象
                data.setProgress(player.getCurrentPosition());

                //回调监听
                ListUtil.eachListener(listeners, listener -> listener.onProgress(data));
            }
        }
    };

}
