package com.jacky.mycloudmusic.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jacky.mycloudmusic.domain.Song;

public class SimplePlayerRvAdapter extends BaseQuickAdapter<Song, BaseViewHolder> {
    public SimplePlayerRvAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Song item) {
        helper.setText(android.R.id.text1, item.getTitle());
    }
}
