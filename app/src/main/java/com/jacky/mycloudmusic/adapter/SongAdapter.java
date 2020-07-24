package com.jacky.mycloudmusic.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jacky.mycloudmusic.R;
import com.jacky.mycloudmusic.domain.Song;

/**
 * 歌单详情-歌曲适配器
 */
public class SongAdapter extends BaseQuickAdapter<Song, BaseViewHolder> {
    /**
     * 构造方法
     *
     * @param layoutResId 布局Id
     */
    public SongAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Song item) {
        helper.setText(R.id.tv_position, String.valueOf(helper.getAdapterPosition()));
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_singer_name, item.getSinger().getNickname());
    }
}
