package com.jacky.mycloudmusic.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Fragment构造不需要传入参数时的ViewPager适配器
 */
public class NoArgViewPagerAdapter extends FragmentStatePagerAdapter {
    //保存已创建的Fragment
    private Map<Integer, Fragment> fragmentMap;
    //需要适配的Fragment类
    private ArrayList<Class<?>> fragClasses;
    //页面标题
    private String[] pageTitle;

    public NoArgViewPagerAdapter(@NonNull FragmentManager fm, ArrayList<Class<?>> fragClasses) {
        super(fm);
        this.fragClasses = fragClasses;
        fragmentMap = new HashMap<>();
    }

    public NoArgViewPagerAdapter(@NonNull FragmentManager fm, ArrayList<Class<?>> fragClasses, String[] pageTitle) {
        super(fm);
        this.fragClasses = fragClasses;
        this.pageTitle = pageTitle;
        fragmentMap = new HashMap<>();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (fragmentMap.get(position) == null) {
            Fragment fragment;
            try {
                fragment = (Fragment) fragClasses.get(position).newInstance();
                fragmentMap.put(position, fragment);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }

        return fragmentMap.get(position);

    }

    @Override
    public int getCount() {
        return fragClasses.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (pageTitle == null)
            return null;
        else
            return pageTitle[position];
    }
}
