package com.jacky.mycloudmusic.activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.jacky.mycloudmusic.MainActivity;
import com.jacky.mycloudmusic.R;
import com.jacky.mycloudmusic.adapter.CommonViewPagerAdapter;
import com.jacky.mycloudmusic.fragment.GuideFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;

/**
 * 引导界面
 */
public class GuideActivity extends BaseCommonActivity {

    //引用控件
    @BindView(R.id.vp_guide)
    ViewPager vpGuide;

    @BindView(R.id.ci_guide)
    CircleIndicator ciGuide;

    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
    }

    @Override
    protected void initViews() {
        super.initViews();

        //隐藏状态栏
        hideStatusBar();

        initFragment();
        CommonViewPagerAdapter guideAdapter = new CommonViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        vpGuide.setAdapter(guideAdapter);
        //指示器与ViewPager关联
        ciGuide.setViewPager(vpGuide);
        guideAdapter.registerDataSetObserver(ciGuide.getDataSetObserver());
    }

    private void initFragment() {
        int[] guideImgId = new int[]{R.drawable.guide1, R.drawable.guide2,
                R.drawable.guide3, R.drawable.guide4, R.drawable.guide5};

        fragmentList = new ArrayList<>();
        for (int id : guideImgId) {
            Fragment fragment = GuideFragment.newInstance(id);
            fragmentList.add(fragment);
        }
    }

    @OnClick(R.id.btn_login_or_register)
    void onBtnLoginOrRegisterClick() {
        startActivityAndFinishThis(LoginOrRegisterActivity.class);
        sp.setShowGuide(false);
    }

    @OnClick(R.id.btn_enter)
    void onBtnEnterClick() {
        startActivityAndFinishThis(MainActivity.class);
        sp.setShowGuide(false);
    }
}
