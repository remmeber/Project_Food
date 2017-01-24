package com.rhg.qf.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.rhg.qf.R;
import com.rhg.qf.bean.SignInBackBean;
import com.rhg.qf.constants.AppConstants;
import com.rhg.qf.impl.SignInListener;
import com.rhg.qf.mvp.presenter.UserSignInPresenter;
import com.rhg.qf.mvp.presenter.UserSignUpPresenter;
import com.rhg.qf.third.UmengUtil;
import com.rhg.qf.ui.activity.AddressActivity;
import com.rhg.qf.ui.activity.DeliverInfoActivity;
import com.rhg.qf.ui.activity.DeliverOrderActivity;
import com.rhg.qf.ui.activity.OrderListActivity;
import com.rhg.qf.utils.AccountUtil;
import com.rhg.qf.utils.DialogUtil;
import com.rhg.qf.utils.ImageUtils;
import com.rhg.qf.utils.ToastHelper;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/**
 * desc:我的fm
 * author：remember
 * time：2016/5/28 16:44
 * email：1013773046@qq.com
 */
public class MyFragment extends BaseFragment implements View.OnClickListener {
    private static final int ORDER_WAIT_PAY = 0;
    private static final int ORDER_DELIVERING = 1;
    private static final int ORDER_COMPLETE = 2;
    private static final int LOGIN = 3;
    private static final int LOG_OUT = 4;
    private static final int ORDER_GRAB = 5;

    ImageView userHeader;
    TextView userName;
    //TODO-------------------------------我的订单栏---------------------------------------------
    TextView myOrder;
    ImageView orderForward;
    TextView orderUnPay;
    TextView orderDelivering;
    TextView orderComplete;
    //TODO---------------------------------我是跑腿员-------------------------------------------
    TextView deliverInfo;
    ImageView deliverForward;
    TextView deliverLogIn;
    TextView deliverOrderGrab;
    TextView deliverLogOut;
    private TextView deliverOrderNum;
    //TODO---------------------------------我的地址-------------------------------------------
    ImageView addressForward;

    boolean isSignIn;
    UmengUtil signUtil = null;
    UserSignInPresenter userSignInPresenter;
    UserSignUpPresenter userSignUpPresenter;
    String nickName;
    String openid;
    String unionid;
    String headImageUrl;

    @Override
    public int getLayoutResId() {
        return R.layout.user_profile_layout_new;
    }

