package com.rhg.qf.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.flyco.tablayout.SlidingTabLayout;
import com.rhg.qf.R;
import com.rhg.qf.adapter.QFoodVpAdapter;
import com.rhg.qf.constants.AppConstants;
import com.rhg.qf.ui.fragment.HotFoodDistanceFm;
import com.rhg.qf.ui.fragment.HotFoodRateFm;
import com.rhg.qf.ui.fragment.HotFoodSellNumberFm;
import com.rhg.qf.ui.fragment.OverallHotFoodFm;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * desc:热销单品
 * author：remember
 * time：2016/6/19 13:08
 * email：1013773046@qq.com
 */
public class HotFoodActivity extends BaseAppcompactActivity {

    @Bind(R.id.stl)
    SlidingTabLayout tlHolSell;
    @Bind(R.id.hot_sell_vp)
    ViewPager vpHotSell;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    List<Fragment> fragments = new ArrayList<>();
    String foodName;

    public HotFoodActivity() {
    }

    @Override
    public void dataReceive(Intent intent) {
        foodName = intent.getStringExtra(AppConstants.KEY_PRODUCT_NAME);
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.hot_sell_layout;
    }


    @Override
    protected void initData() {
        toolbar.setTitle(getString(R.string.tvHotFood));
        setSupportActionBar(toolbar);
        setToolbar(toolbar);

        Bundle _bundle = new Bundle();
        _bundle.putString(AppConstants.KEY_PRODUCT_NAME, foodName);
        OverallHotFoodFm overallHotFoodFm = new OverallHotFoodFm();
        overallHotFoodFm.setArguments(_bundle);
        HotFoodDistanceFm hotFoodDistanceFm = new HotFoodDistanceFm();
        hotFoodDistanceFm.setArguments(_bundle);
        HotFoodSellNumberFm hotFoodSellNumberFm = new HotFoodSellNumberFm();
        hotFoodSellNumberFm.setArguments(_bundle);
        HotFoodRateFm hotFoodRateFm = new HotFoodRateFm();
        hotFoodRateFm.setArguments(_bundle);
        fragments.add(overallHotFoodFm);
        fragments.add(hotFoodDistanceFm);
        fragments.add(hotFoodSellNumberFm);
        fragments.add(hotFoodRateFm);
        QFoodVpAdapter mAdapter = new QFoodVpAdapter(getSupportFragmentManager(), fragments,
                AppConstants.HOT_SELL_TITLES);
        vpHotSell.setAdapter(mAdapter);
        vpHotSell.setOffscreenPageLimit(3);
        tlHolSell.setViewPager(vpHotSell);
    }

    @Override
    public void rightClick() {
        Intent _intent = new Intent(this, SearchActivity.class);
        _intent.putExtra(AppConstants.KEY_SEARCH_TAG, AppConstants.KEY_HOTFOOD_SEARCH);
        _intent.putExtra(AppConstants.KEY_SEARCH_INDEX, tlHolSell.getCurrentTab());
        startActivity(_intent);
        /*TODO 可以将对象保存到realm中*/
    }


}
