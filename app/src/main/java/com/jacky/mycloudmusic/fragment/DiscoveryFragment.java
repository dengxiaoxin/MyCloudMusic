package com.jacky.mycloudmusic.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jacky.mycloudmusic.R;
import com.jacky.mycloudmusic.activity.CommonToolbarActivity;
import com.jacky.mycloudmusic.activity.ImagePreviewActivity;
import com.jacky.mycloudmusic.adapter.DiscoveryAdapter;
import com.jacky.mycloudmusic.domain.Ad;
import com.jacky.mycloudmusic.domain.BaseMultiItemEntity;
import com.jacky.mycloudmusic.domain.Sheet;
import com.jacky.mycloudmusic.domain.Song;
import com.jacky.mycloudmusic.domain.Title;
import com.jacky.mycloudmusic.domain.response.ListResponse;
import com.jacky.mycloudmusic.listener.HttpObserver;
import com.jacky.mycloudmusic.manager.ListManager;
import com.jacky.mycloudmusic.networkapi.RetrofitAPI;
import com.jacky.mycloudmusic.service.MusicPlayerService;
import com.jacky.mycloudmusic.util.Constant;
import com.jacky.mycloudmusic.util.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;

/**
 * 发现界面
 */
public class DiscoveryFragment extends BaseCommonFragment implements OnBannerListener {
    /**
     * 列表控件
     */
    @BindView(R.id.rv)
    RecyclerView rv;

    /**
     * 轮播图控件
     */
    private Banner banner;

    /**
     * 轮播图控件数据
     */
    private List<Ad> bannerData;

    /**
     * RecyclerView适配器
     */
    private DiscoveryAdapter adapter;

    public DiscoveryFragment() {
        // Required empty public constructor
    }

    /**
     * 构造方法
     * <p>
     * 固定写法
     */
    public static DiscoveryFragment newInstance() {
        DiscoveryFragment fragment = new DiscoveryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_discovery, container, false);
    }

    @Override
    protected void initViews() {
        super.initViews();

        //高度固定
        rv.setHasFixedSize(true);

        //设置显示3列
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        rv.setLayoutManager(layoutManager);

        //创建适配器
        adapter = new DiscoveryAdapter();

        //设置Item宽度
        adapter.setSpanSizeLookup((gridLayoutManager, position) -> {
            //在这里获取数据模型里的宽度
            return adapter.getItem(position).getSpanSize();
        });

        //添加头部
        adapter.addHeaderView(createHeaderView());

        //设置适配器
        rv.setAdapter(adapter);

        //请求数据
        fetch();

        //请求轮播图数据
        fetchBannerData();
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Object data = adapter.getItem(position);
                if (data instanceof Song) {
                    //单曲
                    List<Song> songList = new ArrayList<>();
                    Song song = (Song) data;
                    songList.add(song);
                    ListManager listManager = MusicPlayerService.getListManager(getCurrentActivity());
                    listManager.setDataList(songList);
                    listManager.playWithCheck(song);
                    startActivityContainFragment(CommonToolbarActivity.class, Constant.SIMPLE_PLAYER_FRAGMENT);
                } else if (data instanceof Sheet) {
                    //歌单
                    Sheet sheet = (Sheet) data;
                    startActivityContainFragment(CommonToolbarActivity.class, Constant.SHEET_DETAIL_FRAGMENT, sheet.getId());
                }
            }
        });

        adapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                Object data = adapter.getItem(position);
                if (data instanceof Song) {
                    //单曲
                } else if (data instanceof Sheet) {
                    //歌单
                    Sheet sheet = (Sheet) data;
                    ImagePreviewActivity.start(getCurrentActivity(), sheet.getId(), sheet.getBanner());
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 创建头部布局
     */
    private View createHeaderView() {
        //从XML创建View
        View view = getLayoutInflater().inflate(R.layout.header_discovery, (ViewGroup) rv.getParent(), false);

        banner = view.findViewById(R.id.banner);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //轮播图点击事件
        banner.setOnBannerListener(this);

        //设置日期
        TextView tvDay = view.findViewById(R.id.tv_day);
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        tvDay.setText(String.valueOf(day));

        return view;
    }

    /**
     * 请求数据
     */
    private void fetch() {
//        //测试数据
//        List<BaseMultiItemEntity> dataTest = new ArrayList<>();
//
//        //添加标题
//        dataTest.add(new Title("推荐歌单"));
//
//        //添加歌单数据
//        for (int i = 0; i < 9; i++) {
//            dataTest.add(new Sheet());
//        }
//
//        //添加标题
//        dataTest.add(new Title("推荐单曲"));
//
//        //添加单曲数据
//        for (int i = 0; i < 9; i++) {
//            dataTest.add(new Song());
//        }
//
//        //将数据设置（替换）到适配器
//        adapter.replaceData(dataTest);

        //正式请求数据
        List<BaseMultiItemEntity> data = new ArrayList<>();

        //添加标题
        data.add(new Title("推荐歌单"));

        //歌单API
        Observable<ListResponse<Sheet>> sheets = RetrofitAPI.getInstance().requestSheetList();
        //单曲API
        Observable<ListResponse<Song>> songs = RetrofitAPI.getInstance().songs();

        sheets
                .subscribe(new HttpObserver<ListResponse<Sheet>>(getCurrentActivity(), true) {
                    @Override
                    public void onSucceeded(ListResponse<Sheet> sheetListResponse) {
                        //添加歌单数据
                        data.addAll(sheetListResponse.getData());

                        //请求单曲
                        songs.subscribe(new HttpObserver<ListResponse<Song>>() {
                            @Override
                            public void onSucceeded(ListResponse<Song> songListResponse) {
                                //添加标题
                                data.add(new Title("推荐单曲"));

                                //添加单曲数据
                                data.addAll(songListResponse.getData());

                                //设置数据到适配器
                                adapter.replaceData(data);
                            }
                        });
                    }
                });
    }

    /**
     * 请求轮播图数据
     */
    private void fetchBannerData() {
        RetrofitAPI.getInstance()
                .ads()
                .subscribe(new HttpObserver<ListResponse<Ad>>(getCurrentActivity(), true) {
                    @Override
                    public void onSucceeded(ListResponse<Ad> adListResponse) {
                        showBanner(adListResponse.getData());
                    }
                });
    }

    /**
     * 显示banner
     */
    private void showBanner(List<Ad> data) {
        //先保存，后续暂停和恢复时用来判断
        this.bannerData = data;

        //设置轮播图数据
        banner.setImages(data);
        //设置轮播时间
        banner.setDelayTime(3000);
        //显示数据，此时轮播图不滚动
        banner.start();
        //设置滚动
        banner.startAutoPlay();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (bannerData != null) {
            banner.startAutoPlay();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        banner.stopAutoPlay();
    }

    @Override
    public void OnBannerClick(int position) {
        //获取点击的广告对象
        Ad ad = bannerData.get(position);

        //使用通用的WebView界面显示
        //startActivityContainWebView(CommonToolbarActivity.class, getString(R.string.title_activity_detail), ad.getUri());
        startActivityContainWebView(CommonToolbarActivity.class, getString(R.string.title_activity_detail), "https://www.baidu.com");
    }
}
