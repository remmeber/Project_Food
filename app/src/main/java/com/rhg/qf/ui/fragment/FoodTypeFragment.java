package com.rhg.qf.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.rhg.qf.R;
import com.rhg.qf.adapter.GoodsListAdapter;
import com.rhg.qf.bean.ShopDetailUrlBean;
import com.rhg.qf.constants.AppConstants;
import com.rhg.qf.impl.RcvItemClickListener;
import com.rhg.qf.impl.RefreshListener;
import com.rhg.qf.mvp.presenter.ShopDetailPresenter;
import com.rhg.qf.ui.activity.GoodsDetailActivity;
import com.rhg.qf.widget.VerticalTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * desc:店铺详情中的商品类型fm，和{@link VerticalTabLayout}一起使用
 * author：remember
 * time：2016/5/28 16:43
 * email：1013773046@qq.com
 */
public class FoodTypeFragment extends BaseFragment implements RcvItemClickListener<ShopDetailUrlBean.ShopDetailBean> {
    //    private int tag;
    List<ShopDetailUrlBean.ShopDetailBean> shopDetailBeanList;
    GoodsListAdapter goodsListAdapter;
    ShopDetailPresenter shopDetailPresenter;
    RefreshListener refreshListener;

    String merchantId;
    String merchantName;

    @Bind(R.id.common_recycle)
    RecyclerView commonRecycle;
    @Bind(R.id.common_refresh)
    ProgressBar commonRefresh;
    @Bind(R.id.common_swipe)
    SwipeRefreshLayout commonSwipe;


    @Override
    public void receiveData(Bundle arguments) {
        merchantId = arguments.getString(AppConstants.KEY_MERCHANT_ID, "");
        merchantName = arguments.getString(AppConstants.KEY_MERCHANT_NAME, "");
    }

    @Override
    public int getLayoutResId() {
        return R.layout.common_swipe_recycle_layout;
    }

    @Override
    public void loadData() {
        /*commonRefresh.setVisibility(View.VISIBLE);
        shopDetailPresenter.getShopDetail(AppConstants.TABLE_FOOD, merchantId);*/
    }


    @Override
    protected void initData() {
        if (shopDetailBeanList == null)
            shopDetailBeanList = new ArrayList<>();
        commonRecycle.setHasFixedSize(true);
        commonRecycle.setLayoutManager(new LinearLayoutManager(getContext()));
        goodsListAdapter = new GoodsListAdapter(getContext(), shopDetailBeanList);
        goodsListAdapter.setOnGoodsItemClickListener(this);
        commonRecycle.setAdapter(goodsListAdapter);
        commonSwipe.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(getContext(), R.color.colorBlueNormal));
        commonSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                /*shopDetailPresenter.getShopDetail(AppConstants.TABLE_FOOD, merchantId);*/
                if (refreshListener != null)
                    refreshListener.load();
                else
                    new Error("refresh interface can not be null");
            }

        });
    }

    public void setRefreshListener(RefreshListener refreshListener) {
        this.refreshListener = refreshListener;
    }

    public boolean hasListener() {
        return refreshListener != null;
    }


    @Override
    protected void initView(View view) {
    }


    @Override
    protected void showFailed() {

    }

    @Override
    public void showSuccess(Object o) {
       /* shopDetailBeanList = (List<ShopDetailUrlBean.ShopDetailBean>) o;
        if (goodsListAdapter != null) {
            goodsListAdapter.setShopDetailBeanList(shopDetailBeanList);
            goodsListAdapter.notifyDataSetChanged();
        }
        if (commonSwipe.isRefreshing())
            commonSwipe.setRefreshing(false);
        if (commonRefresh.getVisibility() == View.VISIBLE)
            commonRefresh.setVisibility(View.GONE);*/
    }

    @Override
    public void onItemClickListener(View view,int position, ShopDetailUrlBean.ShopDetailBean item) {
        Intent intent = new Intent(getContext(), GoodsDetailActivity.class);
        /*intent.putExtra(AppConstants.KEY_FOOD_ID, "20160518");
        intent.putExtra(AppConstants.KEY_PRODUCT_NAME, "土豆丝");
        intent.putExtra(AppConstants.KEY_PRODUCT_PRICE, "90");*/
        intent.putExtra(AppConstants.KEY_MERCHANT_ID, merchantId);
        intent.putExtra(AppConstants.KEY_MERCHANT_NAME, merchantName);
        intent.putExtra(AppConstants.KEY_PRODUCT_ID, item.getID());
//        intent.putExtra() //todo 传递参数
        startActivityForResult(intent, 1, ActivityOptionsCompat.makeScaleUpAnimation(view,(int)view.getX(),(int)view.getY(),view.getWidth(),view.getHeight()).toBundle());
    }

    public void setShopDetailBeanList(List<ShopDetailUrlBean.ShopDetailBean> shopDetailBeanList) {
        this.shopDetailBeanList = shopDetailBeanList;
    }

    public void finishLoad() {
        if (commonSwipe.isRefreshing())
            commonSwipe.setRefreshing(false);
    }
}
