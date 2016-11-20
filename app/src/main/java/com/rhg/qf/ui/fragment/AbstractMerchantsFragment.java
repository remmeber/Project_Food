package com.rhg.qf.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.rhg.qf.R;
import com.rhg.qf.adapter.QFoodMerchantAdapter;
import com.rhg.qf.bean.MerchantUrlBean;
import com.rhg.qf.constants.AppConstants;
import com.rhg.qf.impl.RcvItemClickListener;
import com.rhg.qf.mvp.presenter.MerchantsPresenter;
import com.rhg.qf.ui.activity.ShopDetailActivity;
import com.rhg.qf.utils.SizeUtil;
import com.rhg.qf.widget.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


/**
 * desc:所有店铺的按销量
 * author：remember
 * time：2016/5/28 16:42
 * email：1013773046@qq.com
 */
public abstract class AbstractMerchantsFragment extends BaseFragment implements RcvItemClickListener<MerchantUrlBean.MerchantBean> {
    @Bind(R.id.common_recycle)
    RecyclerView commonRecycle;
    @Bind(R.id.common_refresh)
    ProgressBar commonRefresh;
    @Bind(R.id.common_swipe)
    SwipeRefreshLayout commonSwipe;

    List<MerchantUrlBean.MerchantBean> dataBySellNumberModels = new ArrayList<>();
    MerchantsPresenter getMerchantsOrderBySellNumberPresenter;
    QFoodMerchantAdapter qFoodMerchantAdapter;
    int merchantsType;

    public AbstractMerchantsFragment() {
        merchantsType = getMerchantsFmType();
        getMerchantsOrderBySellNumberPresenter = new MerchantsPresenter(this);
    }

    public void setContext(Context context) {
        if (qFoodMerchantAdapter != null)
            qFoodMerchantAdapter.setContext(context);
    }

    protected abstract int getMerchantsFmType();

    @Override
    public int getLayoutResId() {
        return R.layout.common_swipe_recycle_layout;
    }


    @Override
    protected void initData() {
        commonRecycle.setHasFixedSize(true);
        commonRecycle.setLayoutManager(new LinearLayoutManager(getContext()));
        qFoodMerchantAdapter = new QFoodMerchantAdapter(getContext(), dataBySellNumberModels);
        qFoodMerchantAdapter.setOnRcvItemClickListener(this);
        commonRecycle.setAdapter(qFoodMerchantAdapter);
        commonRecycle.addItemDecoration(new RecycleViewDivider(getContext(),
                LinearLayoutManager.HORIZONTAL, SizeUtil.dip2px(5),
                ContextCompat.getColor(getContext(), R.color.colorGrayLight)));
        commonSwipe.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(getContext(), R.color.colorBlueNormal));
        commonSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMerchantsOrderBySellNumberPresenter.getMerchants(AppConstants.RESTAURANTS, merchantsType);
            }
        });
    }

    @Override
    protected void initView(View view) {
    }

    @Override
    public void loadData() {
        commonRefresh.setVisibility(View.VISIBLE);
        getMerchantsOrderBySellNumberPresenter.getMerchants(AppConstants.RESTAURANTS, merchantsType);
    }


    @Override
    protected void showFailed() {
        qFoodMerchantAdapter.setOnRcvItemClickListener(null);
    }

    @Override
    public void showSuccess(Object o) {
        dataBySellNumberModels = (List<MerchantUrlBean.MerchantBean>) o;
        qFoodMerchantAdapter.setmData(dataBySellNumberModels);
        if (commonSwipe.isRefreshing())
            commonSwipe.setRefreshing(false);
        if (commonRefresh.getVisibility() == View.VISIBLE) {
            commonRefresh.setVisibility(View.GONE);
        }
    }


    @Override
    public void onItemClickListener(View view, int position, MerchantUrlBean.MerchantBean item) {
        Intent intent = new Intent(getContext(), ShopDetailActivity.class);
        MerchantUrlBean.MerchantBean merchantBean = dataBySellNumberModels.get(position);
        /*目前后台还没有加入这是三个字段*/

        intent.putExtra(AppConstants.KEY_MERCHANT_ID, merchantBean.getID());
        intent.putExtra(AppConstants.KEY_MERCHANT_NAME, merchantBean.getName());
        intent.putExtra(AppConstants.KEY_MERCHANT_LOGO, merchantBean.getPic());
        startActivityForResult(intent, 1, ActivityOptionsCompat.makeScaleUpAnimation(view, (int) view.getX(), (int) view.getY(), view.getWidth(), view.getHeight()).toBundle());
        /*Intent intent = new Intent(getContext(), GoodsDetailActivity.class);
        intent.putExtra("productId","20160518");
        intent.putExtra("productName","黄焖鸡米饭");
        intent.putExtra("goodsPrice","￥:90");
        startActivity(intent);*/

    }

}
