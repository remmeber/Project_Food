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
import com.rhg.qf.adapter.MainAdapter;
import com.rhg.qf.adapter.WrapperAdapter;
import com.rhg.qf.adapter.viewHolder.AllShopsViewHolder;
import com.rhg.qf.adapter.viewHolder.HomeShopViewHolder;
import com.rhg.qf.adapter.viewHolder.HeaderViewHolder;
import com.rhg.qf.bean.CommonListModel;
import com.rhg.qf.bean.IBaseModel;
import com.rhg.qf.bean.InflateModel;
import com.rhg.qf.bean.MerchantUrlBean;
import com.rhg.qf.constants.AppConstants;
import com.rhg.qf.impl.OnItemClickListener;
import com.rhg.qf.mvp.presenter.MerchantsPresenter;
import com.rhg.qf.ui.activity.ShopDetailActivity;
import com.rhg.qf.utils.AccountUtil;
import com.rhg.qf.utils.SizeUtil;
import com.rhg.qf.utils.ToastHelper;
import com.rhg.qf.widget.RecycleViewDivider;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;


/**
 * desc:所有店铺的按销量
 * author：remember
 * time：2016/5/28 16:42
 * email：1013773046@qq.com
 */
public abstract class AbstractMerchantsFragment extends BaseFragment implements OnItemClickListener<IBaseModel> {
    @Bind(R.id.common_recycle)
    RecyclerView commonRecycle;
    @Bind(R.id.common_refresh)
    ProgressBar commonRefresh;
    @Bind(R.id.common_swipe)
    SwipeRefreshLayout commonSwipe;

    CommonListModel<MerchantUrlBean.MerchantBean> bodyMerchant = new CommonListModel<>();
    CommonListModel<MerchantUrlBean.MerchantBean> headMerchant = new CommonListModel<>();
    MerchantsPresenter getMerchantsOrderBySellNumberPresenter;
    WrapperAdapter<IBaseModel> wrapperAdapter;
    int merchantsType;

    public AbstractMerchantsFragment() {
        merchantsType = getMerchantsFmType();
        getMerchantsOrderBySellNumberPresenter = new MerchantsPresenter(this);
    }

    public void setContext(Context context) {
        /*if (qFoodMerchantAdapter != null)
            qFoodMerchantAdapter.setContext(context);*/
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
        commonRecycle.addItemDecoration(new RecycleViewDivider(getContext(),
                LinearLayoutManager.HORIZONTAL, SizeUtil.dip2px(2),
                ContextCompat.getColor(getContext(), R.color.white_light)));
        MainAdapter<IBaseModel> mainAdapter = new MainAdapter<>(
                new InflateModel(new Class[]{AllShopsViewHolder.class, View.class}, new Object[]{R.layout.item_sell_shop}),
                bodyMerchant,
                this
        );
        wrapperAdapter = new WrapperAdapter<>(mainAdapter, this);
        wrapperAdapter.addHeaderViews(new InflateModel(new Class[]{HeaderViewHolder.class, View.class}, new Object[]{R.layout.item_all_shop_header}), headMerchant);
        commonRecycle.setAdapter(wrapperAdapter);
        commonSwipe.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(getContext(), R.color.colorBlueNormal));
        commonSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if ("".contains(AccountUtil.getInstance().getLongitude())) {
                    ToastHelper.getInstance().displayToastWithQuickClose("无法定位当前位置");
                    return;
                }
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
    }

    @Override
    public void showSuccess(Object o) {
        if (o instanceof CommonListModel) {
            List<MerchantUrlBean.MerchantBean> merchantBeenList = ((CommonListModel<MerchantUrlBean.MerchantBean>) o).getEntity();
            headMerchant.setRecommendShopBeanEntity(Collections.singletonList(merchantBeenList.get(0)));
            merchantBeenList.remove(0);
            bodyMerchant.setRecommendShopBeanEntity(merchantBeenList);
            wrapperAdapter.notifyDataSetChanged();
        }
        if (commonSwipe.isRefreshing())
            commonSwipe.setRefreshing(false);
        if (commonRefresh.getVisibility() == View.VISIBLE) {
            commonRefresh.setVisibility(View.GONE);
        }
    }


    @Override
    public void onItemClick(View view, int position, IBaseModel item) {
        Intent intent = new Intent(getContext(), ShopDetailActivity.class);
        MerchantUrlBean.MerchantBean merchantBean = (MerchantUrlBean.MerchantBean) item.getEntity().get(position);
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

    @Override
    public void onItemLongClick(View view, int position, IBaseModel item) {

    }

}
