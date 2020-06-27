package com.jacky.mycloudmusic.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * 通用ViewPager适配器
 */
public class CommonViewPagerAdapter extends FragmentStatePagerAdapter {
    protected List<Fragment> list;

    public CommonViewPagerAdapter(@NonNull FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list = list;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public void setData(List<Fragment> list) {
        if (list != null && list.size() > 0) {
            this.list = list;

            //通知数据改变了
            notifyDataSetChanged();
        }
    }
}
