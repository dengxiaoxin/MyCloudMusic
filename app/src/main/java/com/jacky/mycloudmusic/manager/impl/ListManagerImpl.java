package com.jacky.mycloudmusic.manager.impl;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.CountDownTimer;

import com.jacky.mycloudmusic.domain.Song;
import com.jacky.mycloudmusic.listener.IntFunction;
import com.jacky.mycloudmusic.listener.MusicPlayerListener;
import com.jacky.mycloudmusic.manager.ListManager;
import com.jacky.mycloudmusic.manager.MusicPlayerManager;
import com.jacky.mycloudmusic.service.MusicPlayerService;
import com.jacky.mycloudmusic.util.Constant;
import com.jacky.mycloudmusic.util.LogUtil;
import com.jacky.mycloudmusic.util.PreferencesUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ListManagerImpl implements ListManager, MusicPlayerListener {
    private static final String TAG = "======ListManagerImpl";

    /**
     * 单例实例对象
     */
    private static ListManagerImpl instance;

    /**
     * 保存APP的上下文
     */
    private final Context context;

    /**
     * 音乐播放管理器
     */
    private final MusicPlayerManager musicPlayerManager;

    /**
     * 偏好设置工具类
     */
    private PreferencesUtil sp;

    /**
     * 音乐是否已经播放了，感觉是用来记录一首音乐的上次播放进度
     */
    private boolean isPlayed;

    /**
     * 歌曲列表
     */
    private List<Song> dataList = new LinkedList<>();

    /**
     * 当前音乐对象
     */
    private Song data;

    /**
     * 循环模式，默认列表循环
     */
    private int model;

    private ListManagerImpl(Context context) {
        this.context = context.getApplicationContext();

        musicPlayerManager = MusicPlayerService.getMusicPlayerManager(this.context);
        musicPlayerManager.addMusicPlayerListener(this);

        sp = PreferencesUtil.getInstance(context);
        model = sp.getLoopMode();
    }

    /**
     * 获取列表管理器单例
     */
    public static synchronized ListManagerImpl getInstance(Context context) {
        if (instance == null) {
            instance = new ListManagerImpl(context);
        }
        return instance;
    }

    @Override
    public void setDataList(List<Song> dataList) {
        LogUtil.d(TAG, "setSongList");

        this.dataList.clear();
        this.dataList.addAll(dataList);
    }

    @Override
    public List<Song> getDataList() {
        LogUtil.d(TAG, "getSongList");
        return dataList;
    }

    @Override
    public Song getData() {
        return data;
    }

    @Override
    public void play(Song data) {
        LogUtil.d(TAG, "play");

        isPlayed = true;

        smoothPlayAction(data);
//        this.data = data;
//        musicPlayerManager.play(String.format(Constant.RESOURCE_ENDPOINT, data.getUri()), data);

        if (Constant.MODEL_LOOP_ONE == model) {
            musicPlayerManager.setLooping(true);
        }
    }

    @Override
    public void playWithCheck(Song data) {
        //如果请求播放的音乐和正在播放的音乐是同一首则不重新播放
        Song curData = this.data;
        assert data != null;
        if (curData == null || !curData.getId().equals(data.getId())) {
            play(data);

            if (curData == null) {
                String lastSongId = sp.getLastSongId();
                if (lastSongId != null && lastSongId.equals(data.getId())) {
                    //首次播放音乐，如果当前播放音乐和上次记录播放音乐相同，则跳转到上次播放处
                    seekTo((int) sp.getLastSongProgress());
                }
            }
        }
    }

    @Override
    public void pause() {
        LogUtil.d(TAG, "pause");

        //musicPlayerManager.pause();
        smoothPauseOrResume(true);
    }

    @Override
    public void resume() {
        LogUtil.d(TAG, "resume");

        if (isPlayed) {
            //如果上次已经播放过
            //musicPlayerManager.resume();
            smoothPauseOrResume(false);
        } else {
            //如果还没播放过
            play(data);
        }
    }

    @Override
    public void seekTo(int progress) {
        LogUtil.d(TAG, "seekTo");

        musicPlayerManager.seekTo(progress);
    }

    @Override
    public Song next() {
        return changeSong(this::getSortNextIndex);
    }

    @Override
    public Song previous() {
        return changeSong(this::getSortPreviousIndex);
    }

    @Override
    public int changeLoopModel() {
        model = (model + 1) % Constant.LOOP_MODEL_COUNT;

        sp.setLoopMode(model);

        //判断是否是单曲循环
        if (Constant.MODEL_LOOP_ONE == model) {
            musicPlayerManager.setLooping(true);
        } else {
            musicPlayerManager.setLooping(false);
        }

        return model;
    }

    @Override
    public int getLoopModel() {
        return model;
    }

    @Override
    public void onPaused(Song data) {

    }

    @Override
    public void onPlaying(Song data) {

    }

    @Override
    public void onPrepared(MediaPlayer mp, Song data) {

    }

    @Override
    public void onProgress(Song data) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        LogUtil.d(TAG, "onCompletion");
        //单曲循环不会触发此事件
        //所以这里只要处理非单曲循环的情况即可
        next();
    }

    private void smoothPlayAction(Song data) {
        if (this.data != null && musicPlayerManager.isPlaying()) {
            //渐强的时长，单位：毫秒
            final long duration = Constant.MUSIC_SMOOTH_DURATION;
            //音量调节的时间间隔
            long interval = Constant.MUSIC_SMOOTH_INTERVAL;

            new CountDownTimer(duration, interval) {

                @Override
                public void onTick(long millisUntilFinished) {
                    float volume;
                    volume = millisUntilFinished * 1.0f / duration;

                    musicPlayerManager.setVolume(volume, volume);
                }

                @Override
                public void onFinish() {
                    musicPlayerManager.setVolume(1f, 1f);

                    ListManagerImpl.this.data = data;
                    musicPlayerManager.play(String.format(Constant.RESOURCE_ENDPOINT, data.getUri()), data);
                }
            }.start();
        } else {
            this.data = data;
            musicPlayerManager.play(String.format(Constant.RESOURCE_ENDPOINT, data.getUri()), data);
        }
    }

    private void smoothPauseOrResume(boolean isPause) {
        if (!isPause) {
            musicPlayerManager.setVolume(0f, 0f);
            musicPlayerManager.resume();
        }

        //渐强的时长，单位：毫秒
        final long duration = Constant.MUSIC_SMOOTH_DURATION;
        //音量调节的时间间隔
        long interval = Constant.MUSIC_SMOOTH_INTERVAL;

        new CountDownTimer(duration, interval) {

            @Override
            public void onTick(long millisUntilFinished) {
                float volume;
                if (isPause) {
                    volume = millisUntilFinished * 1.0f / duration;
                } else {
                    volume = 1f - millisUntilFinished * 1.0f / duration;
                }

                musicPlayerManager.setVolume(volume, volume);
            }

            @Override
            public void onFinish() {
                if (isPause) {
                    musicPlayerManager.pause();
                }

                musicPlayerManager.setVolume(1f, 1f);
            }
        }.start();
    }

    private Song changeSong(IntFunction<Integer> action) {
        int index = 0;
        if (Constant.MODEL_LOOP_RANDOM == model) {
            index = new Random().nextInt(dataList.size());
        } else {
            index = dataList.indexOf(data);
            if (index != -1) {
                index = action.apply(index);
            } else {
                throw new IllegalArgumentException("Cant't find current song");
            }

        }

        Song song = dataList.get(index);
        play(song);

        return song;
    }

    private int getSortNextIndex(int curIndex) {
        return (curIndex + 1) % dataList.size();
    }

    private int getSortPreviousIndex(int curIndex) {
        if (curIndex == 0) {
            return dataList.size() - 1;
        } else {
            return curIndex - 1;
        }
    }
}
