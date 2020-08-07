package com.jacky.mycloudmusic.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.jacky.mycloudmusic.R;
import com.jacky.mycloudmusic.domain.Sheet;
import com.jacky.mycloudmusic.domain.Song;
import com.jacky.mycloudmusic.domain.event.CollectSongClickEvent;
import com.jacky.mycloudmusic.util.Constant;
import com.jacky.mycloudmusic.util.ImageUtil;
import com.jacky.mycloudmusic.util.PreferencesUtil;

import org.greenrobot.eventbus.EventBus;


/**
 * 底部滑出窗口类
 */
public class SongMoreDialogFragment extends BottomSheetDialogFragment {

    private NestedScrollView NestedSv;

    /**
     * 封面图
     */
    private ImageView ivBanner;

    /**
     * 标题
     */
    private TextView tvTitle;

    /**
     * 歌手名称
     */
    private TextView tvInfo;

    /**
     * 评论数
     */
    private TextView tvCommentCount;

    /**
     * 歌手名称
     */
    private TextView tvSingerName;

    /**
     * 从歌单中删除音乐容器
     */
    private View llDeleteSongInSheet;

    private LinearLayout llCollectSong;

    private Sheet mSheet;
    private Song mSong;

    public SongMoreDialogFragment() {
        // Required empty public constructor
    }

    /**
     * 创建实例
     */
    public static SongMoreDialogFragment newInstance(Sheet sheet, Song song) {
        SongMoreDialogFragment fragment = new SongMoreDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constant.SHEET, sheet);
        args.putSerializable(Constant.SONG, song);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mSheet = (Sheet) getArguments().getSerializable(Constant.SHEET);
            mSong = (Song) getArguments().getSerializable(Constant.SONG);
        }
    }

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_song_more_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews();
        initData();
        initListeners();
    }

    private void initViews() {
        NestedSv = getView().findViewById(R.id.nested_sv);
//        NestedSv.setNestedScrollingEnabled(false);

        ivBanner = getView().findViewById(R.id.iv_banner);

        tvTitle = getView().findViewById(R.id.tv_title);

        tvInfo = getView().findViewById(R.id.tv_info);

        tvCommentCount = getView().findViewById(R.id.tv_comment_count);

        tvSingerName = getView().findViewById(R.id.tv_singer_name);

        llDeleteSongInSheet = getView().findViewById(R.id.ll_delete_song_in_sheet);

        llCollectSong = getView().findViewById(R.id.ll_collect_song);


    }

    private void initData() {
        //封面
        ImageUtil.show(getActivity(), ivBanner, mSheet.getBanner());

        //标题
        tvTitle.setText(mSheet.getTitle());

        //歌手
        tvInfo.setText(mSong.getSinger().getNickname());

        //评论数
        tvCommentCount.setText(getResources()
                .getString(R.string.comment_count, mSheet.getComments_count()));

        //歌手
        tvSingerName.setText(getResources()
                .getString(R.string.singer_name, mSong.getSinger().getNickname()));

        //只有自己创建的歌单才显示删除音乐
        if (PreferencesUtil
                .getInstance(getActivity())
                .getUserId()
                .equals(mSheet.getUser().getId())) {
            llDeleteSongInSheet.setVisibility(View.VISIBLE);
        }
    }

    private void initListeners() {
        llCollectSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

                EventBus.getDefault().post(new CollectSongClickEvent(mSong));
            }
        });
    }

    public static void show(FragmentManager fragmentManager, Sheet sheet, Song song) {
        SongMoreDialogFragment fragment = newInstance(sheet, song);

        fragment.show(fragmentManager, "song_more_dialog");
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onStart() {
        super.onStart();

        dialogUIConfig();
    }

    private void dialogUIConfig() {
        //获取dialog对象
        BottomSheetDialog dialog = (BottomSheetDialog) getDialog();
        //获取diglog的根部局
        FrameLayout bottomSheet = dialog.getWindow().findViewById(R.id.design_bottom_sheet);

        //把默认背景颜色去掉，不然圆角显示不见
        bottomSheet.setBackground(new ColorDrawable(Color.TRANSPARENT));

        //固定弹窗高度为屏幕的3/4（值可自己修改），不允许上滑（默认可以上滑至全屏）
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomSheet.getLayoutParams();
        layoutParams.height = getPeekHeight();
        bottomSheet.setLayoutParams(layoutParams);

        //设置弹窗直接弹出到最大高度
        BottomSheetBehavior<FrameLayout> behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setPeekHeight(getPeekHeight());

        //设置禁止滑动
        BottomSheetBehavior.BottomSheetCallback bottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                //禁止拖拽
                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    //设置为收缩状态
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        };

        behavior.addBottomSheetCallback(bottomSheetCallback);
    }

    private int getPeekHeight() {
        int peekHeight = getResources().getDisplayMetrics().heightPixels;

        return peekHeight * 2 / 3;
    }
}
