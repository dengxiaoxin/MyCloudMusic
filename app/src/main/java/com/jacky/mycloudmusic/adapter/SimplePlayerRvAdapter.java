package com.jacky.mycloudmusic.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jacky.mycloudmusic.R;
import com.jacky.mycloudmusic.domain.Song;

public class SimplePlayerRvAdapter extends BaseQuickAdapter<Song, BaseViewHolder> {
    private int selectedIndex = -1;

    public SimplePlayerRvAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Song item) {
        helper.setText(android.R.id.text1, item.getTitle());

        if (selectedIndex == helper.getAdapterPosition()) {
            helper.setTextColor(android.R.id.text1, mContext.getResources().getColor(R.color.colorPrimary));
        } else {
            helper.setTextColor(android.R.id.text1, mContext.getResources().getColor(R.color.text));
        }
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
