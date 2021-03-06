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
import com.rhg.qf.adapter.MainAdapter;
import com.rhg.qf.adapter.viewHolder.GoodsDetailViewHolder;
import com.rhg.qf.bean.CommonListModel;
import com.rhg.qf.bean.InflateModel;
import com.rhg.qf.bean.ShopDetailUrlBean;
import com.rhg.qf.constants.AppConstants;
import com.rhg.qf.impl.OnItemClickListener;
import com.rhg.qf.impl.RefreshListener;
import com.rhg.qf.mvp.presenter.ShopDetailPresenter;
import com.rhg.qf.ui.activity.GoodsDetailActivity;
import com.rhg.qf.widget.VerticalTabLayout;

import java.util.List;

import butterknife.Bind;

/**
 * desc:店铺详情中的商品类型fm，和{@link VerticalTabLayout}一起使用
 * author：remember
 * time：2016/5/28 16:43
 * email：1013773046@qq.com
 */
public class FoodTypeFragment extends BaseFragment implements OnItemClickListener<CommonListModel<ShopDetailUrlBean.ShopDetailBean>> {
    CommonListModel<ShopDetailUrlBean.ShopDetailBean> shopDetailBeanList;
    MainAdapter<CommonListModel<ShopDetailUrlBean.ShopDetailBean>> goodsListAdapter;
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
            shopDetailBeanList = new CommonListModel<>();
        commonRecycle.setHasFixedSize(true);
        commonRecycle.setLayoutManager(new LinearLayoutManager(getContext()));
        goodsListAdapter = new MainAdapter<>(
                new InflateModel(new Class[]{GoodsDetailViewHolder.class, View.class}, new Object[]{R.layout.item_goods_layout}),
                shopDetailBeanList,
                this
        );
        commonRecycle.setAdapter(goodsListAdapter);
        commonSwipe.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(getContext(), R.color.colorBlueNormal));
        commonSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
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
    }

    public void setShopDetailBeanList(CommonListModel<ShopDetailUrlBean.ShopDetailBean> shopDetailBeanList) {
        this.shopDetailBeanList = shopDetailBeanList;
    }

    public void finishLoad() {
        if (commonSwipe.isRefreshing())
            commonSwipe.setRefreshing(false);
    }


    @Override
    public void onItemClick(View view, int position, CommonListModel<ShopDetailUrlBean.ShopDetailBean> item) {
        Intent intent = new Intent(getContext(), GoodsDetailActivity.class);
        intent.putExtra(AppConstants.KEY_MERCHANT_ID, merchantId);
        intent.putExtra(AppConstants.KEY_MERCHANT_NAME, merchantName);
        intent.putExtra(AppConstants.KEY_PRODUCT_ID, item.getEntity().get(position).getID());
        startActivityForResult(intent, 1, ActivityOptionsCompat.makeScaleUpAnimation(view, (int) view.getX(), (int) view.getY(), view.getWidth(), view.getHeight()).toBundle());

    }

    @Override
    public void onItemLongClick(View view, int position, CommonListModel<ShopDetailUrlBean.ShopDetailBean> item) {

    }
}
