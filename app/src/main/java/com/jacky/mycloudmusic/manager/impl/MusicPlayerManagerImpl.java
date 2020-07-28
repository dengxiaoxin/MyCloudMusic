package com.jacky.mycloudmusic.manager.impl;

import android.content.Context;
import android.media.MediaPlayer;

import com.jacky.mycloudmusic.domain.Song;
import com.jacky.mycloudmusic.manager.MusicPlayerManager;

import java.io.IOException;

/**
 * MusicPlayerManager接口的实现类
 */
public class MusicPlayerManagerImpl implements MusicPlayerManager {
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

    private MusicPlayerManagerImpl(Context context) {
        //保存context
        //因为后面可能用到
        this.context = context.getApplicationContext();

        //初始化播放器
        player = new MediaPlayer();
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
        }
    }

    @Override
    public void resume() {
        if (!isPlaying()) {
            player.start();
        }
    }
}
