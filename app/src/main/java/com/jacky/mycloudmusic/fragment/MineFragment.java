package com.jacky.mycloudmusic.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.jacky.mycloudmusic.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends Fragment {

    public MineFragment() {
        // Required empty public constructor
    }

    /**
     * 构造方法
     * <p>
     * 固定写法
     */
    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }
}
