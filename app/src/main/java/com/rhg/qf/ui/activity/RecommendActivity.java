package com.rhg.qf.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rhg.qf.R;
import com.rhg.qf.ui.fragment.BySellNumberFm;

import butterknife.Bind;
import butterknife.OnClick;

public class RecommendActivity extends BaseFragmentActivity {

    @Bind(R.id.tb_left_iv)
    ImageView tbLeftIv;
    @Bind(R.id.tb_right_ll)
    LinearLayout tbRightLl;
    @Bind(R.id.tb_center_tv)
    TextView tbCenterTv;
    @Bind(R.id.fl_tab)
    FrameLayout flTab;

    @Override
    protected int getLayoutResId() {
        return R.layout.recommend_layout;
    }

    @Override
    protected void initView(View view) {
        tbLeftIv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_chevron_left_black));
        tbCenterTv.setText("今日推荐");
        flTab.setBackgroundColor(ContextCompat.getColor(this, R.color.colorBlueNormal));
        tbRightLl.setVisibility(View.GONE);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment fm = new BySellNumberFm();
        ft.add(R.id.fm_list, fm, fm.getClass().getName());
        ft.commitAllowingStateLoss();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void showSuccess(Object s) {

    }

    @Override
    protected void showError(Object s) {

    }


    @OnClick(R.id.tb_left_iv)
    public void onClick() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            finishAfterTransition();
        else finish();
    }
}
