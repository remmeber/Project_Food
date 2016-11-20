package com.rhg.qf.ui.fragment;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
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
import com.rhg.qf.ui.activity.AddOrNewAddressActivity;
import com.rhg.qf.ui.activity.AddressActivity;
import com.rhg.qf.ui.activity.DeliverInfoActivity;
import com.rhg.qf.ui.activity.DeliverOrderActivity;
import com.rhg.qf.ui.activity.OrderListActivity;
import com.rhg.qf.utils.AccountUtil;
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
    TextView workerInfo;
    ImageView workerForward;
    TextView workerSignIn;
    TextView workerSignUp;
    TextView workerModify;
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
        workerInfo = (TextView) getViewById(view, R.id.profileWorker, R.id.profileInfo);
        workerForward = (ImageView) getViewById(view, R.id.profileWorker, R.id.profileForward);
        workerSignIn = (TextView) getViewById(view, R.id.profileWorker, R.id.profileDealleft);
        workerSignUp = (TextView) getViewById(view, R.id.profileWorker, R.id.profileDealcenter);
        workerModify = (TextView) getViewById(view, R.id.profileWorker, R.id.profileDealright);
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

        myCancel.setText(R.string.cancel);
        myCancel.setOnClickListener(this);
        myCancel.setTag(1);

        myComplete.setText(R.string.orderComplete);
        myComplete.setOnClickListener(this);
        myComplete.setTag(2);

        workerInfo.setText(R.string.workerInfo);
        //setTextSize()有两种方法，没有unit参数的方法，默认使用sp为单位的数值进行设置字体大小。
        workerInfo.setTextSize(TypedValue.COMPLEX_UNIT_PX, SizeUtil.sp2px(15));

        workerForward.setOnClickListener(this);
        workerForward.setTag(R.id.profileWorker);

        workerSignIn.setText(R.string.workerSignIn);
        workerSignIn.setOnClickListener(this);
        workerSignIn.setTag(3);

        workerSignUp.setText(R.string.wokerSignUp);
        workerSignUp.setOnClickListener(this);
        workerSignUp.setTag(4);

        workerModify.setText(R.string.wokerAndAddrModify);
        workerModify.setOnClickListener(this);
        workerModify.setTag(5);

        addressInfo.setText(R.string.addrInfo);

        addressForward.setOnClickListener(this);
        addressForward.setTag(R.id.profileAddress);

        addressCustome.setText(R.string.custome);
        addressCustome.setOnClickListener(this);
        addressCustome.setTag(6);

        addressAdd.setText(R.string.add);
        addressAdd.setOnClickListener(this);
        addressAdd.setTag(7);

        addressModify.setText(R.string.wokerAndAddrModify);
        addressModify.setOnClickListener(this);
        addressModify.setTag(8);
    }

    private void checkAccount() {
        if (AccountUtil.getInstance().hasAccount()) {
            userName.setText(AccountUtil.getInstance().getNickName());
            userName.setClickable(false);
            ImageUtils.showImage(AccountUtil.getInstance().getHeadImageUrl(),
                    userHeader);
            isSignIn = true;
        } else {
            userName.setText("请登录");
            userName.setClickable(true);
            isSignIn = false;
            userHeader.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_camera_with_circle));
        }
    }

    private View getViewById(View parent, int centerId, int targetId) {
        return parent.findViewById(centerId).findViewById(targetId);
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(hasFetchData)
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
        if (o == null) {/*没有登录成功*/
            ToastHelper.getInstance()._toast("注册");
            if (userSignUpPresenter == null)
                userSignUpPresenter = new UserSignUpPresenter(this);
            userSignUpPresenter.userSignUp(openid, unionid, headImageUrl, nickName);
        }
        if (o instanceof String) {
            ToastHelper.getInstance()._toast((o).toString());
            if (userSignInPresenter != null)
                userSignInPresenter.userSignIn(AppConstants.TABLE_CLIENT, openid, unionid);
        }
        if (o instanceof SignInBackBean.UserInfoBean) {
            ToastHelper.getInstance()._toast("登录成功");
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
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch ((int) v.getTag()) {
            case R.id.profileInfo://TODO 我的订单右箭头
                if (!isSignIn) {
                    ToastHelper.getInstance().displayToastWithQuickClose("请登录");
                    break;
                }
                intent.setClass(getContext(), OrderListActivity.class);
                intent.putExtra(AppConstants.KEY_ORDER_TAG, 0);
                startActivity(intent);
                break;
            case R.id.profileWorker://TODO 我是跑腿员右箭头
//                if (isSignIn)
                if (!isSignIn) {
                    ToastHelper.getInstance().displayToastWithQuickClose("请登录");
                    break;
                }

                intent.setClass(getContext(), /*DeliverInfoActivity*/DeliverOrderActivity.class);
                startActivity(intent);
                /*else
                    ToastHelper.getInstance()._toast("请登录");*/
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
                    ToastHelper.getInstance().displayToastWithQuickClose("请登录");
                    break;
                }
                intent.setClass(getContext(), OrderListActivity.class);
                intent.putExtra(AppConstants.KEY_ORDER_TAG, 0);
                startActivity(intent);
                break;
            case 1://TODO  取消
                if (!isSignIn) {
                    ToastHelper.getInstance().displayToastWithQuickClose("请登录");
                    break;
                }
                intent.setClass(getContext(), OrderListActivity.class);
                intent.putExtra(AppConstants.KEY_ORDER_TAG, 1);
                startActivity(intent);

                break;
            case 2://TODO 已完成
                if (!isSignIn) {
                    ToastHelper.getInstance().displayToastWithQuickClose("请登录");
                    break;
                }
                intent.setClass(getContext(), OrderListActivity.class);
                intent.putExtra(AppConstants.KEY_ORDER_TAG, 2);
                startActivity(intent);

                break;
            case 3://TODO 登录
//                doLogin();
                break;
            case 4://TODO 注册
//                startActivity(new Intent(getContext(), DeliverRegisterActivity.class));
                break;
            case 5://TODO 修改
                if (!isSignIn) {
                    ToastHelper.getInstance().displayToastWithQuickClose("请登录");
                    break;
                }
                intent.setClass(getContext(), DeliverInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.profileAddress://TODO 我的地址右箭头
            case 6://TODO 常用
            case 8://TODO 修改
                /*获取所有地址*/
                if (!isSignIn) {
                    ToastHelper.getInstance().displayToastWithQuickClose("请登录");
                    break;
                }
                intent.setClass(getActivity(), AddressActivity.class);
                startActivity(intent);
                break;
            case 7://TODO 添加
                if (!isSignIn) {
                    ToastHelper.getInstance().displayToastWithQuickClose("请登录");
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
                /*userName.setText(infoMap.get(AppConstants.USERNAME_QQ));
                ImageLoader.getInstance().displayImage(infoMap.get(AppConstants.PROFILE_IMAGE_QQ),
                        userHeader);
                signUtil.setActivity(null);*/
            }

            @Override
            public void signFail(String errorMessage) {
//                ToastHelper.getInstance()._toast(errorMessage);
            }
        });
    }
}
