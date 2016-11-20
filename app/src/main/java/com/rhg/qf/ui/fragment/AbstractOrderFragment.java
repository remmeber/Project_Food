package com.rhg.qf.ui.fragment;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.rhg.qf.R;
import com.rhg.qf.adapter.QFoodOrderAdapter;
import com.rhg.qf.bean.OrderUrlBean;
import com.rhg.qf.constants.AppConstants;
import com.rhg.qf.impl.RcvItemClickListener;
import com.rhg.qf.mvp.presenter.OrdersPresenter;
import com.rhg.qf.ui.activity.OrderDetailActivity;
import com.rhg.qf.utils.AccountUtil;
import com.rhg.qf.utils.DecimalUtil;
import com.rhg.qf.utils.SizeUtil;
import com.rhg.qf.widget.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * desc:订单抽象FM
 * author：remember
 * time：2016/5/28 16:42
 * email：1013773046@qq.com
 */
public abstract class AbstractOrderFragment extends BaseFragment implements RcvItemClickListener<OrderUrlBean.OrderBean> {
    @Bind(R.id.common_recycle)
    RecyclerView commonRecycle;
    @Bind(R.id.common_refresh)
    ProgressBar commonRefresh;
    @Bind(R.id.common_swipe)
    SwipeRefreshLayout commonSwipe;

    QFoodOrderAdapter qFoodOrderAdapter;
    List<OrderUrlBean.OrderBean> orderBeanList = new ArrayList<>();
    OrdersPresenter getOrdersPresenter;
    String userId;
    int style;

    public AbstractOrderFragment() {
        getOrdersPresenter = new OrdersPresenter(this);
        userId = AccountUtil.getInstance().getUserID();/*从数据库中获取*/
        style = getFmTag();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.common_swipe_recycle_layout;
    }

    @Override
    protected void initView(View view) {
        /*commonRecycle = (RecyclerView) view.findViewById(R.id.common_recycle);
        commonSwipe = (SwipeRefreshLayout) view.findViewById(R.id.common_swipe);*/
    }

    @Override
    protected void refresh() {
        commonRefresh.setVisibility(View.VISIBLE);
        getOrdersPresenter.getOrders(AppConstants.TABLE_ORDER, userId, style);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && hasFetchData) {
            refresh();
        }
    }

    @Override
    public void loadData() {
        commonRefresh.setVisibility(View.VISIBLE);
        getOrdersPresenter.getOrders(AppConstants.TABLE_ORDER, userId, style);
    }

    @Override
    protected void initData() {
        qFoodOrderAdapter = new QFoodOrderAdapter(getContext(), orderBeanList);
        qFoodOrderAdapter.setOnRcvItemClickListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        commonRecycle.setLayoutManager(linearLayoutManager);
        commonRecycle.setHasFixedSize(true);
        commonRecycle.setAdapter(qFoodOrderAdapter);
        commonRecycle.addItemDecoration(new RecycleViewDivider(getContext(),
                LinearLayoutManager.HORIZONTAL, SizeUtil.dip2px(5),
                ContextCompat.getColor(getContext(), R.color.colorGrayLight)));
        commonSwipe.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(getContext(), R.color.colorBlueNormal));
        commonSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getOrdersPresenter.getOrders(AppConstants.TABLE_ORDER, userId, style);
            }

        });
    }

    @Override
    protected void showFailed() {

    }


    @Override
    public void showSuccess(Object o) {
        if (commonSwipe.isRefreshing())
            commonSwipe.setRefreshing(false);
        qFoodOrderAdapter.setmData((List<OrderUrlBean.OrderBean>) o);
        if (commonRefresh.getVisibility() == View.VISIBLE)
            commonRefresh.setVisibility(View.GONE);
    }

    @Override
    public void onItemClickListener(View view, int position, OrderUrlBean.OrderBean item) {
        Intent _intent = new Intent(getContext(), OrderDetailActivity.class);
        _intent.putExtra(AppConstants.KEY_ORDER_ID, item.getID());
        _intent.putExtra(AppConstants.KEY_PRODUCT_PRICE, DecimalUtil.addWithScale(item.getPrice(), item.getFee(), 2));
        _intent.putExtra(AppConstants.KEY_MERCHANT_NAME, item.getRName());
        _intent.putExtra(AppConstants.KEY_ORDER_TAG, style);
        startActivity(_intent, ActivityOptionsCompat.makeScaleUpAnimation(view, (int) view.getX(), (int) view.getY(), view.getWidth(), view.getHeight()).toBundle());
    }

    protected abstract int getFmTag();


    /*@Override
    public void itemClick(View v, int position) {
//        ToastHelper.getInstance()._toast("item " + position + "is click.");
        Intent intent = new Intent(getContext(), PayActivity.class);
        startActivity(intent);
    }*/
}
