package com.rhg.qf.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rhg.qf.R;
import com.rhg.qf.adapter.MainAdapter;
import com.rhg.qf.adapter.viewHolder.DeliverOrderViewHolder;
import com.rhg.qf.bean.CommonListModel;
import com.rhg.qf.bean.DeliverOrderUrlBean;
import com.rhg.qf.bean.IBaseModel;
import com.rhg.qf.bean.InflateModel;
import com.rhg.qf.constants.AppConstants;
import com.rhg.qf.impl.OnItemClickListener;
import com.rhg.qf.mvp.presenter.DeliverOrderPresenter;
import com.rhg.qf.mvp.presenter.ModifyOrderPresenter;
import com.rhg.qf.utils.AccountUtil;
import com.rhg.qf.utils.DecimalUtil;
import com.rhg.qf.utils.SizeUtil;
import com.rhg.qf.widget.RecycleViewDivider;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * desc:跑腿员订单查看页面
 * author：remember
 * time：2016/7/9 0:14
 * email：1013773046@qq.com
 */
public class DeliverOrderActivity extends BaseAppcompactActivity implements OnItemClickListener<CommonListModel<DeliverOrderUrlBean.DeliverOrderBean>> {
    @Bind(R.id.fl_tab)
    FrameLayout fl_tab;
    @Bind(R.id.tb_center_tv)
    TextView tbCenterTv;
    @Bind(R.id.tb_left_iv)
    ImageView tbLeftIv;
    @Bind(R.id.bt_order_snatch)
    TextView btOrderSnatch;
    @Bind(R.id.bt_order_progress)
    TextView btOrderProgress;
    @Bind(R.id.common_recycle)
    RecyclerView commonRecycle;
    @Bind(R.id.common_refresh)
    ProgressBar commonRefresh;
    @Bind(R.id.common_swipe)
    SwipeRefreshLayout commonSwipe;
    DeliverOrderPresenter getDeliverOrder;/*获取跑腿员订单接口*/
    ModifyOrderPresenter modifyOrderPresenter;/*修改跑腿员订单接口*/
    int pos;
    private CommonListModel<DeliverOrderUrlBean.DeliverOrderBean> deliverOrderBeanList = new CommonListModel<>();
    private MainAdapter<CommonListModel<DeliverOrderUrlBean.DeliverOrderBean>> deliverOrderItemAdapter;

    @Override
    public void loadingData() {
        commonRefresh.setVisibility(View.VISIBLE);
        if (getDeliverOrder == null)
            getDeliverOrder = new DeliverOrderPresenter(this);
        getDeliverOrder.getDeliverOrder(AppConstants.DELIVER_ORDER, AccountUtil.getInstance().getDeliverID());
    }

