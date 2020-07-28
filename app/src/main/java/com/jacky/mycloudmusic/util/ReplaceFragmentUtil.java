package com.jacky.mycloudmusic.util;

import android.content.Intent;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.jacky.mycloudmusic.R;
import com.jacky.mycloudmusic.activity.BaseCommonActivity;
import com.jacky.mycloudmusic.fragment.CommentFragment;
import com.jacky.mycloudmusic.fragment.LoginFragment;
import com.jacky.mycloudmusic.fragment.RegisterFragment;
import com.jacky.mycloudmusic.fragment.SheetDetailFragment;
import com.jacky.mycloudmusic.fragment.SimplePlayerFragment;
import com.jacky.mycloudmusic.fragment.UserDetailFragment;
import com.jacky.mycloudmusic.fragment.WebViewFragment;

/**
 * 替换Fragment工具类
 * 目前新增一个Fragment,需要5个文件中设置：
 * Fragment的自身类和XML文件，标记在Constant文件中，标题在strings文件中，在当前文件中创建与替换
 */
public class ReplaceFragmentUtil {

    /**
     * 在activity中指定的位置替换为需要的Fragment
     *
     * @param activity 要替换Fragment的activity
     */
    public static void replaceFragment(BaseCommonActivity activity) {

        Intent intent = activity.getIntent();
        Toolbar toolbar = activity.findViewById(R.id.toolbar);

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
            case Constant.SIMPLE_PLAYER_FRAGMENT:
                toolbar.setTitle(R.string.title_simple_player);
                fragment = SimplePlayerFragment.newInstance();
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager manager = activity.getSupportFragmentManager();
            FragmentTransaction trans = manager.beginTransaction();
            trans.replace(R.id.ll_fragment_container, fragment);
            trans.commit();
        }
    }
}
