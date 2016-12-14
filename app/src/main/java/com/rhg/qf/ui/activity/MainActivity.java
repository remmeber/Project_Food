package com.rhg.qf.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.rhg.qf.R;
import com.rhg.qf.constants.AppConstants;
import com.rhg.qf.impl.ToolBarClickListener;
import com.rhg.qf.mvp.view.BaseView;
import com.rhg.qf.ui.FragmentController;
import com.rhg.qf.ui.fragment.HomeFragment;
import com.rhg.qf.ui.fragment.MyFragment;
import com.rhg.qf.ui.fragment.SellerFragment;
import com.rhg.qf.ui.fragment.ShoppingCartFragment;
import com.rhg.qf.utils.ToastHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class MainActivity extends BaseAppcompactActivity implements BaseView {
    private final static int[] NAV_STRING = new int[]{R.string.Home, R.string.Merchant,
            R.string.User, R.string.shoppingCart};
    FragmentController fragmentController;
    long first = 0L;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.bottom_navigation)
    BottomNavigationBar bottomNavigation;


    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }


    @Override
    protected void initData() {
        toolbar.setNavigationIcon(null);
        toolbar.setTitle("家家美食");
        setSupportActionBar(toolbar);

        bottomNavigation.setMode(BottomNavigationBar.MODE_CLASSIC);
        bottomNavigation.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigation
                .setActiveColor(R.color.colorBlueNormal)
                .setInActiveColor(R.color.colorInActive)
                .setBarBackgroundColor(R.color.colorBackground);
        bottomNavigation.addItem(new BottomNavigationItem(R.drawable.ic_home, NAV_STRING[0]))
                .addItem(new BottomNavigationItem(R.drawable.ic_shop, NAV_STRING[1]))
                .addItem(new BottomNavigationItem(R.drawable.ic_user, NAV_STRING[2]))
                .addItem(new BottomNavigationItem(R.drawable.ic_shopping_cart_black, NAV_STRING[3]))
                .initialise();
        bottomNavigation.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            //当item被选中状态
            @Override
            public void onTabSelected(int position) {
                if (position == AppConstants.TypeHome || position == AppConstants.TypeSeller) {
                    toolbar.getMenu().getItem(0).setVisible(true);
                } else {
                    toolbar.getMenu().getItem(0).setVisible(false);
                }
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

        //-----------------------------------------------------------------------------------------
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == AppConstants.BACK_WITH_DELETE) {//// TODO: 商品详情返回购物车
            bottomNavigation.selectTab(data.getExtras().getInt(AppConstants.KEY_DELETE, 0), true);
            return;
        }
        fragmentController.getCurrentFM().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void rightClick() {
        ((ToolBarClickListener) fragmentController.getCurrentFM()).r1_click(toolbar.getMenu().
                getItem(0).getTitle().toString());
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
}