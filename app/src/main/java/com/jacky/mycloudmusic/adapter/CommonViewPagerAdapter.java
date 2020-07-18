package com.jacky.mycloudmusic.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * 通用ViewPager适配器
 * 该适配器是在外面创建好所有的Fragment，然后导入
 */
public class CommonViewPagerAdapter extends FragmentStatePagerAdapter {
    protected List<Fragment> list;
    protected String[] pageTitle;

    public CommonViewPagerAdapter(@NonNull FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list = list;
    }

    public CommonViewPagerAdapter(@NonNull FragmentManager fm, List<Fragment> list, String[] pageTitle) {
        super(fm);
        this.list = list;
        this.pageTitle = pageTitle;
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

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (pageTitle == null)
            return null;
        else
            return pageTitle[position];
    }

    public void setData(List<Fragment> list) {
        if (list != null && list.size() > 0) {
            this.list = list;

            //通知数据改变了
            notifyDataSetChanged();
        }
    }
}
