package com.jacky.mycloudmusic.manager;

import com.jacky.mycloudmusic.domain.Song;

import java.util.List;

/**
 * 列表管理器
 * 主要是封装了列表相关的操作
 * 例如：上一曲，下一曲，循环模式
 */
public interface ListManager {

    /**
     * 设置播放列表
     */
    void setDataList(List<Song> dataList);

    /**
     * 获取播放列表
     */
    List<Song> getDataList();

    /**
     * 获取歌曲信息
     */
    Song getData();

    /**
     * 播放
     */
    void play(Song data);

    /**
     * 播放
     * 检查是否是同一首，是同一首就不重新播放
     * 检查首次播放的时候是否是上次退出时记录的播放音乐，是的话跳转到上次播放的位置继续播放
     */
    void playWithCheck(Song data);

    /**
     * 暂停
     */
    void pause();

    /**
     * 继续播放
     */
    void resume();

    /**
     * 继续播放
     */
    void seekTo(int progress);

    /**
     * 下一曲
     */
    Song next();

    /**
     * 上一曲
     */
    Song previous();

    /**
     * 更改循环模式
     */
    int changeLoopModel();

    /**
     * 获取循环模式
     */
    int getLoopModel();
}
