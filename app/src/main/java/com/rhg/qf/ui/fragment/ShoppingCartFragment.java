package com.rhg.qf.ui.fragment;

import android.content.DialogInterface;
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
import com.rhg.qf.bean.BaseAddress;
import com.rhg.qf.bean.FoodInfoBean;
import com.rhg.qf.bean.IBaseModel;
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
public class ShoppingCartFragment extends BaseFragment implements com.rhg.qf.adapter.QFoodShoppingCartExplAdapter.DataChangeListener {
    List<ShoppingCartBean> shoppingCartBeanList;
    QFoodShoppingCartExplAdapter QFoodShoppingCartExplAdapter;
    boolean isLogin;
    int childPos;
    int parentPos;
    @Bind(R.id.tb_center_tv)
    TextView tbCenterTV;
    @Bind(R.id.tb_right_ll)
    LinearLayout tbRight;
    @Bind(R.id.fl_tab)
    FrameLayout fl_tab;
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
    @Bind(R.id.ll_shopping_cart)
    LinearLayout llShoppingCart;
    @Bind(R.id.rl_shopping_cart_pay)
    RelativeLayout rlShoppingCartPay;
    private GetAddressPresenter getAddressPresenter;
    private BaseAddress addressBean;
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
        if (AccountUtil.getInstance().hasUserAccount()) {
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
        if (AccountUtil.getInstance().hasUserAccount()) {
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
        fl_tab.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorBlueNormal));
        tbCenterTV.setText(getResources().getString(R.string.shoppingCart));
        tbRight.setVisibility(View.GONE);
        srlShoppingCart.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(getContext(), R.color.colorBlueNormal));
        srlShoppingCart.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (AccountUtil.getInstance().hasUserAccount()) {
                    isLogin = false;
//                    userId = AccountUtil.getInstance().getUserID();
//                    getOrdersPresenter.getOrders(AppConstants.TABLE_ORDER, userId, AppConstants.USER_ORDER_UNPAID);
                    List<FoodInfoBean> foodInfoBeanList = ShoppingCartUtil.getAllProductID();
                    setData(foodInfoBeanList);
                } else {
                    ToastHelper.getInstance().displayToastWithQuickClose("当前用户未登录");
                    isLogin = false;
                }
            }
        });
        QFoodShoppingCartExplAdapter = new QFoodShoppingCartExplAdapter(mActivity);
        listShoppingCart.setAdapter(QFoodShoppingCartExplAdapter);
        listShoppingCart.setGroupIndicator(null);
        listShoppingCart.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
        QFoodShoppingCartExplAdapter.setOnClickListener(new QFoodShoppingCartExplAdapter.ClickListener() {
            @Override
            public void OnParentClick(int parent) {

            }

            @Override
            public void OnChildClick(int parent, int child, int action) {
                childPos = child;
                parentPos = parent;
                if (action == DELETE) {
                    mActivity.DialogShow("确认删除该商品吗?");
                }
            }
        });
        QFoodShoppingCartExplAdapter.setDataChangeListener(this);
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

        if (o instanceof IBaseModel) {
            if (((IBaseModel) o).getEntity().size() == 0) {
                Intent intent = new Intent(getContext(), AddressActivity.class);
                intent.setAction(AppConstants.ADDRESS_DEFAULT);
                startActivityForResult(intent, 0);
                return;
            }

            addressBean = (BaseAddress) ((IBaseModel) o).getEntity().get(0);
            createOrderAndToPay(addressBean);
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

    private void createOrderAndToPay(BaseAddress addressBean) {
        Intent intent = new Intent(getActivity(),
                PayActivity.class);
        PayModel payModel = new PayModel();
        payModel.setName(addressBean.getName());
        payModel.setPhone(addressBean.getPhone());
        payModel.setAddress(addressBean.getAddress());
        payModel.setDetail(addressBean.getDetail());
        ArrayList<PayModel.PayBean> payBeen = ShoppingCartUtil.getSelectGoods(shoppingCartBeanList);
        payModel.setPayBeanList(payBeen);
        intent.putExtra(AppConstants.KEY_PARCELABLE, payModel);
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

    @Override
    public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
        if (which == DialogInterface.BUTTON_NEGATIVE) {
            return;
        }
        if (which == DialogInterface.BUTTON_POSITIVE) {
            delGoods(parentPos, childPos, shoppingCartBeanList.get(parentPos).getMerID(), shoppingCartBeanList.get(parentPos).getGoods().get(childPos).getGoodsID());
            String[] infos = ShoppingCartUtil.getShoppingCount(shoppingCartBeanList);
            this.onDataChange(infos[1]);
            QFoodShoppingCartExplAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 删除商品
     *
     * @param groupPosition
     * @param childPosition
     */
    private void delGoods(int groupPosition, int childPosition, String merchantId, String foodId) {
        this.removeData(merchantId, foodId);
        shoppingCartBeanList.get(groupPosition).getGoods().remove(childPosition);
        if (shoppingCartBeanList.get(groupPosition).getGoods().size() == 0) {
            shoppingCartBeanList.remove(groupPosition);
        }
    }

    @Override
    public void onDataChange(String CountMoney) {
        if (shoppingCartBeanList.size() == 0) {
            showEmpty(true, isLogin ? getString(R.string.emptyCart) : getString(R.string.pleaseSignInAsUser));
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
}
