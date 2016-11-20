package com.rhg.qf.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.rhg.qf.R;
import com.rhg.qf.bean.ShopDetailLocalModel;
import com.rhg.qf.bean.ShopDetailUrlBean;
import com.rhg.qf.constants.AppConstants;
import com.rhg.qf.impl.RefreshListener;
import com.rhg.qf.mvp.presenter.ShopDetailPresenter;
import com.rhg.qf.ui.FragmentController;
import com.rhg.qf.widget.VerticalTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * desc:店铺详情的食物类型fm，里面{@link FoodTypeFragment}展示店铺中的商品
 * author：remember
 * time：2016/5/28 16:48
 * email：1013773046@qq.com
 */
public class ShopDetailFoodFragment extends BaseFragment implements RefreshListener {
    @Bind(R.id.vt_selector)
    VerticalTabLayout verticalTabLayout;

    FragmentController fragmentController;
    List<Fragment> fragments = new ArrayList<>();


    ShopDetailLocalModel shopDetailLocalModel;
    ShopDetailPresenter shopDetailPresenter;

    String merchantId;
    String merchantName;

    public ShopDetailFoodFragment() {
        shopDetailPresenter = new ShopDetailPresenter(this);
    }

    @Override
    public void receiveData(Bundle arguments) {
        merchantId = arguments.getString(AppConstants.KEY_MERCHANT_ID);
        merchantName = arguments.getString(AppConstants.KEY_MERCHANT_NAME);
    }

    @Override
    public void loadData() {
        shopDetailPresenter.getShopDetail(AppConstants.TABLE_FOOD, merchantId);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.shop_detail_fm1_content;
    }

    @Override
    protected void initView(View view) {
    }


    @Override
    protected void initData() {
        fragmentController = new FragmentController(getChildFragmentManager(), fragments, R.id.fl_shop_detail);
        verticalTabLayout.setOnVerticalTabClickListener(new VerticalTabLayout.VerticalTabClickListener() {
            @Override
            public void onVerticalTabClick(int position) {
                fragmentController.showFragment(position);
            }
        });
    }


    @Override
    protected void showFailed() {
    }

    @Override
    public void showSuccess(Object o) {
        if (o instanceof ShopDetailLocalModel) {
            shopDetailLocalModel = (ShopDetailLocalModel) o;
            verticalTabLayout.setTitles(shopDetailLocalModel.getVarietys());
            int start = -1;
            int end = -1;
            FoodTypeFragment fragment;
            for (int i = 0; i < shopDetailLocalModel.getVarietys().size(); i++) {
                if (i >= fragments.size()) {
                    Bundle bundle = new Bundle();
                    bundle.putString(AppConstants.KEY_MERCHANT_ID, merchantId);
                    bundle.putString(AppConstants.KEY_MERCHANT_NAME, merchantName);
                    fragment = new FoodTypeFragment();
                    fragment.setArguments(bundle);
                } else
                    fragment = ((FoodTypeFragment) fragments.get(i));
                if (!fragment.hasListener())
                    fragment.setRefreshListener(this);
                List<ShopDetailUrlBean.ShopDetailBean> _shopDetailBeanList = new ArrayList<>();
                for (int j = start + 1; j < shopDetailLocalModel.getShopDetailBean().size(); j++) {
                    if (shopDetailLocalModel.getShopDetailBean().get(j).getVariety()
                            .equals(shopDetailLocalModel.getVarietys().get(i))) {
                        _shopDetailBeanList.add(shopDetailLocalModel.getShopDetailBean().get(j));
                        end++;
                    } else {
                        start = end;
                        break;
                    }
                }
                fragment.setShopDetailBeanList(_shopDetailBeanList);
                if (i >= fragments.size())
                    fragmentController.addFm(fragment);
            }
            ((FoodTypeFragment) fragmentController.getCurrentFM()).finishLoad();
        }

    }

    /*页面刷新调用*/
    @Override
    public void load() {
        shopDetailPresenter.getShopDetail(AppConstants.TABLE_FOOD, merchantId);
    }
}
