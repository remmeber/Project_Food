package com.rhg.qf.ui.activity;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import com.rhg.qf.R;
import com.rhg.qf.ui.fragment.BySellNumberFm;

import butterknife.Bind;
import butterknife.OnClick;

public class RecommendActivity extends BaseAppcompactActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int getLayoutResId() {
        return R.layout.recommend_layout;
    }


    @Override
    protected void initData() {
        toolbar.setTitle("今日推荐");
        setSupportActionBar(toolbar);
        setToolbar(toolbar);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment fm = new BySellNumberFm();
        ft.add(R.id.fm_list, fm, fm.getClass().getName());
        ft.commitAllowingStateLoss();
    }
}
