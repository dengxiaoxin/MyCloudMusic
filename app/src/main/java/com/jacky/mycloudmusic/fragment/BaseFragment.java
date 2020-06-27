package com.jacky.mycloudmusic.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jacky.mycloudmusic.R;

/**
 * 所有Fragment通用父类
 */
public class BaseFragment extends Fragment {

    /**
     * 找控件
     */
    protected void initViews(){

    }

    /**
     * 设置数据
     */
    protected void initData(){

    }

    /**
     * 绑定监听器
     */
    protected void initListeners() {

    }

    /**
     * onCreateView完成后
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews();
        initData();
        initListeners();
    }
}
