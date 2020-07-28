package com.jacky.mycloudmusic.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.florent37.glidepalette.GlidePalette;
import com.jacky.mycloudmusic.R;
import com.jacky.mycloudmusic.activity.CommonToolbarActivity;
import com.jacky.mycloudmusic.adapter.SongAdapter;
import com.jacky.mycloudmusic.domain.Sheet;
import com.jacky.mycloudmusic.domain.response.DetailResponse;
import com.jacky.mycloudmusic.listener.HttpObserver;
import com.jacky.mycloudmusic.networkapi.RetrofitAPI;
import com.jacky.mycloudmusic.util.Constant;
import com.jacky.mycloudmusic.util.ImageUtil;
import com.jacky.mycloudmusic.util.ToastUtil;

import butterknife.BindView;
import retrofit2.Response;

/**
 * 歌单详情页
 */
public class SheetDetailFragment extends BaseCommonFragment implements View.OnClickListener {

    private static final String TAG = "======SheetDetailFragment";
    @BindView(R.id.rv)
    RecyclerView rv;

    private LinearLayout llHeader;

    private ImageView ivBanner;

    private TextView tvTitle;

    private LinearLayout llUser;

    private ImageView ivAvatar;

    private TextView tvUserName;

    private LinearLayout llCommentContainer;

    private TextView tvCommentCount;

    private LinearLayout llPlayAllContainer;

    private TextView tvMusicCount;

    private Button btnCollection;

    /**
     * 歌单id
     */
    private String sheetId;

    /**
     * 歌单详细数据
     */
    private Sheet data;

    private SongAdapter adapter;

    private SheetDetailFragment(String sheetId) {
        this.sheetId = sheetId;
    }

    public static SheetDetailFragment newInstance(String sheetId) {
        SheetDetailFragment fragment = new SheetDetailFragment(sheetId);
        Bundle args = new Bundle();
        //args.putString(Constant.SHEET_ID, sheetId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sheet_detail, container, false);
    }

    @Override
    protected void initViews() {
        super.initViews();

        rv.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getCurrentActivity());
        rv.setLayoutManager(layoutManager);

        adapter = new SongAdapter(R.layout.item_song_in_sheet);

        adapter.setHeaderView(createHeaderView());

