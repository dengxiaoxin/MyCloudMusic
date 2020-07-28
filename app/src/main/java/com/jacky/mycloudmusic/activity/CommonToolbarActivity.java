package com.jacky.mycloudmusic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.jacky.mycloudmusic.R;
import com.jacky.mycloudmusic.util.Constant;
import com.jacky.mycloudmusic.util.LogUtil;
import com.jacky.mycloudmusic.util.ReplaceFragmentUtil;

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

        //替换Fragment
        ReplaceFragmentUtil.replaceFragment(this);

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
