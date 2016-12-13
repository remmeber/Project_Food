package com.rhg.qf.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.flyco.tablayout.SlidingTabLayout;
import com.rhg.qf.R;
import com.rhg.qf.adapter.QFoodVpAdapter;
import com.rhg.qf.constants.AppConstants;
import com.rhg.qf.impl.ToolBarClickListener;
import com.rhg.qf.ui.activity.SearchActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


/**
 * desc:所有店铺fm
 * author：remember
 * time：2016/5/28 16:47
 * email：1013773046@qq.com
 */
public class SellerFragment extends BaseFragment implements ToolBarClickListener {
    List<Fragment> fragments = new ArrayList<>();
    @Bind(R.id.all_seller_tl)
    SlidingTabLayout allSellerTl;
    @Bind(R.id.sellerViewPager)
    ViewPager sellerViewPager;


    //-----------------根据需求创建相应的presenter----------------------------------------------------

    //----------------------------------------------------------------------------------------------

    public SellerFragment() {

        fragments.add(new BySellNumberFm());
        fragments.add(new ByDistanceFm());
        fragments.add(new ByRateFm());
    }


    @Override
    public int getLayoutResId() {
        return R.layout.all_shop_home_layout;
    }

    @Override
    protected void initView(View view) {
    }

    @Override
    protected void initData() {
        QFoodVpAdapter qFoodVpAdapter = new QFoodVpAdapter(getChildFragmentManager(), fragments,
                AppConstants.SELL_TITLES);

        sellerViewPager.setAdapter(qFoodVpAdapter);
        sellerViewPager.setOffscreenPageLimit(2);
        allSellerTl.setViewPager(sellerViewPager);
    }

    @Override
    protected void showFailed() {

    }

    @Override
    public void showSuccess(Object o) {

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null)
            sellerViewPager.setCurrentItem(savedInstanceState.getInt("position"));
    }

    private void doSearch(int position) {
        Intent _intent = new Intent(getActivity(), SearchActivity.class);
        _intent.putExtra(AppConstants.KEY_SEARCH_TAG, AppConstants.KEY_RESTAURANT_SEARCH);
        _intent.putExtra(AppConstants.KEY_SEARCH_INDEX, position + 1);
        startActivity(_intent);
    }

    @Override
    public void r1_click(String title) {
        Log.i("RHG", "点击的内容为：" + title);
        doSearch(allSellerTl.getCurrentTab());
    }
}
