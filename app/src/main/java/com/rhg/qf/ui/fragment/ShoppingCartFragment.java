package com.rhg.qf.ui.fragment;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rhg.qf.R;
import com.rhg.qf.adapter.QFoodShoppingCartExplAdapter;
import com.rhg.qf.bean.AddressUrlBean;
import com.rhg.qf.bean.FoodInfoBean;
import com.rhg.qf.bean.PayModel;
import com.rhg.qf.bean.ShoppingCartBean;
import com.rhg.qf.constants.AppConstants;
import com.rhg.qf.mvp.presenter.GetAddressPresenter;
import com.rhg.qf.mvp.presenter.ModifyOrderPresenter;
import com.rhg.qf.ui.activity.PayActivity;
import com.rhg.qf.utils.AccountUtil;
import com.rhg.qf.utils.ShoppingCartUtil;
import com.rhg.qf.utils.ToastHelper;
import com.rhg.qf.widget.SwipeDeleteExpandListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * desc:购物车fm
 * author：remember
 * time：2016/5/28 16:49
 * email：1013773046@qq.com
 */
public class ShoppingCartFragment extends BaseFragment {
    List<ShoppingCartBean> shoppingCartBeanList;
    List<ShoppingCartBean.Goods> goodsList;
    QFoodShoppingCartExplAdapter QFoodShoppingCartExplAdapter;
    ModifyOrderPresenter modifyOrderPresenter;
//    OrdersPresenter getOrdersPresenter;

    @Bind(R.id.tb_center_tv)
    TextView tbCenterTV;
    @Bind(R.id.tb_right_ll)
    LinearLayout tbRight;
    @Bind(R.id.fl_tab)
    FrameLayout fl_tab;
    @Bind(R.id.rl_shopping_cart_empty)
    RelativeLayout rlShoppingCartEmpty;
    @Bind(R.id.list_shopping_cart)
    SwipeDeleteExpandListView listShoppingCart;
    @Bind(R.id.srl_shopping_cart)
    SwipeRefreshLayout srlShoppingCart;
    @Bind(R.id.tv_count_money)
    TextView tvCountMoney;
    @Bind(R.id.ll_shopping_cart)
    LinearLayout llShoppingCart;
    @Bind(R.id.rl_shopping_cart_pay)
    RelativeLayout rlShoppingCartPay;
    private GetAddressPresenter getAddressPresenter;
    private String userId;
    //-----------------根据需求创建相应的presenter----------------------------------------------------

    public ShoppingCartFragment() {
        shoppingCartBeanList = new ArrayList<>();
        /*for (int i = 0; i < 6; i++) {
            ShoppingCartBean shoppingCartBean = new ShoppingCartBean();
            shoppingCartBean.setMerchantName("iiiii");
            shoppingCartBean.setMerID("2015051800");
            goodsList = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                ShoppingCartBean.Goods goods = new ShoppingCartBean.Goods();
                goods.setGoodsLogoUrl(R.drawable.recommend_default_icon_1);
                goods.setGoodsName("" + j);
                goods.setPrice("" + j * 2);
                goods.setProductID("20160518");
                goods.setNumber("1");
                goodsList.add(goods);
            }
            shoppingCartBean.setGoods(goodsList);
            shoppingCartBeanList.add(shoppingCartBean);
        }*/
    }

    //----------------------------------------------------------------------------------------------

    @Override
    public int getLayoutResId() {
        return R.layout.shopping_cart_layout;
    }


    @Override
    public void loadData() {
//        getOrdersPresenter = new OrdersPresenter(this);
        if (AccountUtil.getInstance().hasAccount()) {
//            userId = AccountUtil.getInstance().getUserID();
//            getOrdersPresenter.getOrders(AppConstants.TABLE_ORDER, userId, AppConstants.USER_ORDER_UNPAID);
            List<FoodInfoBean> foodInfoBeanList = ShoppingCartUtil.getAllProductID();
//            Log.i("RHG", foodInfoBean.toString());
            Collections.sort(foodInfoBeanList, new Comparator<FoodInfoBean>() {
                @Override
                public int compare(FoodInfoBean o1, FoodInfoBean o2) {
                    return o1.getMerchantId().compareTo(o2.getMerchantId());
                }
            });

            setData(foodInfoBeanList);
        } else {
            ToastHelper.getInstance()._toast("当前用户未登录");
            shoppingCartBeanList.clear();
            updateListView();
        }
    }

