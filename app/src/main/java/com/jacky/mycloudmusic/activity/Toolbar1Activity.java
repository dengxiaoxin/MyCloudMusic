package com.jacky.mycloudmusic.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jacky.mycloudmusic.R;
import com.jacky.mycloudmusic.fragment.LoginFragment;
import com.jacky.mycloudmusic.fragment.RegisterFragment;
import com.jacky.mycloudmusic.util.Constant;
import com.jacky.mycloudmusic.util.LogUtil;

import butterknife.BindView;

public class Toolbar1Activity extends BaseCommonActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar1);
    }

    @Override
    protected void initViews() {
        super.initViews();

        //setStatusBarFullTransparent();
        //setFitSystemWindow(true);
        changeStatusIconColor(false);

        Intent intent = this.getIntent();
        String fragmentTag = intent.getStringExtra("fragmentTag");
        Fragment fragment = null;
        assert fragmentTag != null;
        switch (fragmentTag) {
            case Constant.LOGIN_FRAGMENT:
                toolbar.setTitle(Constant.LOGIN_FRAGMENT_TITLE);
                fragment = new LoginFragment();
                break;
            case Constant.REGISTER_FRAGMENT:
                toolbar.setTitle(Constant.REGISTER_FRAGMENT_TITLE);
                fragment = new RegisterFragment();
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction trans = manager.beginTransaction();
            trans.replace(R.id.ll_fragment_container1, fragment);
            trans.commit();
        }
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
