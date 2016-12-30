package com.rhg.qf.ui.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.rhg.qf.R;
import com.rhg.qf.adapter.FoodsDetailAdapter;
import com.rhg.qf.adapter.WrapperAdapter;
import com.rhg.qf.adapter.viewHolder.AddressViewHolder;
import com.rhg.qf.adapter.viewHolder.TotalViewHolder;
import com.rhg.qf.bean.BaseAddressModel;
import com.rhg.qf.bean.IBaseModel;
import com.rhg.qf.bean.InflateModel;
import com.rhg.qf.bean.OrderDetailUrlBean;
import com.rhg.qf.bean.PayModel;
import com.rhg.qf.bean.TotalModel;
import com.rhg.qf.constants.AppConstants;
import com.rhg.qf.mvp.presenter.OrderDetailPresenter;
import com.rhg.qf.utils.DecimalUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/*
 *desc 订单详情页
 *author rhg
 *time 2016/6/22 20:31
 *email 1013773046@qq.com
 */
public class OrderDetailActivity extends BaseAppcompactActivity {

    @Bind(R.id.tb_center_tv)
    TextView tbCenterTv;
    @Bind(R.id.tb_left_iv)
    ImageView tbLeftIv;
    @Bind(R.id.fl_tab)
    FrameLayout flTab;

    @Bind(R.id.rcyPayItem)
    RecyclerView rcyPayItem;

    @Bind(R.id.btPayOrRateOrConform)
    TextView btPayOrRateOrConform;

    OrderDetailPresenter getOrderDetailPresenter;

    String orderId;
    int orderTag;
    //    String orderPrice;
    String orderSrc;
    /*String merchantName;
    String productName;*/
    List<OrderDetailUrlBean.OrderDetailBean.FoodsBean> foodBean = new ArrayList<>();

    WrapperAdapter<IBaseModel> orderDetailAdapter;
    TotalModel totalModel;
    BaseAddressModel addressInfo;

    @Override
    public void dataReceive(Intent intent) {
        orderId = intent.getStringExtra(AppConstants.KEY_ORDER_ID);
        /*merchantName = intent.getStringExtra(AppConstants.KEY_MERCHANT_NAME);
        productName = merchantName;*/
        orderSrc = intent.getStringExtra(AppConstants.KEY_PRODUCT_LOGO);
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
        flTab.setBackgroundColor(ContextCompat.getColor(this, R.color.colorBlueNormal));
        tbCenterTv.setText(getResources().getString(R.string.orderDetail));
        tbLeftIv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_chevron_left_black));
        rcyPayItem.setLayoutManager(new LinearLayoutManager(this));
        rcyPayItem.setHasFixedSize(true);
        FoodsDetailAdapter foodsDetailAdapter = new FoodsDetailAdapter(this, foodBean);
        orderDetailAdapter = new WrapperAdapter<>(foodsDetailAdapter);
        if (totalModel == null)
            totalModel = new TotalModel();
        if (addressInfo == null) {
            addressInfo = new BaseAddressModel();
        }
        orderDetailAdapter.addFooterViews(
                new InflateModel(new Class[]{TotalViewHolder.class, View.class}, new Object[]{R.layout.total_pay_layout}),
                totalModel
        );
        orderDetailAdapter.addHeaderViews(
                new InflateModel(new Class[]{AddressViewHolder.class, View.class}, new Object[]{R.layout.item_receiver_info}),
                addressInfo
        );
        rcyPayItem.setAdapter(orderDetailAdapter);
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
            foodBean.clear();
            foodBean.addAll(((OrderDetailUrlBean.OrderDetailBean) s).getFoods());
            setData((OrderDetailUrlBean.OrderDetailBean) s);
            return;
        }
        if (s instanceof String && ((String) s).contains("order")) {
            finish();
        }
    }

    private void setData(OrderDetailUrlBean.OrderDetailBean orderDetail) {
        List<String> addressList = new ArrayList<>();
        addressList.add(BaseAddressModel.ADDRESS_RECEIVER, orderDetail.getReceiver());
        addressList.add(BaseAddressModel.ADDRESS_PHONE, orderDetail.getPhone());
        addressList.add(BaseAddressModel.ADDRESS_CONTENT, orderDetail.getAddress());
        addressList.add(BaseAddressModel.ADDRESS_DETAIL, "");/*地址详情*/
        addressInfo.setRecommendShopBeanEntity(addressList);

        List<String> totalList = new ArrayList<>();
        totalList.add(TotalModel.DELIVER_FEE, orderDetail.getFee());
        totalList.add(TotalModel.ORDER_TOTAL_PRICE, DecimalUtil.addWithScale(orderDetail.getFee(), orderDetail.getPrice(), 2));
        totalModel.setRecommendShopBeanEntity(totalList);

        orderDetailAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(Object s) {

    }

    @OnClick({R.id.tb_left_iv,/* R.id.btDrawback,*/ R.id.btPayOrRateOrConform})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tb_left_iv:
                finish();
                break;
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
                    if (orderDetailAdapter.getItemCount() == 2)/*去头去尾*/
                        return;
                    Intent intent = new Intent(this, PayActivity.class);
                    PayModel payModel = new PayModel();
                    payModel.setName(addressInfo.getEntity().get(BaseAddressModel.ADDRESS_RECEIVER));
                    payModel.setPhone(addressInfo.getEntity().get(BaseAddressModel.ADDRESS_PHONE));
                    payModel.setAddress(addressInfo.getEntity().get(BaseAddressModel.ADDRESS_CONTENT));
                    payModel.setDetail(addressInfo.getEntity().get(BaseAddressModel.ADDRESS_DETAIL));
                    ArrayList<PayModel.PayBean> payBeen = new ArrayList<>();
                    PayModel.PayBean _pay = new PayModel.PayBean();
                    StringBuilder sb = new StringBuilder();
                    for (OrderDetailUrlBean.OrderDetailBean.FoodsBean _bean : foodBean) {
                        if (_bean.getNum() == null) {
                            sb.append(_bean.getRName());
                            sb.append(" ");
                        }
                    }
                    _pay.setProductName(sb.toString());
                    _pay.setChecked(true);
                    _pay.setProductId(orderId);

                    _pay.setProductNumber("1");
                    _pay.setProductPic(orderSrc);
                    _pay.setProductPrice(totalModel.getEntity().get(TotalModel.ORDER_TOTAL_PRICE));
                    _pay.setDeliverFee(totalModel.getEntity().get(TotalModel.DELIVER_FEE));
                    payBeen.add(_pay);
                    payModel.setPayBeanList(payBeen);
                    intent.putExtra(AppConstants.KEY_PARCELABLE, payModel);
                    intent.putExtra(AppConstants.ORDER_STYLE, AppConstants.USER_ORDER_UNPAID);
                    startActivity(intent);
                }
                break;
        }
    }

}
