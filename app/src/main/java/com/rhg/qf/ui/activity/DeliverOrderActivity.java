package com.rhg.qf.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rhg.qf.R;
import com.rhg.qf.adapter.DeliverOrderItemAdapter;
import com.rhg.qf.bean.DeliverOrderUrlBean;
import com.rhg.qf.constants.AppConstants;
import com.rhg.qf.impl.RcvItemClickListener;
import com.rhg.qf.mvp.presenter.DeliverOrderPresenter;
import com.rhg.qf.mvp.presenter.ModifyOrderPresenter;
import com.rhg.qf.ui.UIAlertView;
import com.rhg.qf.utils.AccountUtil;
import com.rhg.qf.utils.SizeUtil;
import com.rhg.qf.utils.ToastHelper;
import com.rhg.qf.widget.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * desc:跑腿员订单查看页面
 * author：remember
 * time：2016/7/9 0:14
 * email：1013773046@qq.com
 */
public class DeliverOrderActivity extends BaseAppcompactActivity implements DeliverOrderItemAdapter.OrderStyleListener,
        RcvItemClickListener<DeliverOrderUrlBean.DeliverOrderBean> {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
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
    UIAlertView delDialog;
    private List<DeliverOrderUrlBean.DeliverOrderBean> deliverOrderBeanList = new ArrayList<>();
    private DeliverOrderItemAdapter deliverOrderItemAdapter;

    @Override
    public void loadingData() {
        commonRefresh.setVisibility(View.VISIBLE);
        if (TextUtils.isEmpty(AccountUtil.getInstance().getDeliverID())) {
            ToastHelper.getInstance().displayToastWithQuickClose("请登录跑腿员");
            return;
        }
        if (getDeliverOrder == null)
            getDeliverOrder = new DeliverOrderPresenter(this);
        getDeliverOrder.getDeliverOrder(AppConstants.DELIVER_ORDER, AccountUtil.getInstance().getDeliverID());
    }

    @Override
    protected void initData() {
        toolbar.setTitle(getResources().getString(R.string.deliverOrder));
        setSupportActionBar(toolbar);
        setToolbar(toolbar);

        commonRecycle.setLayoutManager(new LinearLayoutManager(this));
        commonRecycle.setHasFixedSize(false);
        RecycleViewDivider _divider = new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL,
                SizeUtil.dip2px(16), ContextCompat.getColor(this, R.color.white));
        commonRecycle.addItemDecoration(_divider);
        deliverOrderItemAdapter = new DeliverOrderItemAdapter(this, deliverOrderBeanList);
        deliverOrderItemAdapter.setOnRcvItemClick(this);
        deliverOrderItemAdapter.setOnStyleChange(this);
        commonRecycle.setAdapter(deliverOrderItemAdapter);

        commonSwipe.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this, R.color.colorBlueNormal));
        commonSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (TextUtils.isEmpty(AccountUtil.getInstance().getDeliverID())) {
                    ToastHelper.getInstance().displayToastWithQuickClose("请登录跑腿员");
                    return;
                }
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
//            ToastHelper.getInstance()._toast((String) s);
            return;
        }
        deliverOrderBeanList = (List<DeliverOrderUrlBean.DeliverOrderBean>) s;
        deliverOrderItemAdapter.setDeliverOrderBeanList(deliverOrderBeanList);
        if (commonRefresh.getVisibility() == View.VISIBLE)
            commonRefresh.setVisibility(View.GONE);
        if (commonSwipe.isRefreshing())
            commonSwipe.setRefreshing(false);
    }

    @Override
    public void showError(Object s) {

    }


    @OnClick({R.id.bt_order_snatch, R.id.bt_order_progress})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_order_snatch:
                btOrderSnatch.setBackgroundColor(ContextCompat.getColor(this, R.color.colorBlueNormal));
                btOrderProgress.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
                showDelDialog("正在改造，敬请期待");
                break;
            case R.id.bt_order_progress:
                btOrderProgress.setBackgroundColor(ContextCompat.getColor(this, R.color.colorBlueNormal));
                btOrderSnatch.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
                break;
        }
    }

    @Override
    public void onStyleChange(String style, int position) {
        if (modifyOrderPresenter == null)
            modifyOrderPresenter = new ModifyOrderPresenter(this);
        switch (style) {
            case AppConstants.DELIVER_ORDER_UNACCEPT:
                deliverOrderBeanList.get(position).setStyle(AppConstants.DELIVER_ORDER_ACCEPT);
                deliverOrderItemAdapter.updateCertainPosition(deliverOrderBeanList, position);
                modifyOrderPresenter.modifyUserOrDeliverOrderState(deliverOrderBeanList.get(position).getID(),
                        AppConstants.UPDATE_ORDER_WAIT);
                break;
            case AppConstants.DELIVER_ORDER_ACCEPT:
                deliverOrderBeanList.get(position).setStyle(AppConstants.DELIVER_ORDER_DELIVERING);
                deliverOrderItemAdapter.updateCertainPosition(deliverOrderBeanList, position);
                modifyOrderPresenter.modifyUserOrDeliverOrderState(deliverOrderBeanList.get(position).getID(),
                        AppConstants.UPDATE_ORDER_DELIVER);
                break;
            case AppConstants.DELIVER_ORDER_DELIVERING:
                deliverOrderBeanList.get(position).setStyle(AppConstants.DELIVER_ORDER_COMPLETE);
                deliverOrderItemAdapter.updateCertainPosition(deliverOrderBeanList, position);
                modifyOrderPresenter.modifyUserOrDeliverOrderState(deliverOrderBeanList.get(position).getID(),
                        AppConstants.ORDER_FINISH);/*1表示已完成*/
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClickListener(View view, int position, DeliverOrderUrlBean.DeliverOrderBean item) {
        Intent _intent = new Intent(this, OrderDetailActivity.class);
        _intent.putExtra(AppConstants.KEY_ORDER_ID, item.getID());
        _intent.putExtra(AppConstants.KEY_ORDER_TAG, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            startActivity(_intent, ActivityOptionsCompat.makeScaleUpAnimation(view, (int) view.getX(), (int) view.getY(), view.getWidth(), view.getHeight()).toBundle());
        } else
            startActivity(_intent);


    }

    @Override
    public void onItemLongClickListener(View view, int position, DeliverOrderUrlBean.DeliverOrderBean item) {

    }

    private void showDelDialog(final String content) {
        if (delDialog == null) {
            delDialog = new UIAlertView(this, "温馨提示", content,
                    "", "知道啦");
            delDialog.setClicklistener(new UIAlertView.ClickListenerInterface() {
                                           @Override
                                           public void doLeft() {
                                           }

                                           @Override
                                           public void doRight() {
                                               delDialog.dismiss();
                                               btOrderProgress.setBackgroundColor(ContextCompat.getColor(DeliverOrderActivity.this, R.color.colorBlueNormal));
                                               btOrderSnatch.setBackgroundColor(ContextCompat.getColor(DeliverOrderActivity.this, R.color.white));

                                           }
                                       }
            );
        }
        delDialog.show();
    }

    @Override
    public void menuCreated(Menu menu) {
        menu.getItem(0).setVisible(false);
    }
}
