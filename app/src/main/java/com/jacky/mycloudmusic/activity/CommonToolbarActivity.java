package com.jacky.mycloudmusic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.jacky.mycloudmusic.R;
import com.jacky.mycloudmusic.fragment.CommentFragment;
import com.jacky.mycloudmusic.fragment.LoginFragment;
import com.jacky.mycloudmusic.fragment.RegisterFragment;
import com.jacky.mycloudmusic.fragment.SheetDetailFragment;
import com.jacky.mycloudmusic.fragment.UserDetailFragment;
import com.jacky.mycloudmusic.fragment.WebViewFragment;
import com.jacky.mycloudmusic.util.Constant;
import com.jacky.mycloudmusic.util.LogUtil;

import butterknife.BindView;

public class CommonToolbarActivity extends BaseCommonActivity {

    private static final String TAG = "======CommonToolbarActivity";
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

        //设置toolbar属性，不然菜单栏关联不到此toolbar
        setSupportActionBar(toolbar);

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
            case Constant.SHEET_DETAIL_FRAGMENT:
                toolbar.setTitle(R.string.sheet_detail);
                String sheetId = intent.getStringExtra(Constant.ID);
                fragment = SheetDetailFragment.newInstance(sheetId);
                break;
            case Constant.COMMENT_FRAGMENT:
                toolbar.setTitle(R.string.title_comment);
                String id = intent.getStringExtra(Constant.ID);
                fragment = CommentFragment.newInstance(id);
                break;
            case Constant.USER_DETAIL_FRAGMENT:
                toolbar.setTitle(R.string.title_user_detail);
                String userId = intent.getStringExtra(Constant.ID);
                fragment = UserDetailFragment.newInstance(userId);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Intent intent = this.getIntent();
        String fragmentTag = intent.getStringExtra(Constant.FRAGMENT_TAG);
        assert fragmentTag != null;
        if (fragmentTag.equals(Constant.SHEET_DETAIL_FRAGMENT)) {
            getMenuInflater().inflate(R.menu.menu_sheet_detail, menu);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (R.id.action_search == id) {
            LogUtil.d(TAG, "点击搜索按钮");
            return true;
        } else if (R.id.action_sort == id) {
            LogUtil.d(TAG, "点击排序按钮");
            return true;
        } else if (R.id.action_report == id) {
            LogUtil.d(TAG, "点击举报按钮");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
