package com.rhg.qf.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.rhg.qf.R;
import com.rhg.qf.adapter.QFoodVpAdapter;
import com.rhg.qf.constants.AppConstants;
import com.rhg.qf.ui.fragment.OrderCompleteFm;
import com.rhg.qf.ui.fragment.OrderDeliveringFm;
import com.rhg.qf.ui.fragment.OrderDrawbackFm;
import com.rhg.qf.ui.fragment.OrderUnPaidFM;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * desc:订单页面
 * author：remember
 * time：2016/5/28 16:14
 * email：1013773046@qq.com
 */
public class OrderListActivity extends BaseFragmentActivity {
    @Bind(R.id.tb_center_tv)
    TextView tbCenterTv;
    @Bind(R.id.tb_left_iv)
    ImageView tbLeftIv;
    @Bind(R.id.fl_tab)
    FrameLayout flTab;
    @Bind(R.id.stl_myorder)
    SlidingTabLayout stlMyorder;
    @Bind(R.id.vp_myorder)
    ViewPager vpMyorder;

    int vpFlag = -1;

    @Override
    protected int getLayoutResId() {
        return R.layout.myorder_layout;
    }

    @Override
    public void dataReceive(Intent intent) {
        if (intent != null)
            vpFlag = intent.getIntExtra(AppConstants.KEY_ORDER_TAG, -1);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        tbLeftIv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_chevron_left_black));
        flTab.setBackgroundResource(R.color.colorBlueNormal);
        tbCenterTv.setText(getResources().getString(R.string.myOrder));
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new OrderUnPaidFM());
        fragments.add(new OrderDeliveringFm());
        fragments.add(new OrderCompleteFm());
        fragments.add(new OrderDrawbackFm());
        QFoodVpAdapter qFoodVpAdapter = new QFoodVpAdapter(getSupportFragmentManager(), fragments,
                AppConstants.ORDER_TITLES);
        vpMyorder.setAdapter(qFoodVpAdapter);
        vpMyorder.setOffscreenPageLimit(3);
        vpMyorder.setCurrentItem(vpFlag, false);
        stlMyorder.setViewPager(vpMyorder);

    }

    @Override
    protected void initView(View view) {
    }

    @Override
    protected void showSuccess(Object s) {

    }

    @Override
    protected void showError(Object s) {

    }

    @OnClick(R.id.tb_left_iv)
    public void onClick() {
        finish();
    }
}
