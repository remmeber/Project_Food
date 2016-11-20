package com.rhg.qf.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.rhg.qf.ui.activity.SearchActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * desc:所有店铺fm
 * author：remember
 * time：2016/5/28 16:47
 * email：1013773046@qq.com
 */
public class SellerFragment extends BaseFragment {
    List<Fragment> fragments = new ArrayList<>();

    @Bind(R.id.tb_center_tv)
    TextView tbCenterTv;
    @Bind(R.id.tb_right_iv)
    ImageView tbRightIv;
    @Bind(R.id.fl_tab)
    FrameLayout flTab;
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
        tbCenterTv.setText(getResources().getString(R.string.allStore));
        flTab.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorBlueNormal));
        tbRightIv.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_search_black));
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

    @OnClick(R.id.tb_right_iv)
    public void onClick() {
        doSearch(allSellerTl.getCurrentTab());
    }

    private void doSearch(int position) {
        Intent _intent = new Intent(getActivity(), SearchActivity.class);
        _intent.putExtra(AppConstants.KEY_SEARCH_TAG, AppConstants.KEY_RESTAURANT_SEARCH);
        _intent.putExtra(AppConstants.KEY_SEARCH_INDEX, position + 1);
        startActivity(_intent);
    }
}
