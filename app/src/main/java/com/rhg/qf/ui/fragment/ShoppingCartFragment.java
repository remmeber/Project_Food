package com.rhg.qf.ui.fragment;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
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
import com.rhg.qf.ui.activity.AddressActivity;
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
    QFoodShoppingCartExplAdapter QFoodShoppingCartExplAdapter;
    boolean isLogin;
    @Bind(R.id.tv_shopping_cart_ind)
    TextView tvShoppingCartInd;
    @Bind(R.id.rl_shopping_cart_empty)
    RelativeLayout rlShoppingCartEmpty;
    @Bind(R.id.list_shopping_cart)
    SwipeDeleteExpandListView listShoppingCart;
    @Bind(R.id.srl_shopping_cart)
    SwipeRefreshLayout srlShoppingCart;
    @Bind(R.id.tv_count_money)
    TextView tvCountMoney;
    @Bind(R.id.rl_shopping_cart_pay)
    RelativeLayout rlShoppingCartPay;
    private GetAddressPresenter getAddressPresenter;
    private AddressUrlBean.AddressBean addressBean;
    //-----------------根据需求创建相应的presenter----------------------------------------------------

    public ShoppingCartFragment() {
        shoppingCartBeanList = new ArrayList<>();
    }

    //----------------------------------------------------------------------------------------------

    @Override
    public int getLayoutResId() {
        return R.layout.shopping_cart_layout;
    }


    @Override
    public void loadData() {
        if (AccountUtil.getInstance().hasAccount()) {
            isLogin = true;
            List<FoodInfoBean> foodInfoBeanList = ShoppingCartUtil.getAllProductID();

            setData(foodInfoBeanList);
        } else {
            ToastHelper.getInstance().displayToastWithQuickClose("当前用户未登录");
            isLogin = false;
            shoppingCartBeanList.clear();
            updateListView();
        }
    }

    private void setData(List<FoodInfoBean> foodInfoBeanList) {
        if (listShoppingCart.hasExpandState())
            listShoppingCart.close();
        Collections.sort(foodInfoBeanList, new Comparator<FoodInfoBean>() {
            @Override
            public int compare(FoodInfoBean o1, FoodInfoBean o2) {
                return o1.getMerchantId().compareTo(o2.getMerchantId());
            }
        });
        shoppingCartBeanList.clear();
        String lastMerchantId = "-1";
        String newMerchantId;
        ShoppingCartBean shoppingCartBean = null;
        List<ShoppingCartBean.Goods> goodsList = null;
        for (FoodInfoBean foodInfoBean : foodInfoBeanList) {
            Log.i("RHG", foodInfoBean.toString());
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
        }
        updateListView();
    }

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
            isLogin = true;
//            userId = AccountUtil.getInstance().getUserID();
//            getOrdersPresenter.getOrders(AppConstants.TABLE_ORDER, userId, AppConstants.USER_ORDER_UNPAID);
            List<FoodInfoBean> foodInfoBeanList = ShoppingCartUtil.getAllProductID();
            setData(foodInfoBeanList);
        } else {
            ToastHelper.getInstance()._toast("当前用户未登录");
            isLogin = false;
            shoppingCartBeanList.clear();
            updateListView();
        }
    }

    @Override
    protected void initView(View view) {
    }

    @Override
    protected void initData() {
        srlShoppingCart.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(getContext(), R.color.colorBlueNormal));
        srlShoppingCart.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (AccountUtil.getInstance().hasAccount()) {
                    isLogin = true;
                    List<FoodInfoBean> foodInfoBeanList = ShoppingCartUtil.getAllProductID();
                    setData(foodInfoBeanList);
                } else {
                    ToastHelper.getInstance().displayToastWithQuickClose("当前用户未登录");
                    isLogin = false;
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
                    showEmpty(true, isLogin ? getString(R.string.emptyCart) : getString(R.string.pleaseSignIn));
                } else
                    showEmpty(false, null);
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


    private void showEmpty(boolean isEmpty, String msg) {
        if (isEmpty) {
            rlShoppingCartEmpty.setVisibility(View.VISIBLE);
            listShoppingCart.setVisibility(View.GONE);
            rlShoppingCartPay.setVisibility(View.GONE);
            tvShoppingCartInd.setText(msg);
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
        if (o instanceof String) {
            if ("error".equals(o)) {
                ToastHelper.getInstance().displayToastWithQuickClose(o.toString());
                return;
            }
            if (((String) o).contains("order")) {
                refresh();
            }
            return;
        }
        if (o instanceof AddressUrlBean.AddressBean) {
            addressBean = (AddressUrlBean.AddressBean) o;
            createOrderAndToPay(addressBean);
            return;
        }
        if (addressBean == null) {
            Intent intent = new Intent(getContext(), AddressActivity.class);
            intent.setAction(AppConstants.ADDRESS_DEFAULT);
            startActivityForResult(intent, 0);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {
            if (data == null) {
                ToastHelper.getInstance().displayToastWithQuickClose("未能生成订单");
                return;
            }
            addressBean = data.getParcelableExtra(AppConstants.ADDRESS_DEFAULT);
            if (addressBean == null) {
                ToastHelper.getInstance().displayToastWithQuickClose("未能生成订单，请填写详细地址");
                return;
            }
            createOrderAndToPay(addressBean);
        }
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
