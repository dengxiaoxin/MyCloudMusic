package com.jacky.mycloudmusic.adapter;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jacky.mycloudmusic.R;
import com.jacky.mycloudmusic.domain.BaseMultiItemEntity;
import com.jacky.mycloudmusic.domain.Sheet;
import com.jacky.mycloudmusic.domain.Song;
import com.jacky.mycloudmusic.domain.Title;
import com.jacky.mycloudmusic.util.Constant;
import com.jacky.mycloudmusic.util.ImageUtil;

import java.util.ArrayList;

public class DiscoveryAdapter extends BaseMultiItemQuickAdapter<BaseMultiItemEntity, BaseViewHolder> {
    /**
     * 构造方法
     */
    public DiscoveryAdapter() {
        //第一次要传入数据，而这时候我们还没有准备好数据，所以先传递一个空列表
        super(new ArrayList<>());

        //添加多类型item布局

        //添加标题类型
        addItemType(Constant.TYPE_TITLE, R.layout.item_title);

        //添加歌单类型
        addItemType(Constant.TYPE_SHEET, R.layout.item_sheet);

        //添加单曲类型
        addItemType(Constant.TYPE_SONG, R.layout.item_song);
    }

    /**
     * 绑定数据方法
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, BaseMultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case Constant.TYPE_TITLE:
                //标题
                Title title = (Title) item;
                helper.setText(R.id.tv_title, title.getTitle());
                break;
            case Constant.TYPE_SHEET:
                //歌单
                Sheet sheet = (Sheet) item;
                ImageUtil.show((Activity) mContext, helper.getView(R.id.iv_banner), sheet.getBanner());
                helper.setText(R.id.tv_title, sheet.getTitle());
                helper.setText(R.id.tv_clicks_count, String.valueOf(sheet.getClicks_count()));
                break;
            case Constant.TYPE_SONG:
                //单曲
                Song song = (Song) item;
                ImageUtil.show((Activity) mContext, helper.getView(R.id.iv_banner), song.getBanner());
                helper.setText(R.id.tv_title, song.getTitle());
                helper.setText(R.id.tv_clicks_count, String.valueOf(song.getClicks_count()));
                ImageUtil.showAvatar((Activity) mContext, helper.getView(R.id.iv_avatar), song.getSinger().getAvatar());
                helper.setText(R.id.tv_singer_name, song.getSinger().getNickname());
                break;
            default:
                break;
        }
    }
}
