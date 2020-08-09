package com.jacky.mycloudmusic.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jacky.mycloudmusic.R;
import com.jacky.mycloudmusic.activity.VideoDetailActivity;
import com.jacky.mycloudmusic.adapter.VideoRvAdapter;
import com.jacky.mycloudmusic.domain.Video;
import com.jacky.mycloudmusic.domain.event.PageSelectedEvent;
import com.jacky.mycloudmusic.domain.response.ListResponse;
import com.jacky.mycloudmusic.listener.HttpObserver;
import com.jacky.mycloudmusic.networkapi.RetrofitAPI;
import com.jacky.mycloudmusic.util.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * 视频界面
 */
public class VideoFragment extends BaseCommonFragment {

    private static final String TAG = "======VideoFragment";
    @BindView(R.id.rv)
    RecyclerView rv;

    private VideoRvAdapter adapter;

    public VideoFragment() {
        // Required empty public constructor
    }

    /**
     * 构造方法
     * <p>
     * 固定写法
     */
    public static VideoFragment newInstance() {
        VideoFragment fragment = new VideoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video, container, false);
    }

    @Override
    protected void initViews() {
        super.initViews();

        rv.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getCurrentActivity());
        rv.setLayoutManager(layoutManager);
        adapter = new VideoRvAdapter(R.layout.item_video);
        rv.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        EventBus.getDefault().register(this);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //获取点击的视频
                Video data = (Video) adapter.getItem(position);

                startActivityExtraId(VideoDetailActivity.class, data.getId());
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPageSelectedEvent(PageSelectedEvent event) {
        if (event.getPosition() == 1 && adapter.getData().size() == 0) {
            fetch();
        }
    }

    private void fetch() {
        RetrofitAPI.getInstance()
                .videos()
                .subscribe(new HttpObserver<ListResponse<Video>>(getCurrentActivity(), true) {
                               @Override
                               public void onSucceeded(ListResponse<Video> videoListResponse) {
                                   if (videoListResponse.getData() != null && videoListResponse.getData().size() > 0) {
                                       LogUtil.d(TAG, "get videos:" + videoListResponse.getData().size());

                                       adapter.replaceData(videoListResponse.getData());
                                   }
                               }
                           }
                );
    }
}