    @Override
    protected void initData() {
        fl_tab.setBackgroundColor(ContextCompat.getColor(this, R.color.colorBlueNormal));
        tbCenterTv.setText(getResources().getString(R.string.deliverOrder));
        tbLeftIv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_chevron_left_black));
        commonRecycle.setLayoutManager(new LinearLayoutManager(this));
        commonRecycle.setHasFixedSize(false);
        RecycleViewDivider _divider = new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL,
                SizeUtil.dip2px(16), ContextCompat.getColor(this, R.color.white));
        commonRecycle.addItemDecoration(_divider);
        deliverOrderItemAdapter = new MainAdapter<>(
                new InflateModel(new Class[]{DeliverOrderViewHolder.class, View.class}, new Object[]{R.layout.item_order_manage}),
                deliverOrderBeanList,
                this
        );
        commonRecycle.setAdapter(deliverOrderItemAdapter);

        commonSwipe.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this, R.color.colorBlueNormal));
        commonSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (getDeliverOrder == null)
                    getDeliverOrder = new DeliverOrderPresenter(DeliverOrderActivity.this);
                getDeliverOrder.getDeliverOrder(AppConstants.DELIVER_ORDER, AccountUtil.getInstance().getDeliverID());
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.order_manage_layout;
    }

    @Override
    public void showSuccess(Object s) {
        if (s instanceof String) {
            if (!((String) s).contains("error")) {
                deliverOrderBeanList.getEntity().get(pos).setStyle(s.toString());
                deliverOrderItemAdapter.notifyItemChanged(pos);
                updateOrderNum(s.toString());
            }
        }
        if (s instanceof IBaseModel) {
            deliverOrderBeanList.setRecommendShopBeanEntity(((IBaseModel) s).getEntity());
            deliverOrderItemAdapter.notifyDataSetChanged();
        }
        if (commonRefresh.getVisibility() == View.VISIBLE)
            commonRefresh.setVisibility(View.GONE);
        if (commonSwipe.isRefreshing())
            commonSwipe.setRefreshing(false);
    }

    private void updateOrderNum(String s) {
        String num = AccountUtil.getInstance().getDeliverOrderNum();
        switch (s) {
            case AppConstants.DELIVER_ORDER_ACCEPT:

                AccountUtil.getInstance().setDeliverOrderNum(DecimalUtil.add(TextUtils.isEmpty(num) ? "0" : num, "1"));
                Log.i("RHG", "增加1");
                break;
            case AppConstants.DELIVER_ORDER_COMPLETE:
                if (TextUtils.isEmpty(num))
                    return;
                AccountUtil.getInstance().setDeliverOrderNum(DecimalUtil.subtract(num, "1"));
                Log.i("RHG", "减少1");
                break;
        }
    }


    @OnClick({R.id.tb_left_iv, R.id.bt_order_snatch, R.id.bt_order_progress})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tb_left_iv:
                finish();
                break;
            case R.id.bt_order_snatch:
                btOrderSnatch.setBackgroundColor(ContextCompat.getColor(this, R.color.colorBlueNormal));
                btOrderProgress.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
                DialogShow("正在改造，敬请期待");
                break;
            case R.id.bt_order_progress:
                btOrderProgress.setBackgroundColor(ContextCompat.getColor(this, R.color.colorBlueNormal));
                btOrderSnatch.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
                break;
        }
    }

    private void changeStyle(String style, String id) {
        if (style.equals(AppConstants.DELIVER_ORDER_COMPLETE))
            return;
        if (modifyOrderPresenter == null)
            modifyOrderPresenter = new ModifyOrderPresenter(this);
        switch (style) {
            case AppConstants.DELIVER_ORDER_UNACCEPT:
                /*deliverOrderBeanList.get(position).setStyle(AppConstants.DELIVER_ORDER_ACCEPT);
                deliverOrderItemAdapter.updateCertainPosition(deliverOrderBeanList, position);*/
                modifyOrderPresenter.modifyUserOrDeliverOrderState(id, AppConstants.UPDATE_ORDER_WAIT);
                break;
            case AppConstants.DELIVER_ORDER_ACCEPT:
                /*deliverOrderBeanList.get(position).setStyle(AppConstants.DELIVER_ORDER_DELIVERING);
                deliverOrderItemAdapter.updateCertainPosition(deliverOrderBeanList, position);*/
                modifyOrderPresenter.modifyUserOrDeliverOrderState(id, AppConstants.UPDATE_ORDER_DELIVER);
                break;
            case AppConstants.DELIVER_ORDER_DELIVERING:
                /*deliverOrderBeanList.get(position).setStyle(AppConstants.DELIVER_ORDER_COMPLETE);
                deliverOrderItemAdapter.updateCertainPosition(deliverOrderBeanList, position);*/
                modifyOrderPresenter.modifyUserOrDeliverOrderState(id, AppConstants.ORDER_FINISH);/*1表示已完成*/
                break;
            default:
                break;
        }
    }

/*    @Override
    public void onItemClick(View view, int position, DeliverOrderUrlBean.DeliverOrderBean item) {
        Intent _intent = new Intent(this, OrderDetailActivity.class);
        _intent.putExtra(AppConstants.KEY_ORDER_ID, item.getID());
        _intent.putExtra(AppConstants.KEY_ORDER_TAG, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            startActivity(_intent, ActivityOptionsCompat.makeScaleUpAnimation(view, (int) view.getX(), (int) view.getY(), view.getWidth(), view.getHeight()).toBundle());
        } else
            startActivity(_intent);


    }*/

    @Override
    public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
        btOrderProgress.setBackgroundColor(ContextCompat.getColor(DeliverOrderActivity.this, R.color.colorBlueNormal));
        btOrderSnatch.setBackgroundColor(ContextCompat.getColor(DeliverOrderActivity.this, R.color.white));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onItemClick(View view, int position, CommonListModel<DeliverOrderUrlBean.DeliverOrderBean> item) {
        switch (view.getId()) {
            case R.id.rl_order_info:
                Intent _intent = new Intent(this, OrderDetailActivity.class);
                _intent.putExtra(AppConstants.KEY_ORDER_ID, item.getEntity().get(position).getID());
                _intent.putExtra(AppConstants.KEY_ORDER_TAG, 0);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    startActivity(_intent, ActivityOptionsCompat.makeScaleUpAnimation(view, (int) view.getX(), (int) view.getY(), view.getWidth(), view.getHeight()).toBundle());
                } else
                    startActivity(_intent);
                break;
            case R.id.tv_order_ind:
                pos = position;
                changeStyle(item.getEntity().get(position).getStyle(), item.getEntity().get(position).getID());
                break;
        }
    }

    @Override
    public void onItemLongClick(View view, int position, CommonListModel<DeliverOrderUrlBean.DeliverOrderBean> item) {

    }
}
