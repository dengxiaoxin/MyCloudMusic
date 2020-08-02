package com.jacky.mycloudmusic.listener;

import android.media.MediaPlayer;

import com.jacky.mycloudmusic.domain.Song;

public interface MusicPlayerListener {
    /**
     * 已经暂停了
     */
    void onPaused(Song data);

    /**
     * 已经播放了
     */
    void onPlaying(Song data);

    /**
     * MediaPlayer准备好了
     */
    void onPrepared(MediaPlayer mp, Song data);

    /**
     * 音乐进度改变
     */
    void onProgress(Song data);

    /**
     * 音乐播放完成
     */
    default void onCompletion(MediaPlayer mp) {

    }
}