    @Override
    protected void initView(View view) {
        (view.findViewById(R.id.tb_left_iv)).setVisibility(View.GONE);
        ((TextView) view.findViewById(R.id.tb_center_tv)).setText(getString(R.string.User));
        (view.findViewById(R.id.tb_right_ll)).setVisibility(View.GONE);
        (view.findViewById(R.id.fl_tab)).setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorBlueNormal));

        userHeader = (ImageView) view.findViewById(R.id.userHeader);
        userName = (TextView) view.findViewById(R.id.userName);

        //TODO-------------------------------我的订单栏---------------------------------------------
        myOrder = (TextView) getViewById(view, R.id.order_layout, R.id.tv_profile);
        orderForward = (ImageView) getViewById(view, R.id.order_layout, R.id.iv_profile_forward);
        orderUnPay = (TextView) getViewById(view, R.id.order_layout, R.id.tv_profile_left);
        orderDelivering = (TextView) getViewById(view, R.id.order_layout, R.id.tv_profile_center);
        orderComplete = (TextView) getViewById(view, R.id.order_layout, R.id.tv_profile_right);
        TextView orderAll = (TextView) getViewById(view, R.id.order_layout, R.id.tv_profile_subText);
        orderAll.setText("所有订单");

        //TODO---------------------------------我是跑腿员-------------------------------------------
        deliverInfo = (TextView) getViewById(view, R.id.deliver_layout, R.id.tv_profile);
        deliverForward = (ImageView) getViewById(view, R.id.deliver_layout, R.id.iv_profile_forward);
        deliverLogIn = (TextView) getViewById(view, R.id.deliver_layout, R.id.tv_profile_left);
        deliverLogOut = (TextView) getViewById(view, R.id.deliver_layout, R.id.tv_profile_center);
        deliverOrderGrab = (TextView) getViewById(view, R.id.deliver_layout, R.id.tv_profile_right);
        deliverOrderNum = (TextView) getViewById(view, R.id.deliver_layout, R.id.tv_profile_subText);
        //TODO---------------------------------我的地址-------------------------------------------
        addressForward = (ImageView) view.findViewById(R.id.address_forward);
    }

    @Override
    protected void initData() {
        userHeader.setOnClickListener(this);
        userHeader.setTag(R.id.userHeader);
        checkAccount();
        userName.setTag(R.id.userName);
        userName.setOnClickListener(this);

        myOrder.setText(R.string.myOrder);

        orderForward.setOnClickListener(this);
        orderForward.setTag(R.id.order_layout);

        orderUnPay.setText(R.string.orderUnPaid);
        orderUnPay.setOnClickListener(this);
        orderUnPay.setTag(ORDER_WAIT_PAY);

        orderDelivering.setText(R.string.orderDelivering);
        orderDelivering.setOnClickListener(this);
        orderDelivering.setTag(ORDER_DELIVERING);

        orderComplete.setText(R.string.orderComplete);
        orderComplete.setOnClickListener(this);
        orderComplete.setTag(ORDER_COMPLETE);

        deliverInfo.setText(R.string.workerInfo);
        //setTextSize()有两种方法，没有unit参数的方法，默认使用sp为单位的数值进行设置字体大小。
//        deliverInfo.setTextSize(TypedValue.COMPLEX_UNIT_PX, SizeUtil.sp2px(15));
        setDeliverInfo();

        deliverForward.setOnClickListener(this);
        deliverForward.setTag(R.id.deliver_layout);

        deliverLogIn.setText(R.string.workerSignIn);
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_login);
        deliverLogIn.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
        deliverLogIn.setOnClickListener(this);
        deliverLogIn.setTag(LOGIN);

        deliverLogOut.setText(R.string.tvExit);
        deliverLogOut.setOnClickListener(this);
        drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_logout);
        deliverLogOut.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
        deliverLogOut.setTag(LOG_OUT);

        deliverOrderGrab.setText(R.string.deliverOrderGrab);
        drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_grab_order);
        deliverOrderGrab.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
        deliverOrderGrab.setOnClickListener(this);
        deliverOrderGrab.setTag(ORDER_GRAB);


        addressForward.setOnClickListener(this);
        addressForward.setTag(R.id.address_forward);

    }


    private void setDeliverInfo() {
        if (TextUtils.isEmpty(AccountUtil.getInstance().getDeliverID())) {
            deliverOrderNum.setText("跑腿员未登录!");
            deliverOrderNum.setTextColor(Color.BLACK);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                deliverOrderNum.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.number_bg_gray));
            }
        } else {
            setDeliverOrderNum(AccountUtil.getInstance().getDeliverOrderNum());
        }
    }

    private void setDeliverOrderNum(String num) {
        if (num.equals("0") | TextUtils.isEmpty(num))
            deliverOrderNum.setText("您当前未接单");
        else
            deliverOrderNum.setText(String.format("您已经接%s单", num));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            deliverOrderNum.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.number_bg_blue));
        }
    }

    private void checkAccount() {
        Log.i("RHG", "Check account");
        if (AccountUtil.getInstance().hasUserAccount()) {
            isSignIn = true;
            userName.setText(AccountUtil.getInstance().getNickName());
            userName.setClickable(false);
            ImageUtils.showImage(AccountUtil.getInstance().getHeadImageUrl(), userHeader);
        } else {
            isSignIn = false;
            userName.setText(R.string.pleaseSignInAsUser);
            userName.setClickable(true);
            userHeader.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_camera_with_circle));
        }
        if (isSignIn) {
            setDeliverInfo();
        }
    }

    private View getViewById(View parent, int centerId, int targetId) {
        return parent.findViewById(centerId).findViewById(targetId);
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (hasFetchData)
            refresh();
    }

    @Override
    protected void refresh() {
        checkAccount();
    }

    @Override
    protected void showFailed() {
        refresh();
    }

    @Override
    public void showSuccess(Object o) {
        if (o == null) {/*没有登录*/
//            ToastHelper.getInstance()._toast("注册");
            if (userSignUpPresenter == null)
                userSignUpPresenter = new UserSignUpPresenter(this);
            userSignUpPresenter.userSignUp(openid, unionid, headImageUrl, nickName);
            return;
        }
        if (o instanceof String) {
            ToastHelper.getInstance()._toast((o).toString());
            if (userSignInPresenter != null)
                userSignInPresenter.userSignIn(AppConstants.TABLE_CLIENT, openid, unionid);
            return;
        }
        if (o instanceof SignInBackBean.UserInfoBean) {
            userName.setClickable(false);
            isSignIn = true;
            SignInBackBean.UserInfoBean _data = (SignInBackBean.UserInfoBean) o;
            AccountUtil.getInstance().setUserID(_data.getID());
            AccountUtil.getInstance().setHeadImageUrl(_data.getPic());
            AccountUtil.getInstance().setPhoneNumber(_data.getPhonenumber());
            AccountUtil.getInstance().setNickName(nickName);
            AccountUtil.getInstance().setUserName(_data.getCName());
            AccountUtil.getInstance().setPwd(_data.getPwd());
            userName.setText(nickName);
            ImageLoader.getInstance().displayImage(_data.getPic(), userHeader);
            DialogUtil.cancelDialog();
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch ((int) v.getTag()) {
            case R.id.order_layout://TODO 我的订单右箭头
                if (!isSignIn) {
                    ToastHelper.getInstance().displayToastWithQuickClose(getResources().getString(R.string.pleaseSignInAsUser));
                    return;
                }
                intent.setClass(getContext(), OrderListActivity.class);
                intent.putExtra(AppConstants.KEY_ORDER_TAG, 0);
                startActivity(intent);
                break;
            case R.id.deliver_layout://TODO 我是跑腿员右箭头
                if (!isSignIn) {
                    ToastHelper.getInstance().displayToastWithQuickClose(getResources().getString(R.string.pleaseSignInAsUser));
                    break;
                }
                if (TextUtils.isEmpty(AccountUtil.getInstance().getDeliverID())) {
                    ToastHelper.getInstance().displayToastWithQuickClose(getResources().getString(R.string.pleaseSignInAsDeliver));
                    return;
                }

                intent.setClass(getContext(), DeliverOrderActivity.class);
                startActivity(intent);
                break;
            case R.id.address_forward:
                /*获取所有地址*/
                if (!isSignIn) {
                    ToastHelper.getInstance().displayToastWithQuickClose(getResources().getString(R.string.pleaseSignInAsUser));
                    break;
                }
                intent.setClass(getActivity(), AddressActivity.class);
                startActivity(intent);
                break;
            case R.id.userName:
                doLogin();
                break;
            case ORDER_WAIT_PAY:
                if (!isSignIn) {
                    ToastHelper.getInstance().displayToastWithQuickClose(getResources().getString(R.string.pleaseSignInAsUser));
                    break;
                }
                intent.setClass(getContext(), OrderListActivity.class);
                intent.putExtra(AppConstants.KEY_ORDER_TAG, 0);
                startActivity(intent);
                break;
            case ORDER_DELIVERING:
                if (!isSignIn) {
                    ToastHelper.getInstance().displayToastWithQuickClose(getResources().getString(R.string.pleaseSignInAsUser));
                    break;
                }
                intent.setClass(getContext(), OrderListActivity.class);
                intent.putExtra(AppConstants.KEY_ORDER_TAG, 1);
                startActivity(intent);

                break;
            case ORDER_COMPLETE:
                if (!isSignIn) {
                    ToastHelper.getInstance().displayToastWithQuickClose(getResources().getString(R.string.pleaseSignInAsUser));
                    break;
                }
                intent.setClass(getContext(), OrderListActivity.class);
                intent.putExtra(AppConstants.KEY_ORDER_TAG, 2);
                startActivity(intent);

                break;
            case LOGIN:
                if (!isSignIn) {
                    ToastHelper.getInstance().displayToastWithQuickClose(getResources().getString(R.string.pleaseSignInAsUser));
                    break;
                }
                intent.setClass(getContext(), DeliverInfoActivity.class);
                startActivity(intent);
                break;
            case LOG_OUT:
                if (TextUtils.isEmpty(AccountUtil.getInstance().getDeliverID())) {
                    ToastHelper.getInstance().displayToastWithQuickClose("跑腿员未登录！");
                    return;
                }
                AccountUtil.getInstance().deleteDeliverAccount();
                checkAccount();
                break;
            case ORDER_GRAB:
                ToastHelper.getInstance().displayToastWithQuickClose("抢单功能暂未开通！");
//                startActivity(new Intent(getContext(), DeliverRegisterActivity.class));
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        signUtil.onActivityResult(requestCode, resultCode, data);
    }

    /*TODO 登录*/
    private void doLogin() {
        DialogUtil.showDialog(getContext(), "登录中...");
        if (signUtil == null)
            signUtil = new UmengUtil(getActivity());
        signUtil.SignIn(SHARE_MEDIA.WEIXIN, new SignInListener() {
            @Override
            public void signSuccess(Map<String, String> infoMap) {
                openid = infoMap.get(AppConstants.OPENID_WX);
                unionid = infoMap.get(AppConstants.UNIONID_WX);
                nickName = infoMap.get(AppConstants.USERNAME_WX);
                headImageUrl = infoMap.get(AppConstants.PROFILE_IMAGE_WX);
                if (userSignInPresenter == null)
                    userSignInPresenter = new UserSignInPresenter(MyFragment.this);
                userSignInPresenter.userSignIn(AppConstants.TABLE_CLIENT,
                        openid, unionid);
            }

            @Override
            public void signFail(String errorMessage) {
            }
        });
    }
}
