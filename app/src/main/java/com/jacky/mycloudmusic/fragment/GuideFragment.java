package com.jacky.mycloudmusic.fragment;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jacky.mycloudmusic.R;
import com.jacky.mycloudmusic.util.Constant;

/**
 * 引导界面Fragment
 */
public class GuideFragment extends BaseCommonFragment {

    private ImageView ivGuide;

    public GuideFragment() {
        // Required empty public constructor
    }

    public static GuideFragment newInstance(int id) {
        GuideFragment fragment = new GuideFragment();
        Bundle args = new Bundle();
        args.putInt(Constant.GUIDE_IMAGE_ID, id);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_guide, container, false);
    }

    @Override
    protected void initViews() {
        super.initViews();

        ivGuide = getView().findViewById(R.id.iv_guide);
    }

    @Override
    protected void initData() {
        super.initData();

        int id = getArguments().getInt(Constant.GUIDE_IMAGE_ID, -1);
        if (id == -1) {
            getActivity().finish();
            return;
        }

        ivGuide.setImageResource(id);
    }
}
