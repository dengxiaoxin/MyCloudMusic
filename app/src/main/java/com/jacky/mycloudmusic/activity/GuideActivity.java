package com.jacky.mycloudmusic.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.jacky.mycloudmusic.R;
import com.jacky.mycloudmusic.adapter.CommonViewPagerAdapter;
import com.jacky.mycloudmusic.fragment.GuideFragment;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

/**
 * 引导界面
 */
public class GuideActivity extends BaseCommonActivity implements View.OnClickListener {
    private Button btnLoginOrRegister;
    private Button btnEnter;
    private ViewPager vpGuide;
    private List<Fragment> fragmentList;
    private CircleIndicator ciGuide;

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

        btnLoginOrRegister = findViewById(R.id.btn_login_or_register);
        btnEnter = findViewById(R.id.btn_enter);

        vpGuide = findViewById(R.id.vp_guide);
        ciGuide = findViewById(R.id.ci_guide);
        initFragment();
        CommonViewPagerAdapter guideAdapter = new CommonViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        vpGuide.setAdapter(guideAdapter);
        //指示器与ViewPager关联
        ciGuide.setViewPager(vpGuide);
        guideAdapter.registerDataSetObserver(ciGuide.getDataSetObserver());
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        btnLoginOrRegister.setOnClickListener(this);
        btnEnter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login_or_register:
                startActivityAndFinishThis(LoginOrRegisterActivity.class);
                sp.setShowGuide(false);
                break;
            case R.id.btn_enter:
                startActivityAndFinishThis(MainActivity.class);
                sp.setShowGuide(false);
                break;
            default:
                break;
        }
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
}
