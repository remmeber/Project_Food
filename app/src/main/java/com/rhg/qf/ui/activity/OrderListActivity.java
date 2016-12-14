package com.rhg.qf.ui.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

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

/**
 * desc:订单页面
 * author：remember
 * time：2016/5/28 16:14
 * email：1013773046@qq.com
 */
public class OrderListActivity extends BaseAppcompactActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.stl)
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
    protected void initData() {
        toolbar.setTitle(getResources().getString(R.string.myOrder));
        setSupportActionBar(toolbar);
        setToolbar(toolbar);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new OrderUnPaidFM());
        fragments.add(new OrderDeliveringFm());
        fragments.add(new OrderCompleteFm());
        fragments.add(new OrderDrawbackFm());
        QFoodVpAdapter qFoodVpAdapter = new QFoodVpAdapter(getSupportFragmentManager(), fragments,
                AppConstants.ORDER_TITLES);
        vpMyorder.setAdapter(qFoodVpAdapter);
        vpMyorder.setOffscreenPageLimit(3);
        stlMyorder.setViewPager(vpMyorder);
        stlMyorder.setCurrentTab(vpFlag);
        stlMyorder.notifyDataSetChanged();
    }

    @Override
    public void menuCreated(Menu menu) {
        menu.getItem(0).setVisible(false);
    }
}
