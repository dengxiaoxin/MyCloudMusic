package com.jacky.mycloudmusic.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Objects;

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

    /**
     * 引用控件，使得在Fragment里面引用代码更简洁
     */
    final <T extends View> T findViewById(@IdRes int id) {
        return Objects.requireNonNull(getView()).findViewById(id);
    }
}
