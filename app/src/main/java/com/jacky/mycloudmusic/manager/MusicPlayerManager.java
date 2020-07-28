package com.jacky.mycloudmusic.manager;

import com.jacky.mycloudmusic.domain.Song;

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
}
