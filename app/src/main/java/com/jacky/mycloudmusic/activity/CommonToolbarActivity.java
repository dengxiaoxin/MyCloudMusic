package com.jacky.mycloudmusic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.jacky.mycloudmusic.R;
import com.jacky.mycloudmusic.fragment.LoginFragment;
import com.jacky.mycloudmusic.fragment.RegisterFragment;
import com.jacky.mycloudmusic.fragment.WebViewFragment;
import com.jacky.mycloudmusic.util.Constant;

import butterknife.BindView;

public class CommonToolbarActivity extends BaseCommonActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_toolbar);
    }

    @Override
    protected void initViews() {
        super.initViews();

        //setStatusBarFullTransparent();
        //setFitSystemWindow(true);
        changeStatusIconColor(false);

        Intent intent = this.getIntent();
        String fragmentTag = intent.getStringExtra(Constant.FRAGMENT_TAG);
        Fragment fragment = null;
        assert fragmentTag != null;
        switch (fragmentTag) {
            case Constant.LOGIN_FRAGMENT:
                toolbar.setTitle(R.string.title_login);
                fragment = new LoginFragment();
                break;
            case Constant.REGISTER_FRAGMENT:
                toolbar.setTitle(R.string.title_register);
                fragment = new RegisterFragment();
                break;
            case Constant.WEB_VIEW_FRAGMENT:
                String title = intent.getStringExtra(Constant.TITLE);
                String url = intent.getStringExtra(Constant.URL);
                toolbar.setTitle(title);
                fragment = new WebViewFragment(url);
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction trans = manager.beginTransaction();
            trans.replace(R.id.ll_fragment_container, fragment);
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