    private void setData(List<FoodInfoBean> foodInfoBeanList) {
        shoppingCartBeanList.clear();
        String lastMerchantId = "-1";
        String newMerchantId;
        ShoppingCartBean shoppingCartBean = null;
        List<ShoppingCartBean.Goods> goodsList = null;
        for (FoodInfoBean foodInfoBean : foodInfoBeanList) {
            newMerchantId = foodInfoBean.getMerchantId();

            if (!newMerchantId.equals(lastMerchantId)) {
                shoppingCartBean = new ShoppingCartBean();
            }
            if (shoppingCartBean == null)
                throw new NullPointerException("can not apply null object");
            shoppingCartBean.setMerchantName(foodInfoBean.getMerchantName());
            shoppingCartBean.setMerID(foodInfoBean.getMerchantId());
            if (!newMerchantId.equals(lastMerchantId))
                goodsList = new ArrayList<>();
            ShoppingCartBean.Goods goods = new ShoppingCartBean.Goods();
            goods.setGoodsName(foodInfoBean.getFoodName());
            goods.setGoodsLogoUrl(foodInfoBean.getFoodUri());
            goods.setPrice(foodInfoBean.getFoodPrice());
            goods.setGoodsID(foodInfoBean.getFoodId());
            goods.setNumber(foodInfoBean.getFoodNum());
            if (goodsList == null)
                throw new NullPointerException("can not apply null object");
            goodsList.add(goods);
            if (!newMerchantId.equals(lastMerchantId)) {
                shoppingCartBean.setGoods(goodsList);
                shoppingCartBeanList.add(shoppingCartBean);
                lastMerchantId = newMerchantId;
            }
//            ShoppingCartUtil.addGoodToCart(orderBean.getID(), orderBean.getRName());
        }
        updateListView();
    }

/*    @Override
    public void onStart() {
        if(hasFetchData){

        }
        super.onStart();
    }*/

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser && ToastHelper.getInstance().isShowing()) {
            ToastHelper.getInstance().cancel();
        }
        if (isVisibleToUser && hasFetchData) {
            refresh();
        }
    }

    @Override
    protected void refresh() {
        super.refresh();
        if (AccountUtil.getInstance().hasAccount()) {
            userId = AccountUtil.getInstance().getUserID();
//            getOrdersPresenter.getOrders(AppConstants.TABLE_ORDER, userId, AppConstants.USER_ORDER_UNPAID);
            List<FoodInfoBean> foodInfoBeanList = ShoppingCartUtil.getAllProductID();
            setData(foodInfoBeanList);
        } else {
            ToastHelper.getInstance()._toast("当前用户未登录");
            shoppingCartBeanList.clear();
            updateListView();
        }
    }

    /*  @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isViewPrepare) {
            Log.i("RHG", isVisibleToUser + "");
            getOrdersPresenter.getOrders(AppConstants.TABLE_ORDER, userId, AppConstants.USER_ORDER_UNPAID);
        }
    }*/