        rv.setAdapter(adapter);
    }

    private View createHeaderView() {
        View view = getLayoutInflater().inflate(R.layout.header_sheet_detail, (ViewGroup) rv.getParent(), false);

        llHeader = view.findViewById(R.id.ll_header);
        ivBanner = view.findViewById(R.id.iv_banner);
        tvTitle = view.findViewById(R.id.tv_title);
        llUser = view.findViewById(R.id.ll_user);
        ivAvatar = view.findViewById(R.id.iv_avatar);
        tvUserName = view.findViewById(R.id.tv_user_name);
        llCommentContainer = view.findViewById(R.id.ll_comment_container);
        tvCommentCount = view.findViewById(R.id.tv_comment_count);
        llPlayAllContainer = view.findViewById(R.id.ll_play_all_container);
        tvMusicCount = view.findViewById(R.id.tv_music_count);
        btnCollection = view.findViewById(R.id.btn_collection);

        return view;
    }

    @Override
    protected void initData() {
        super.initData();

        fetchData();
    }

    /**
     * 请求数据
     */
    private void fetchData() {

        RetrofitAPI.getInstance()
                .requestSheetDetail(sheetId)
                .subscribe(new HttpObserver<DetailResponse<Sheet>>() {
                    @Override
                    public void onSucceeded(DetailResponse<Sheet> sheetDetailResponse) {
                        next(sheetDetailResponse.getData());
                    }
                });
    }

    /**
     * 显示头部数据
     *
     * @param data
     */
    private void next(Sheet data) {
        this.data = data;
        if (data.getSongs() != null && data.getSongs().size() > 0) {
            adapter.replaceData(data.getSongs());
        }

        //设置封面以及主题色
        //ImageUtil.show(getCurrentActivity(), ivBanner, data.getBanner());
        String uri = data.getBanner();
        if (TextUtils.isEmpty(uri)) {
            //没有值
            //显示默认图片
            ivBanner.setImageResource(R.drawable.placeholder);
        } else {
            //处理uri
            //是相对路径则转换为绝对路径
            if (!uri.startsWith("http"))
                uri = String.format(Constant.RESOURCE_ENDPOINT, uri);

            //显示图片
            //使用GlidePalette
            RequestListener<Drawable> glidePalette = GlidePalette
                    .with(uri)
                    .use(GlidePalette.Profile.VIBRANT)
                    .intoBackground(getCurrentActivity().findViewById(R.id.toolbar), GlidePalette.Swatch.RGB)
                    .intoBackground(llHeader, GlidePalette.Swatch.RGB)
                    .intoCallBack(palette -> {
                        Palette.Swatch swatch = palette.getVibrantSwatch();
                        if (swatch != null) {
                            int rgb = swatch.getRgb();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                Window window = getCurrentActivity().getWindow();
                                window.setStatusBarColor(rgb);
                                window.setNavigationBarColor(rgb);
                            }
                        }
                    })
                    .crossfade(true);

            Glide.with(this)
                    .load(uri)
                    .listener(glidePalette)
                    .into(ivBanner);

            //直接使用Palette
//            Glide.with(this)
//                    .asBitmap()
//                    .load(uri)
//                    .into(new CustomTarget<Bitmap>() {
//                        /**
//                         * 资源加载完成
//                         */
//                        @Override
//                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                            ivBanner.setImageBitmap(resource);
//
//                            Palette.from(resource)
//                                    .generate(new Palette.PaletteAsyncListener() {
//                                        @Override
//                                        public void onGenerated(@Nullable Palette palette) {
//                                            Palette.Swatch swatch = palette.getVibrantSwatch();
//                                            if (swatch != null) {
//                                                int rgb = swatch.getRgb();
//                                                getCurrentActivity().findViewById(R.id.toolbar).setBackgroundColor(rgb);
//                                                llHeader.setBackgroundColor(rgb);
//
//                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                                                    Window window = getCurrentActivity().getWindow();
//                                                    window.setStatusBarColor(rgb);
//                                                    window.setNavigationBarColor(rgb);
//                                                }
//                                            }
//                                        }
//                                    });
//                        }
//
//                        /**
//                         * 加载任务取消
//                         */
//                        @Override
//                        public void onLoadCleared(@Nullable Drawable placeholder) {
//
//                        }
//                    });
        }

        //其他设置
        tvTitle.setText(data.getTitle());
        ImageUtil.showAvatar(getCurrentActivity(), ivAvatar, data.getUser().getAvatar());
        tvUserName.setText(data.getUser().getNickname());
        tvCommentCount.setText(String.valueOf(data.getComments_count()));
        tvMusicCount.setText(getString(R.string.music_count, data.getSongsCount()));

        //收藏按钮
        showCollectionStatus();
    }

    @SuppressLint("ResourceType")
    private void showCollectionStatus() {
        if (data.isCollection()) {
            //将按钮文字改为取消
            btnCollection.setText(getResources().getString(R.string.cancel_collection, data.getCollections_count()));

            //弱化取消收藏按钮,去掉背景
            btnCollection.setBackgroundColor(Color.TRANSPARENT);
            //btnCollection.setBackground(null);

            //设置文字颜色为灰色
            btnCollection.setTextColor(getResources().getColor(R.color.light_grey));
        } else {
            //将按钮文字改为收藏
            btnCollection.setText(getResources().getString(R.string.collection, data.getCollections_count()));

            //设置按钮颜色为主色调
            btnCollection.setBackgroundResource(R.drawable.selector_color_primary);

            //将文字颜色设置为白色
            btnCollection.setTextColor(getResources().getColorStateList(R.color.selector_text_color_primary_reverse));
        }
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        btnCollection.setOnClickListener(this);
        llUser.setOnClickListener(this);
        llCommentContainer.setOnClickListener(this);

        //设置item点击事件
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivityContainFragment(CommonToolbarActivity.class, Constant.SIMPLE_PLAYER_FRAGMENT);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_collection:
                onBtnCollectionClick();
                break;
            case R.id.ll_user:
                onUserClick();
                break;
            case R.id.ll_comment_container:
                onCommentClick();
                break;
            default:
                break;
        }
    }

    private void onBtnCollectionClick() {
        if (data.isCollection()) {
            //已经收藏了

            //取消收藏
            RetrofitAPI.getInstance()
                    .deleteCollect(data.getId())
                    .subscribe(new HttpObserver<Response<Void>>() {
                        @Override
                        public void onSucceeded(Response<Void> d) {
                            //弹出提示
                            ToastUtil.successShortToast(R.string.cancel_success);

                            //重新加载数据
                            //目的是显示新的收藏状态
                            //fetchData();
                            data.setCollection_id(null);
                            data.setCollections_count(data.getCollections_count() - 1);
                            showCollectionStatus();
                        }
                    });
        } else {
            //没有收藏

            //收藏
            RetrofitAPI.getInstance()
                    .collect(data.getId())
                    .subscribe(new HttpObserver<Response<Void>>() {
                        @Override
                        public void onSucceeded(Response<Void> d) {
                            //弹出提示
                            ToastUtil.successShortToast(R.string.collection_success);

                            //重新加载数据
                            //目的是显示新的收藏状态
                            //fetchData();
                            data.setCollection_id(1);
                            data.setCollections_count(data.getCollections_count() + 1);
                            //刷新状态
                            showCollectionStatus();
                        }
                    });
        }
    }

    private void onUserClick() {
        startActivityContainFragment(CommonToolbarActivity.class, Constant.USER_DETAIL_FRAGMENT, data.getUser().getId());
    }

    private void onCommentClick() {
        startActivityContainFragment(CommonToolbarActivity.class, Constant.COMMENT_FRAGMENT, sheetId);
    }
}
