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
    private int selectedIndex = -1;
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

        if (selectedIndex == helper.getAdapterPosition()) {
            helper.setTextColor(R.id.tv_title, mContext.getResources().getColor(R.color.colorPrimary));
        } else {
            helper.setTextColor(R.id.tv_title, mContext.getResources().getColor(R.color.text));
        }

        helper.addOnClickListener(R.id.ib_more);
    }

    public void setSelectedIndex(int index) {
        //先刷新上一行
        notifyItemChanged(selectedIndex);

        //保存选中索引
        selectedIndex = index;

        //刷新当前行
        notifyItemChanged(selectedIndex);
    }
}
