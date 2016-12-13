package com.rhg.qf.ui.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rhg.qf.R;
import com.rhg.qf.adapter.FoodsDetailAdapter;
import com.rhg.qf.bean.OrderDetailUrlBean;
import com.rhg.qf.bean.PayModel;
import com.rhg.qf.constants.AppConstants;
import com.rhg.qf.mvp.presenter.ModifyOrderPresenter;
import com.rhg.qf.mvp.presenter.OrderDetailPresenter;
import com.rhg.qf.utils.DecimalUtil;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.Bind;
import butterknife.OnClick;

/*
 *desc 订单详情页
 *author rhg
 *time 2016/6/22 20:31
 *email 1013773046@qq.com
 */
public class OrderDetailActivity extends BaseAppcompactActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_receiver)
    TextView tvReceiver;
    @Bind(R.id.tv_receiver_phone)
    TextView tvReceiverPhone;
    @Bind(R.id.tv_receiver_address)
    TextView tvReceiverAddress;
    @Bind(R.id.tvOrderNote)
    TextView tvOrderNote;
    @Bind(R.id.rcyPayItem)
    RecyclerView rcyPayItem;

    @Bind(R.id.tv_totalMoney)
    TextView tvTotalMoney;
    @Bind(R.id.ly_totalCount)
    LinearLayout lyTotalCount;
    @Bind(R.id.btPayOrRateOrConform)
    TextView btPayOrRateOrConform;
    @Bind(R.id.iv_edit_right)
    ImageView ivEditRight;
    @Bind(R.id.tv_edit)
    TextView tvEdit;

    OrderDetailPresenter getOrderDetailPresenter;
    ModifyOrderPresenter modifyOrderPresenter;

    String orderId;
    int orderTag;
    String orderPrice;
    /*String merchantName;
    String productName;*/
    OrderDetailUrlBean.OrderDetailBean foodBean = new OrderDetailUrlBean.OrderDetailBean();
    private FoodsDetailAdapter foodsDetailAdapter;

    @Override
    public void dataReceive(Intent intent) {
        orderId = intent.getStringExtra(AppConstants.KEY_ORDER_ID);
        /*merchantName = intent.getStringExtra(AppConstants.KEY_MERCHANT_NAME);
        productName = merchantName;*/
        orderPrice = intent.getStringExtra(AppConstants.KEY_PRODUCT_PRICE);
        orderTag = intent.getIntExtra(AppConstants.KEY_ORDER_TAG, -1);
        /*
        orderReceiver = intent.getStringExtra(AppConstants.SP_USER_NAME);
        orderPhone = intent.getStringExtra(AppConstants.KEY_OR_SP_PHONE);
        orderAddress = intent.getStringExtra(AppConstants.KEY_ADDRESS);*/
    }

    @Override
    public void loadingData() {
        getOrderDetailPresenter = new OrderDetailPresenter(this);
        getOrderDetailPresenter.getOrderDetail(AppConstants.ORDER_DETAIL, orderId);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.order_detail_layout;
    }

    @Override
    protected void initData() {
        toolbar.setTitle(getResources().getString(R.string.orderDetail));
        setSupportActionBar(toolbar);
        setToolbar(toolbar);

        ivEditRight.setVisibility(View.GONE);
        tvEdit.setVisibility(View.GONE);
        rcyPayItem.setLayoutManager(new LinearLayoutManager(this));
        rcyPayItem.setHasFixedSize(true);
        foodsDetailAdapter = new FoodsDetailAdapter(this, foodBean);
        rcyPayItem.setAdapter(foodsDetailAdapter);
        lyTotalCount.setVisibility(View.VISIBLE);
        tvTotalMoney.setText(String.format(Locale.ENGLISH, "%s", ""));
        setText(btPayOrRateOrConform);
    }

    private void setText(TextView btPayOrRateOrConform) {
        if (AppConstants.USER_ORDER_UNPAID == orderTag) {
            btPayOrRateOrConform.setText(getResources().getString(R.string.goPay));
            return;
        }
        if (AppConstants.USER_ORDER_DELIVERING == orderTag) {
            btPayOrRateOrConform.setText(getResources().getString(R.string.conformReceive));
            return;
        }
        if (AppConstants.USER_ORDER_COMPLETE == orderTag
                || AppConstants.USER_ORDER_DRAWBACK == orderTag) {
            btPayOrRateOrConform.setText(getResources().getString(R.string.goEvaluate));
            return;
        }
        btPayOrRateOrConform.setVisibility(View.GONE);
    }

    @Override
    public void showSuccess(Object s) {
        if (s instanceof OrderDetailUrlBean.OrderDetailBean) {
            foodBean = (OrderDetailUrlBean.OrderDetailBean) s;
            setData(foodBean);
        }
        if (s instanceof String && ((String) s).contains("order")) {
            finish();
        }
    }

    private void setData(OrderDetailUrlBean.OrderDetailBean orderDetail) {
        tvReceiver.setText(String.format(Locale.ENGLISH, getResources().getString(R.string.tvReceiver),
                orderDetail.getReceiver()));
        tvReceiverPhone.setText(String.format(Locale.ENGLISH, getResources().getString(R.string.tvContactPhone),
                orderDetail.getPhone()));
        tvReceiverAddress.setText(String.format(Locale.ENGLISH, getResources().getString(R.string.tvReceiveAddress),
                orderDetail.getAddress()));
        tvOrderNote.setText(String.format(Locale.ENGLISH, getResources().getString(R.string.tvNote), "无"));
//        tvMerchantName.setText(merchantName);
        tvTotalMoney.setText(String.format(Locale.ENGLISH, getResources().getString(R.string.countMoney),
                DecimalUtil.addWithScale(orderDetail.getFee(), orderDetail.getPrice(), 2)));
        foodsDetailAdapter.setFoodsBeanList(orderDetail);
    }

    @Override
    public void showError(Object s) {

    }

    @OnClick({/* R.id.btDrawback,*/ R.id.btPayOrRateOrConform})
    public void onClick(View view) {
        switch (view.getId()) {
            /*case R.id.btDrawback:
                if (modifyOrderPresenter == null)
                    modifyOrderPresenter = new ModifyOrderPresenter(this);
                modifyOrderPresenter.modifyUserOrDeliverOrderState(orderId*//*订单号*//*,
                        *//*0:退单，1,：完成*//*AppConstants.ORDER_WITHDRAW);
                break;*/
            case R.id.btPayOrRateOrConform:
                if (orderTag == AppConstants.USER_ORDER_DELIVERING || orderTag == AppConstants.USER_ORDER_COMPLETE) {
                    Intent intent = new Intent(this, DeliverStateNoneActivity.class);
                    intent.putExtra(AppConstants.KEY_ORDER_ID, orderId);
                    startActivity(intent);
                }
                if (orderTag == AppConstants.USER_ORDER_UNPAID) {
                    if (foodsDetailAdapter.getItemCount() == 0)
                        return;
                    Intent intent = new Intent(this, PayActivity.class);
                    PayModel payModel = new PayModel();
                    payModel.setReceiver(tvReceiver.getText().toString().split(":")[1]);
                    payModel.setPhone(tvReceiverPhone.getText().toString().split(":")[1]);
                    payModel.setAddress(tvReceiverAddress.getText().toString().split(":")[1]);
                    ArrayList<PayModel.PayBean> payBeen = new ArrayList<>();
                    PayModel.PayBean _pay = new PayModel.PayBean();
                    StringBuilder sb = new StringBuilder();
                    for (int index :
                            foodBean.getIndex()) {
                        sb.append(foodBean.getFoods().get(index).getRName());
                        sb.append(" ");
                    }
                    _pay.setProductName(sb.toString());
                    _pay.setChecked(true);
                    _pay.setProductId(orderId);
                    int count = 1;
                    _pay.setProductNumber(String.valueOf(count));
                    _pay.setProductPic("");
                    _pay.setProductPrice(orderPrice == null | "".equals(orderPrice) ? "0.00" : orderPrice);
                    _pay.setDeliverFee(foodBean.getFee());
                    payBeen.add(_pay);
                    payModel.setPayBeanList(payBeen);
                    intent.putExtra(AppConstants.KEY_PARCELABLE, payModel);
                    intent.putExtra(AppConstants.ORDER_STYLE, AppConstants.USER_ORDER_UNPAID);
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    public void menuCreated(Menu menu) {
        menu.getItem(0).setVisible(false);
    }

}