/*    *//*Fragment被activity覆盖后，重新显示出来时调用的方法*//*
    @Override
    public void onStart() {
        super.onStart();
        this.setUserVisibleHint(true);
    }*/

    @Override
    protected void initView(View view) {
    }

    @Override
    protected void initData() {
        fl_tab.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorBlueNormal));
        tbCenterTV.setText(getResources().getString(R.string.shoppingCart));
        tbRight.setVisibility(View.GONE);
        srlShoppingCart.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(getContext(), R.color.colorBlueNormal));
        srlShoppingCart.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (AccountUtil.getInstance().hasAccount()) {
                    userId = AccountUtil.getInstance().getUserID();
//                    getOrdersPresenter.getOrders(AppConstants.TABLE_ORDER, userId, AppConstants.USER_ORDER_UNPAID);
                    List<FoodInfoBean> foodInfoBeanList = ShoppingCartUtil.getAllProductID();
                    setData(foodInfoBeanList);
                } else {
                    ToastHelper.getInstance()._toast("当前用户未登录");
                }
            }
        });
        QFoodShoppingCartExplAdapter = new QFoodShoppingCartExplAdapter(getContext());
        listShoppingCart.setAdapter(QFoodShoppingCartExplAdapter);
        listShoppingCart.setGroupIndicator(null);
        listShoppingCart.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
        QFoodShoppingCartExplAdapter.setDataChangeListener(new QFoodShoppingCartExplAdapter.DataChangeListener() {
            @Override
            public void onDataChange(String CountMoney) {
                if (shoppingCartBeanList.size() == 0) {
                    showEmpty(true);
                } else
                    showEmpty(false);
                String countMoney = String.format(getResources().getString(R.string.countMoney), CountMoney);
                tvCountMoney.setText(countMoney);
            }

            @Override
            public void removeData(String merchantId, String foodId) {
                /*if (modifyOrderPresenter == null)
                    modifyOrderPresenter = new ModifyOrderPresenter(ShoppingCartFragment.this);
                Log.i("RHG", "Id: " + Id);
                modifyOrderPresenter.modifyUserOrDeliverOrderState(Id*//*订单号*//*,
                        *//*0:退单，1,：完成*//*AppConstants.ORDER_WITHDRAW);*/
                ShoppingCartUtil.delGood(merchantId, foodId);

            }
        });
        updateListView();
    }

    private void updateListView() {
        QFoodShoppingCartExplAdapter.setmData(shoppingCartBeanList);
        QFoodShoppingCartExplAdapter.notifyDataSetChanged();
        expandAll(shoppingCartBeanList.size());
        srlShoppingCart.setRefreshing(false);
    }

    private void showEmpty(boolean isEmpty) {
        if (isEmpty) {
            rlShoppingCartEmpty.setVisibility(View.VISIBLE);
            listShoppingCart.setVisibility(View.GONE);
            rlShoppingCartPay.setVisibility(View.GONE);
        } else {
            rlShoppingCartEmpty.setVisibility(View.GONE);
            listShoppingCart.setVisibility(View.VISIBLE);
            rlShoppingCartPay.setVisibility(View.VISIBLE);
        }
    }

    private void expandAll(int size) {
        for (int i = 0; i < size; i++) {
            listShoppingCart.expandGroup(i);
        }
    }


    @Override
    protected void showFailed() {

    }

    @Override
    public void showSuccess(Object o) {
        if (o instanceof AddressUrlBean.AddressBean) {
            AddressUrlBean.AddressBean addressBean = (AddressUrlBean.AddressBean) o;
            createOrderAndToPay(addressBean);
            return;
        }
        if (o instanceof String) {
            if ("error".equals(o)) {
                ToastHelper.getInstance()._toast(o.toString());
                return;
            }
            if (((String) o).contains("order")) {
                refresh();
            }
        }
//        ShoppingCartUtil.delAllGoods();
        /*shoppingCartBeanList.clear();
        for (OrderUrlBean.OrderBean orderBean : (List<OrderUrlBean.OrderBean>) o) {
            ShoppingCartBean shoppingCartBean = new ShoppingCartBean();
            List<ShoppingCartBean.Goods> goodsList = new ArrayList<>();
            ShoppingCartBean.Goods goods = new ShoppingCartBean.Goods();
            goods.setGoodsName(orderBean.getRName());
            goods.setGoodsLogoUrl(orderBean.getPic());
            goods.setPrice(orderBean.getPrice());
            goods.setFee(orderBean.getFee());
            goods.setGoodsID(orderBean.getID());
            goods.setNumber("1");
            goodsList.add(goods);
            shoppingCartBean.setGoods(goodsList);
            shoppingCartBeanList.add(shoppingCartBean);
//            ShoppingCartUtil.addGoodToCart(orderBean.getID(), orderBean.getRName());
        }
        updateListView();*/
    }

    private void createOrderAndToPay(AddressUrlBean.AddressBean addressBean) {
        Intent intent = new Intent(getActivity(),
                PayActivity.class);
        PayModel payModel = new PayModel();
        payModel.setReceiver(addressBean.getName());
        payModel.setPhone(addressBean.getPhone());
        payModel.setAddress(addressBean.getAddress().concat(addressBean.getDetail()));
        ArrayList<PayModel.PayBean> payBeen = ShoppingCartUtil.getSelectGoods(shoppingCartBeanList);
        payModel.setPayBeanList(payBeen);
        intent.putExtra(AppConstants.KEY_PARCELABLE, payModel);
//        intent.putExtra(AppConstants.ORDER_STYLE, AppConstants.USER_ORDER_UNPAID);
        startActivity(intent);
    }

    @OnClick({R.id.iv_shopping_cart_empty, R.id.tv_count})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_shopping_cart_empty:
                break;
            case R.id.tv_count:
                if (ShoppingCartUtil.hasSelectedGoods(shoppingCartBeanList)) {
                    if (getAddressPresenter == null)
                        getAddressPresenter = new GetAddressPresenter(this);
                    getAddressPresenter.getAddress(AppConstants.TABLE_DEFAULT_ADDRESS);
                } else
                    ToastHelper.getInstance().displayToastWithQuickClose("亲，请选择商品！");
                break;
        }
    }
}
