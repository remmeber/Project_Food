package com.rhg.qf.ui.activity;

import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
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
    UIAlertView delDialog;
    private List<DeliverOrderUrlBean.DeliverOrderBean> deliverOrderBeanList = new ArrayList<>();
    private DeliverOrderItemAdapter deliverOrderItemAdapter;

    @Override
    public void loadingData() {
        commonRefresh.setVisibility(View.VISIBLE);
        getDeliverOrder = new DeliverOrderPresenter(this);
        getDeliverOrder.getDeliverOrder(AppConstants.DELIVER_ORDER, AccountUtil.getInstance().getUserID());
    }

    @Override
    protected void initData() {
        fl_tab.setBackgroundColor(ContextCompat.getColor(this, R.color.colorBlueNormal));
        tbCenterTv.setText(getResources().getString(R.string.myOrder));
        tbLeftIv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_chevron_left_black));
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
                getDeliverOrder.getDeliverOrder(AppConstants.DELIVER_ORDER, AccountUtil.getInstance().getUserID());
            }
        });

        /*new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpClient androidHttpClient = new DefaultHttpClient();
                    HttpResponse httpResponse = androidHttpClient.execute(new HttpGet("http://www.baidu.com"));
                    byte[] bytes = EntityUtils.toByteArray(httpResponse.getEntity());
                    Message message = Message.obtain();
                    message.what = 1;
                    message.obj = bytes;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();*/
    }

/*    private static android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:

                    Log.i("RHG", " length is :" + ((byte[]) msg.obj).length);
                    break;
                default:
                    break;
            }

        }
    };*/

    @Override
    protected int getLayoutResId() {
        return R.layout.order_manage_layout;
    }

    @Override
    protected void showSuccess(Object s) {
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
    protected void showError(Object s) {

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
    public void onItemClickListener(View view,int position, DeliverOrderUrlBean.DeliverOrderBean item) {

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
    protected void onDestroy() {
        super.onDestroy();
    }
}
