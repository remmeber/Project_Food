package com.rhg.qf.ui.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easemob.easeui.EaseConstant;
import com.rhg.qf.R;
import com.rhg.qf.bean.AddressUrlBean;
import com.rhg.qf.bean.PayModel;
import com.rhg.qf.bean.SignInBackBean;
import com.rhg.qf.constants.AppConstants;
import com.rhg.qf.impl.SignInListener;
import com.rhg.qf.mvp.presenter.GetAddressPresenter;
import com.rhg.qf.mvp.presenter.UserSignInPresenter;
import com.rhg.qf.mvp.presenter.UserSignUpPresenter;
import com.rhg.qf.third.UmengUtil;
import com.rhg.qf.ui.UIAlertView;
import com.rhg.qf.utils.AccountUtil;
import com.rhg.qf.utils.ToastHelper;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
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
    @Bind(R.id.etNum)
    EditText etNum;
    @Bind(R.id.tb_left_iv)
    ImageView tbLeftIv;
    @Bind(R.id.fl_tab)
    FrameLayout flTab;
    UserSignInPresenter userSignInPresenter;
    UserSignUpPresenter userSignUpPresenter;
    GetAddressPresenter getAddressPresenter;
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
    protected void showSuccess(Object o) {
        if (o == null) {/*没有登录成功*/
            if (userSignUpPresenter == null)
                userSignUpPresenter = new UserSignUpPresenter(this);
            userSignUpPresenter.userSignUp(openid, unionid, headImageUrl, nickName);
            return;
        }
        if (o instanceof String && "success".equals(o)) {
            if (userSignInPresenter != null)
                userSignInPresenter.userSignIn(AppConstants.TABLE_CLIENT, openid, unionid);
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
            if (getAddressPresenter == null)
                getAddressPresenter = new GetAddressPresenter(this);
            getAddressPresenter.getAddress(AppConstants.ADDRESS_DEFAULT);
            return;
        }
        if (o instanceof AddressUrlBean.AddressBean) {
            Log.i("RHG", "BACK ADDRESS");
            createOrderAndToPay((AddressUrlBean.AddressBean) o);
        }
    }

    @Override
    protected void showError(Object s) {
    }


    @Override
    public void keyBoardHide() {
        if (TextUtils.isEmpty(etNum.getText())) {
            etNum.setText("0");
        }
    }

    @OnClick({R.id.tb_left_iv, R.id.ivPersonalBackground, R.id.ivReduce, R.id.ivAdd,
            R.id.btPersonalOrderPay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tb_left_iv:
                finish();
                break;
            case R.id.ivPersonalBackground:
                if (!AccountUtil.getInstance().hasAccount()) {
                    ToastHelper.getInstance().displayToastWithQuickClose("请登录");
                    return;
                }
                startActivity(new Intent(this, ChatActivity.class)
                        .putExtra(EaseConstant.EXTRA_USER_ID, AppConstants.CUSTOMER_SERVER));
                break;
            case R.id.ivReduce:
                int num = Integer.valueOf(etNum.getText().toString());
                if (num == 0)
                    return;
                etNum.setText(String.valueOf(num - 1));
                break;
            case R.id.ivAdd:
                etNum.setText(String.valueOf(Integer.valueOf(etNum.getText().toString()) + 1));
                break;
            case R.id.btPersonalOrderPay:
                if (!AccountUtil.getInstance().hasAccount()) {
                    signInDialogShow("登录后才能享受自主点餐哦！");
                    return;
                }
                int pay_num = Integer.valueOf(etNum.getText().toString());
                if (pay_num == 0) {
                    ToastHelper.getInstance()._toast("请输入正确价格");
                    return;
                }
                if (getAddressPresenter == null)
                    getAddressPresenter = new GetAddressPresenter(this);
                getAddressPresenter.getAddress(AppConstants.TABLE_DEFAULT_ADDRESS);
                break;
        }
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

    private void createOrderAndToPay(AddressUrlBean.AddressBean addressBean) {
        Intent intent = new Intent(PersonalOrderActivity.this,
                PayActivity.class);
        PayModel payModel = new PayModel();
        payModel.setReceiver(addressBean.getName());
        payModel.setPhone(addressBean.getPhone());
        payModel.setAddress(addressBean.getAddress().concat(addressBean.getDetail()));
        ArrayList<PayModel.PayBean> payBeen = new ArrayList<>();
        PayModel.PayBean _pay = new PayModel.PayBean();
        _pay.setMerchantName(getResources().getString(R.string.personalOrder));
        _pay.setProductName(getResources().getString(R.string.personalOrder));
        _pay.setChecked(true);
        _pay.setProductId("0");
        _pay.setProductNumber("1");
        _pay.setProductPic("");
        _pay.setProductPrice(etNum.getText().toString());
        payBeen.add(_pay);
        payModel.setPayBeanList(payBeen);
        intent.putExtra(AppConstants.KEY_PARCELABLE, payModel);
        startActivity(intent);
    }

}
