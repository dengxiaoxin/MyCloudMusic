package com.jacky.mycloudmusic.manager;

import com.jacky.mycloudmusic.domain.Song;
import com.jacky.mycloudmusic.listener.MusicPlayerListener;

/**
 * 音乐播放器对外暴露的接口
 */
public interface MusicPlayerManager {
    /**
     * 播放
     *
     * @param uri  播放音乐的绝对地址
     * @param data 音乐对象
     */
    void play(String uri, Song data);

    /**
     * 是否在播放
     *
     * @return
     */
    boolean isPlaying();

    /**
     * 暂停
     */
    void pause();

    /**
     * 继续播放
     */
    void resume();

    /**
     * 添加播放监听器
     */
    void addMusicPlayerListener(MusicPlayerListener listener);

    /**
     * 移除播放监听器
     */
    void removeMusicPlayerListener(MusicPlayerListener listener);

    /**
     * 获取歌曲对象
     */
    Song getData();

    /**
     * 跳转到指定位置
     *
     * @param progress 单位:毫秒
     */
    void seekTo(int progress);

    /**
     * 设置歌曲是否循环
     */
    void setLooping(boolean b);

}
