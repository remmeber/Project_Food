package com.rhg.qf.ui.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easemob.easeui.EaseConstant;
import com.rhg.qf.R;
import com.rhg.qf.bean.AddressUrlBean;
import com.rhg.qf.bean.NewOrderBackBean;
import com.rhg.qf.bean.NewOrderBean;
import com.rhg.qf.bean.SignInBackBean;
import com.rhg.qf.constants.AppConstants;
import com.rhg.qf.impl.SignInListener;
import com.rhg.qf.mvp.presenter.GetAddressPresenter;
import com.rhg.qf.mvp.presenter.NewOrderPresenter;
import com.rhg.qf.mvp.presenter.UserSignInPresenter;
import com.rhg.qf.mvp.presenter.UserSignUpPresenter;
import com.rhg.qf.third.UmengUtil;
import com.rhg.qf.ui.UIAlertView;
import com.rhg.qf.utils.AccountUtil;
import com.rhg.qf.utils.DialogUtil;
import com.rhg.qf.utils.NetUtil;
import com.rhg.qf.utils.ToastHelper;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/*
 *desc 
 *author rhg
 *time 2016/9/25 20:51
 *email 1013773046@qq.com
 */
public class PersonalOrderActivity extends BaseAppcompactActivity {
    @Bind(R.id.tb_center_tv)
    TextView tbCenterTv;
    @Bind(R.id.tb_right_ll)
    LinearLayout tbRightLl;
    @Bind(R.id.tb_left_iv)
    ImageView tbLeftIv;
    @Bind(R.id.fl_tab)
    FrameLayout flTab;
    UserSignInPresenter userSignInPresenter;
    UserSignUpPresenter userSignUpPresenter;
    GetAddressPresenter getAddressPresenter;
    NewOrderPresenter createOrderPresenter;
    String nickName;
    String openid;
    String unionid;
    String headImageUrl;
    private UmengUtil signUtil;

    @Override
    protected void initData() {
        tbCenterTv.setText(R.string.personalOrder);
        tbRightLl.setVisibility(View.GONE);
        tbLeftIv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_chevron_left_black));
        flTab.setBackgroundColor(ContextCompat.getColor(this, R.color.colorBlueNormal));
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.personal_order_layout;
    }

    @Override
    public void showSuccess(Object o) {
        if (o == null) {/*没有登录成功*/
            if (userSignUpPresenter == null)
                userSignUpPresenter = new UserSignUpPresenter(this);
            userSignUpPresenter.userSignUp(openid, unionid, headImageUrl, nickName);
            return;
        }
        if (o instanceof String) {
            if ("success".equals(o)) {
                if (userSignInPresenter == null)
                    userSignInPresenter = new UserSignInPresenter(this);
                userSignInPresenter.userSignIn(AppConstants.TABLE_CLIENT, openid, unionid);
            } else {
                ToastHelper.getInstance().displayToastWithQuickClose("自主点餐失败");
                DialogUtil.cancelDialog();
            }
            return;
        }
        if (o instanceof SignInBackBean.UserInfoBean) {
            ToastHelper.getInstance()._toast("登录成功");
            SignInBackBean.UserInfoBean _data = (SignInBackBean.UserInfoBean) o;
            AccountUtil.getInstance().setUserID(_data.getID());
            AccountUtil.getInstance().setHeadImageUrl(_data.getPic());
            AccountUtil.getInstance().setPhoneNumber(_data.getPhonenumber());
            AccountUtil.getInstance().setUserName(_data.getCName());
            AccountUtil.getInstance().setNickName(nickName);
            AccountUtil.getInstance().setPwd(_data.getPwd());
            return;
        }
        if (o instanceof AddressUrlBean.AddressBean) {
//            generateOrder((AddressUrlBean.AddressBean) o);
            if (createOrderPresenter == null)
                createOrderPresenter = new NewOrderPresenter(this);
            createOrderPresenter.createNewOrder(generateOrder((AddressUrlBean.AddressBean) o));
        }
        if (o instanceof NewOrderBackBean) {
            DialogUtil.cancelDialog();
            startActivity(new Intent(this, ChatActivity.class)
                    .putExtra(EaseConstant.EXTRA_USER_ID, AppConstants.CUSTOMER_SERVER));
        }
    }

    @Override
    public void showError(Object s) {
    }


    @OnClick({R.id.tb_left_iv, R.id.ivPersonalBackground})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tb_left_iv:
                finish();
                break;
            case R.id.ivPersonalBackground:
                if (!NetUtil.isConnected(this)) {
                    ToastHelper.getInstance()._toast("网络未连接");
                }
                if (!AccountUtil.getInstance().hasAccount()) {
                    signInDialogShow("登录后才能享受自主点单哦！");
                    return;
                }
                DialogUtil.showDialog(this, "跳转中....");
                if (getAddressPresenter == null)
                    getAddressPresenter = new GetAddressPresenter(this);
                getAddressPresenter.getAddress(AppConstants.TABLE_DEFAULT_ADDRESS);
                break;
        }
    }

    private NewOrderBean generateOrder(AddressUrlBean.AddressBean addressBean) {
        NewOrderBean _orderBean = new NewOrderBean();
        _orderBean.setReceiver(addressBean.getName());
        _orderBean.setPhone(addressBean.getPhone());
        _orderBean.setAddress(addressBean.getAddress().concat(addressBean.getDetail()));
        _orderBean.setFood(getFoodList());
        _orderBean.setClient(AccountUtil.getInstance().getUserID());
        _orderBean.setPrice("1");
        return _orderBean;
    }

    private List<NewOrderBean.FoodBean> getFoodList() {
        List<NewOrderBean.FoodBean> _bean = new ArrayList<>();
        NewOrderBean.FoodBean foodBean = new NewOrderBean.FoodBean();
        foodBean.setID("0");
        foodBean.setNum("1");
        _bean.add(foodBean);

        return _bean;
    }

    private void signInDialogShow(String content) {
        final UIAlertView delDialog = new UIAlertView(this, "温馨提示", content,
                "取消", "登录");
        delDialog.show();
        delDialog.setClicklistener(new UIAlertView.ClickListenerInterface() {
                                       @Override
                                       public void doLeft() {
                                           delDialog.dismiss();
                                       }

                                       @Override
                                       public void doRight() {
                                           doLogin();
                                           delDialog.dismiss();
                                       }
                                   }
        );
    }


    private void doLogin() {
        if (signUtil == null)
            signUtil = new UmengUtil(this);
        signUtil.SignIn(SHARE_MEDIA.WEIXIN, new SignInListener() {
            @Override
            public void signSuccess(Map<String, String> infoMap) {
                openid = infoMap.get(AppConstants.OPENID_WX);
                unionid = infoMap.get(AppConstants.UNIONID_WX);
                nickName = infoMap.get(AppConstants.USERNAME_WX);
                headImageUrl = infoMap.get(AppConstants.PROFILE_IMAGE_WX);
                if (userSignInPresenter == null)
                    userSignInPresenter = new UserSignInPresenter(PersonalOrderActivity.this);
                userSignInPresenter.userSignIn(AppConstants.TABLE_CLIENT,
                        openid, unionid);
            }

            @Override
            public void signFail(String errorMessage) {
//                ToastHelper.getInstance()._toast(errorMessage);
            }
        });
    }

}
