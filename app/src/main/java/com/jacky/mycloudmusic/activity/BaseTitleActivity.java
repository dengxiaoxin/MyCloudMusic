package com.jacky.mycloudmusic.activity;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.jacky.mycloudmusic.R;

import butterknife.BindView;

/**
 * 通用标题界面
 */
public class BaseTitleActivity extends BaseCommonActivity {
    /**
     * 标题控件
     */
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @Override
    protected void initViews() {
        super.initViews();

        //setStatusBarFullTransparent();
        //setFitSystemWindow(true);
        //changeStatusIconColor(false);

        //设置toolbar属性，不然菜单栏关联不到此toolbar
        setSupportActionBar(toolbar);

        //是否显示返回按钮
        if (isShowBackMenu()) {
            showBackMenu();
        }
    }

    @Override
    protected void initListeners() {
        super.initListeners();

//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
    }

    /**
     * 显示返回按钮
     */
    protected void showBackMenu() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * 是否显示返回按钮
     *
     * @return
     */
    protected boolean isShowBackMenu() {
        return true;
        //return false;
    }

    /**
     * 菜单点击了回调
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Toolbar返回按钮点击
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
