package com.jacky.mycloudmusic.adapter;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jacky.mycloudmusic.R;
import com.jacky.mycloudmusic.domain.Video;
import com.jacky.mycloudmusic.util.ImageUtil;
import com.jacky.mycloudmusic.util.TimeUtil;

public class VideoRvAdapter extends BaseQuickAdapter<Video, BaseViewHolder> {
    public VideoRvAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Video item) {
        //封面
        //ImageUtil.show((Activity) mContext, helper.getView(R.id.iv_banner), item.getBanner());
        ImageUtil.showSmallCorners((Activity) mContext, helper.getView(R.id.iv_banner), item.getBanner());

        //点击数
        helper.setText(R.id.tv_count, String.valueOf(item.getClicks_count()));

        //视频时长
        helper.setText(R.id.tv_time,
                TimeUtil.s2ms((int) item.getDuration()));

        //标题
        helper.setText(R.id.tv_title, item.getTitle());

        //头像
        ImageUtil.showAvatar((Activity) mContext,
                helper.getView(R.id.iv_avatar),
                item.getUser().getAvatar());

        //昵称
        helper.setText(R.id.tv_nickname, item.getUser().getNickname());

        //点赞数
        //helper.setText(R.id.tv_likes_count,String.valueOf(data.get))

        //评论数
        helper.setText(R.id.tv_comments_count, String.valueOf(item.getComments_count()));
    }
}
