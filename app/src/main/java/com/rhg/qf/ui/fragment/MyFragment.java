package com.rhg.qf.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewStub;
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
import com.rhg.qf.ui.activity.AddOrNewAddressActivity;
import com.rhg.qf.ui.activity.AddressActivity;
import com.rhg.qf.ui.activity.DeliverInfoActivity;
import com.rhg.qf.ui.activity.DeliverOrderActivity;
import com.rhg.qf.ui.activity.OrderListActivity;
import com.rhg.qf.utils.AccountUtil;
import com.rhg.qf.utils.DialogUtil;
import com.rhg.qf.utils.ImageUtils;
import com.rhg.qf.utils.SizeUtil;
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
    ImageView userHeader;
    TextView userName;
    //TODO-------------------------------我的订单栏---------------------------------------------
    TextView myInfo;
    ImageView myForward;
    TextView myPay;
    TextView myCancel;
    TextView myComplete;
    //TODO---------------------------------我是跑腿员-------------------------------------------
    TextView deliverInfo;
    ImageView deliverForward;
    TextView deliverSignIn;
    TextView deliverSignUp;
    TextView deliverModify;
    TextView deliverState;
    //TODO---------------------------------我的地址-------------------------------------------
    TextView addressInfo;
    ImageView addressForward;
    TextView addressCustome;
    TextView addressAdd;
    TextView addressModify;

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
        return R.layout.user_profile_layout;
    }

    @Override
    protected void initView(View view) {
//        flTAB = (FrameLayout) view.findViewById(R.id.fl_tab);

        userHeader = (ImageView) view.findViewById(R.id.userHeader);
        userName = (TextView) view.findViewById(R.id.userName);

        //TODO-------------------------------我的订单栏---------------------------------------------
        myInfo = (TextView) getViewById(view, R.id.profileOrder, R.id.profileInfo);
        myForward = (ImageView) getViewById(view, R.id.profileOrder, R.id.profileForward);
        myPay = (TextView) getViewById(view, R.id.profileOrder, R.id.profileDealleft);
        myCancel = (TextView) getViewById(view, R.id.profileOrder, R.id.profileDealcenter);
        myComplete = (TextView) getViewById(view, R.id.profileOrder, R.id.profileDealright);
        //TODO---------------------------------我是跑腿员-------------------------------------------
        deliverInfo = (TextView) getViewById(view, R.id.profileDeliver, R.id.profileInfo);
        deliverForward = (ImageView) getViewById(view, R.id.profileDeliver, R.id.profileForward);
        deliverSignIn = (TextView) getViewById(view, R.id.profileDeliver, R.id.profileDealleft);
        deliverSignUp = (TextView) getViewById(view, R.id.profileDeliver, R.id.profileDealcenter);
        deliverModify = (TextView) getViewById(view, R.id.profileDeliver, R.id.profileDealright);
        ViewStub viewStub = (ViewStub) getViewById(view, R.id.profileDeliver, R.id.profileDeliverNum);
        viewStub.inflate();
        deliverState = (TextView) view.findViewById(R.id.tv_deliver_state);
        //TODO---------------------------------我的地址-------------------------------------------
        addressInfo = (TextView) getViewById(view, R.id.profileAddress, R.id.profileInfo);
        addressForward = (ImageView) getViewById(view, R.id.profileAddress, R.id.profileForward);
        addressCustome = (TextView) getViewById(view, R.id.profileAddress, R.id.profileDealleft);
        addressAdd = (TextView) getViewById(view, R.id.profileAddress, R.id.profileDealcenter);
        addressModify = (TextView) getViewById(view, R.id.profileAddress, R.id.profileDealright);
    }

    @Override
    protected void initData() {
        userHeader.setOnClickListener(this);
        userHeader.setTag(R.id.userHeader);
        checkAccount();
        userName.setTag(R.id.userName);
        userName.setOnClickListener(this);

        myInfo.setText(R.string.myOrder);

        myForward.setOnClickListener(this);
        myForward.setTag(R.id.profileInfo);

        myPay.setText(R.string.orderUnPaid);
        myPay.setOnClickListener(this);
        myPay.setTag(0);

        myCancel.setText(R.string.orderDelivering);
        myCancel.setOnClickListener(this);
        myCancel.setTag(1);

        myComplete.setText(R.string.orderComplete);
        myComplete.setOnClickListener(this);
        myComplete.setTag(2);

        deliverInfo.setText(R.string.workerInfo);
        //setTextSize()有两种方法，没有unit参数的方法，默认使用sp为单位的数值进行设置字体大小。
        deliverInfo.setTextSize(TypedValue.COMPLEX_UNIT_PX, SizeUtil.sp2px(15));
        setDeliverInfo();

        deliverForward.setOnClickListener(this);
        deliverForward.setTag(R.id.profileDeliver);

        deliverSignIn.setText(R.string.workerSignIn);
        deliverSignIn.setOnClickListener(this);
        deliverSignIn.setTag(3);

        deliverSignUp.setText(R.string.deliverSignUp);
        deliverSignUp.setOnClickListener(this);
        deliverSignUp.setTag(4);

        deliverModify.setText(R.string.tvExit);
        deliverModify.setOnClickListener(this);
        deliverModify.setTag(5);

        addressInfo.setText(R.string.addrInfo);

        addressForward.setOnClickListener(this);
        addressForward.setTag(R.id.profileAddress);

        addressCustome.setText(R.string.custome);
        addressCustome.setOnClickListener(this);
        addressCustome.setTag(6);

        addressAdd.setText(R.string.add);
        addressAdd.setOnClickListener(this);
        addressAdd.setTag(7);

        addressModify.setText(R.string.deliverAndAddrModify);
        addressModify.setOnClickListener(this);
        addressModify.setTag(8);
    }

    private void setDeliverInfo() {
        if (TextUtils.isEmpty(AccountUtil.getInstance().getDeliverID())) {
            deliverState.setText("跑腿员未登录!");
            deliverState.setTextColor(Color.BLACK);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                deliverState.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.number_bg_gray));
            }
        } else {
            setDeliverNum(AccountUtil.getInstance().getDeliverOrderNum());
        }
    }

    private void setDeliverNum(String deliverOrderNum) {
        if (deliverOrderNum.equals("0") | TextUtils.isEmpty(deliverOrderNum))
            deliverState.setText("您当前未接单");
        else
            deliverState.setText(String.format("您已经接%s单", deliverOrderNum));
        deliverState.setTextColor(Color.WHITE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            deliverState.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.number_bg_blue));
        }
    }

    private void checkAccount() {
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
        Log.i("RHG", "账户校验");
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
            case R.id.profileInfo://TODO 我的订单右箭头
                if (!isSignIn) {
                    ToastHelper.getInstance().displayToastWithQuickClose(getResources().getString(R.string.pleaseSignInAsUser));
                    return;
                }
                intent.setClass(getContext(), OrderListActivity.class);
                intent.putExtra(AppConstants.KEY_ORDER_TAG, 0);
                startActivity(intent);
                break;
            case R.id.profileDeliver://TODO 我是跑腿员右箭头
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
            /*case R.id.userHeader://TODO 更改头像
                if (modifyHeadImageDialog == null) {
                    modifyHeadImageDialog = new ModifyHeadImageDialog(getContext());
                    modifyHeadImageDialog.setChoosePicListener(this);
                }
                modifyHeadImageDialog.show();
                break;*/
            case R.id.userName://TODO 点击登录
                doLogin();
                break;
            case 0://TODO 待付款
                if (!isSignIn) {
                    ToastHelper.getInstance().displayToastWithQuickClose(getResources().getString(R.string.pleaseSignInAsUser));
                    break;
                }
                intent.setClass(getContext(), OrderListActivity.class);
                intent.putExtra(AppConstants.KEY_ORDER_TAG, 0);
                startActivity(intent);
                break;
            case 1://TODO  进行中
                if (!isSignIn) {
                    ToastHelper.getInstance().displayToastWithQuickClose(getResources().getString(R.string.pleaseSignInAsUser));
                    break;
                }
                intent.setClass(getContext(), OrderListActivity.class);
                intent.putExtra(AppConstants.KEY_ORDER_TAG, 1);
                startActivity(intent);

                break;
            case 2://TODO 已完成
                if (!isSignIn) {
                    ToastHelper.getInstance().displayToastWithQuickClose(getResources().getString(R.string.pleaseSignInAsUser));
                    break;
                }
                intent.setClass(getContext(), OrderListActivity.class);
                intent.putExtra(AppConstants.KEY_ORDER_TAG, 2);
                startActivity(intent);

                break;
            case 3://TODO 登录
                if (!isSignIn) {
                    ToastHelper.getInstance().displayToastWithQuickClose(getResources().getString(R.string.pleaseSignInAsUser));
                    break;
                }
                intent.setClass(getContext(), DeliverInfoActivity.class);
                startActivity(intent);
                break;
            case 4://TODO 注册
//                startActivity(new Intent(getContext(), DeliverRegisterActivity.class));
                break;
            case 5://TODO 修改
                if (TextUtils.isEmpty(AccountUtil.getInstance().getDeliverID())) {
                    ToastHelper.getInstance().displayToastWithQuickClose("跑腿员未登录！");
                    return;
                }
                AccountUtil.getInstance().deleteDeliverAccount();
                checkAccount();
                break;
            case R.id.profileAddress://TODO 我的地址右箭头
            case 6://TODO 常用
            case 8://TODO 修改
                /*获取所有地址*/
                if (!isSignIn) {
                    ToastHelper.getInstance().displayToastWithQuickClose(getResources().getString(R.string.pleaseSignInAsUser));
                    break;
                }
                intent.setClass(getActivity(), AddressActivity.class);
                startActivity(intent);
                break;
            case 7://TODO 添加
                if (!isSignIn) {
                    ToastHelper.getInstance().displayToastWithQuickClose(getResources().getString(R.string.pleaseSignInAsUser));
                    break;
                }
                intent.setClass(getActivity(), AddOrNewAddressActivity.class);
                startActivity(intent);
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
