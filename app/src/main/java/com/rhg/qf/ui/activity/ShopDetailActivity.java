package com.rhg.qf.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.rhg.qf.R;
import com.rhg.qf.adapter.QFoodVpAdapter;
import com.rhg.qf.constants.AppConstants;
import com.rhg.qf.ui.fragment.ShopDetailFoodFragment;
import com.rhg.qf.ui.fragment.ShopDetailFragment;
import com.rhg.qf.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * desc:店铺详情页面
 * author：remember
 * time：2016/5/28 16:15
 * email：1013773046@qq.com
 */
public class ShopDetailActivity extends BaseAppcompactActivity {

    @Bind(R.id.iv_shop_detail_logo)
    ImageView ivShopLogo;
    @Bind(R.id.tv_shop_detail_name)
    TextView tvShopName;
    @Bind(R.id.stl_shop_detail)
    SlidingTabLayout slidingTabLayout;
    @Bind(R.id.vp_shop_detail)
    ViewPager viewPager;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    String shopLogoUrl;
    String merchantName;
    String merchantId;
    Bundle bundle;

    @Override
    public void dataReceive(Intent intent) {
        if (intent != null) {
            bundle = intent.getExtras();
            merchantName = bundle.getString(AppConstants.KEY_MERCHANT_NAME);
            merchantId = bundle.getString(AppConstants.KEY_MERCHANT_ID, "");
            shopLogoUrl = bundle.getString(AppConstants.KEY_MERCHANT_LOGO);
        } else {
            merchantName = "";
            merchantId = "";
            shopLogoUrl = "";
        }
    }

    @Override
    public void hideNavigationBar(View decorView) {
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);//隐藏屏幕下方的虚拟导航栏
    }

    @Override()
    protected int getLayoutResId() {
        return R.layout.shop_detail_layout;
    }


    @Override
    protected void initData() {

        toolbar.setTitle(getString(R.string.tvShopDetail));
        setSupportActionBar(toolbar);
        setToolbar(toolbar);

        ImageUtils.showImage(shopLogoUrl, ivShopLogo);

        Fragment fragment;
        Bundle bundle = new Bundle();
        List<Fragment> fragments = new ArrayList<>();
        fragment = new ShopDetailFoodFragment();
        bundle.putString(AppConstants.KEY_MERCHANT_ID, merchantId);
        bundle.putString(AppConstants.KEY_MERCHANT_NAME, merchantName);
        fragment.setArguments(bundle);
        fragments.add(fragment);
        fragment = new ShopDetailFragment();
        fragment.setArguments(bundle);
        fragments.add(fragment);
        QFoodVpAdapter qFoodVpAdapter = new QFoodVpAdapter(getSupportFragmentManager(), fragments,
                AppConstants.SHOP_DETAIL_TITLES);
        viewPager.setAdapter(qFoodVpAdapter);
        slidingTabLayout.setViewPager(viewPager);
    }

    @Override
    public void menuCreated(Menu menu) {
        menu.getItem(0).setVisible(false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == AppConstants.BACK_WITH_DELETE) {
            setResult(resultCode, data);//AppConstants.KEY_BACK_2_SHOPPING_CART
            finish();
            return;
        }
        if (resultCode == AppConstants.BACK_WITHOUT_DATA) {
            /*setResult(resultCode);
            finish();*/
        }
    }

    @Override
    public void onBackPressed() {
        setResult(AppConstants.BACK_WITHOUT_DATA);
        bundle = null;
        super.onBackPressed();
    }

    public void setMerchantName(String merchantName) {
        tvShopName.setText(merchantName);
    }
}
