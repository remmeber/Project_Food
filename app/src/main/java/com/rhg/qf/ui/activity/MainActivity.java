package com.rhg.qf.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.rhg.qf.R;
import com.rhg.qf.constants.AppConstants;
import com.rhg.qf.mvp.view.BaseView;
import com.rhg.qf.ui.FragmentController;
import com.rhg.qf.ui.fragment.HomeFragment;
import com.rhg.qf.ui.fragment.MyFragment;
import com.rhg.qf.ui.fragment.SellerFragment;
import com.rhg.qf.ui.fragment.ShoppingCartFragment;
import com.rhg.qf.update.Updater;
import com.rhg.qf.utils.ToastHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseAppcompactActivity implements BaseView {
    FragmentController fragmentController;
    long first = 0L;

    @Bind(R.id.bottom_navigation)
    BottomNavigationBar bottomNavigation;


    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        fragmentController.getCurrentFM().onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void hideNavigationBar(View decorView) {
//        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
    }

    @Override
    protected void initData() {
        bottomNavigation.setMode(BottomNavigationBar.MODE_CLASSIC);
        bottomNavigation.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigation
                .setActiveColor(R.color.colorBlueNormal)
                .setInActiveColor(R.color.colorInActive)
                .setBarBackgroundColor(R.color.colorBackground);
        bottomNavigation.addItem(new BottomNavigationItem(R.drawable.ic_home, R.string.Home))
                .addItem(new BottomNavigationItem(R.drawable.ic_shop, R.string.Merchant))
                .addItem(new BottomNavigationItem(R.drawable.ic_user, R.string.User))
                .addItem(new BottomNavigationItem(R.drawable.ic_shopping_cart_black, R.string.shoppingCart))
                .initialise();
        bottomNavigation.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            //当item被选中状态
            @Override
            public void onTabSelected(int position) {
                fragmentController.showFragment(position);
            }

            //当item不被选中状态
            @Override
            public void onTabUnselected(int position) {
            }

            //当item再次被选中状态
            @Override
            public void onTabReselected(int position) {
            }
        });
        List<Fragment> fragments = new ArrayList<>();
        HomeFragment homeFragment = new HomeFragment();
        fragments.add(homeFragment);
        fragments.add(new SellerFragment());
        fragments.add(new MyFragment());
        fragments.add(new ShoppingCartFragment());
        fragmentController = new FragmentController(getSupportFragmentManager(),
                fragments, R.id.content_fragment);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == AppConstants.BACK_WITH_DELETE) {//// TODO: 商品详情返回购物车
            bottomNavigation.selectTab(data.getExtras().getInt(AppConstants.KEY_DELETE, 0), true);
            return;
        }
        fragmentController.getCurrentFM().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onBackPressed() {
        long second = System.currentTimeMillis();
        if (second - first > 1500) {
            ToastHelper.getInstance().displayToastWithQuickClose("再按一次退出");
            first = second;
        } else {
            super.onBackPressed();
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Updater.getInstance().check(this);
    }
}