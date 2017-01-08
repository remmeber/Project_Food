package com.rhg.qf.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.easemob.EMCallBack;
import com.easemob.EMError;
import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.easemob.easeui.EaseConstant;
import com.easemob.easeui.ui.EaseBaseActivity;
import com.easemob.easeui.ui.EaseChatFragment;
import com.easemob.exceptions.EaseMobException;
import com.rhg.qf.R;
import com.rhg.qf.bean.BaseAddress;
import com.rhg.qf.bean.IBaseModel;
import com.rhg.qf.bean.NewOrderBackBean;
import com.rhg.qf.bean.NewOrderBean;
import com.rhg.qf.constants.AppConstants;
import com.rhg.qf.mvp.presenter.GetAddressPresenter;
import com.rhg.qf.mvp.presenter.NewOrderPresenter;
import com.rhg.qf.mvp.view.BaseView;
import com.rhg.qf.runtimepermissions.PermissionsManager;
import com.rhg.qf.utils.AccountUtil;
import com.rhg.qf.utils.CustomerHelper;
import com.rhg.qf.utils.DialogUtil;
import com.rhg.qf.utils.ToastHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends EaseBaseActivity implements BaseView {
    public static ChatActivity activityInstance;
    String toChatUsername;
    String uname;
    private EaseChatFragment chatFragment;
    GetAddressPresenter getAddressPresenter;
    NewOrderPresenter createOrderPresenter;
    BaseAddress addressBean;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        CustomerHelper.getInstance().setEaseUIProviders();
        CustomerHelper.getInstance().registerEventListener();

        activityInstance = this;
        uname = "QF" + dealUName(AccountUtil.getInstance().getNickName());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            checkPermissionAndSetIfNecessary(new String[]{Manifest.permission_group.MICROPHONE});
        doLogin();
    }

    private void checkPermissionAndSetIfNecessary(String[] permissions) {
        if (!PermissionsManager.getInstance().hasAllPermissions(this, permissions)) {
            PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(this,
                    permissions, null);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {
            if (data == null) {
                ToastHelper.getInstance()._toast("点单失败");
                if (DialogUtil.isShow())
                    DialogUtil.cancelDialog();
                return;
            }
            addressBean = data.getParcelableExtra(AppConstants.ADDRESS_DEFAULT);
            if (addressBean == null) {
                ToastHelper.getInstance()._toast("点单失败，请填写详细地址");
                if (DialogUtil.isShow())
                    DialogUtil.cancelDialog();
                return;
            }
            this.showData(addressBean);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                final String permission = permissions[i];
                onDeny(permission);
                return;
            }
        }
        onGrant();
    }

    private void onGrant() {
    }

    private void onDeny(final String permission) {
        Snackbar.make(getWindow().getDecorView(), "授权失败，将影响您的体验", Snackbar.LENGTH_LONG)
                .setAction("重新授权", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(ChatActivity.this,
                                new String[]{permission}, null);
                    }
                }).show();

    }

    private void doLogin() {
        if (EMChat.getInstance().isLoggedIn()) {
            initView();
        } else
            createAccountToServer(uname, getPwd(uname), new EMCallBack() {
                @Override
                public void onSuccess() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loginToServer(uname, getPwd(uname));
                        }
                    });
                }

                @Override
                public void onError(final int i, String s) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (i == EMError.NONETWORK_ERROR) {
                                ToastHelper.getInstance()._toast("网络不可用");
                            } else if (i == EMError.USER_ALREADY_EXISTS) {
                                loginToServer(uname, getPwd(uname));
                            }
                        }
                    });
                }

                @Override
                public void onProgress(int i, String s) {

                }
            });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initView() {
        toChatUsername = getIntent().getExtras().getString(EaseConstant.EXTRA_USER_ID);
        chatFragment = new EaseChatFragment();
        //传入参数
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
    }

    private void loginToServer(String nickName, String pwd) {
        EMChatManager.getInstance().login(nickName, pwd, new EMCallBack() {
            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initView();
                    }
                });
            }

            @Override
            public void onError(final int i, final String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
                //Log.i("RHG", "登录失败" + " i= " + i + " s= " + s);
            }

            @Override
            public void onProgress(int i, String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastHelper.getInstance()._toast("正在获取客服聊天...");

                    }
                });
            }
        });
    }

    private String getPwd(String nickName) {
        return "jjms" + nickName + "jjms";
    }

    /*去除中文*/
    private String dealUName(String uname) {
        String reg = "[\u4e00-\u9fa5]";
        Pattern pat = Pattern.compile(reg);
        Matcher mat = pat.matcher(uname);
        String s = mat.replaceAll("");
        return s.replace(" ", "");
    }

    private void createAccountToServer(final String uname, final String pwd, final EMCallBack callBack) {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMChatManager.getInstance().createAccountOnServer(uname, pwd);
                    if (callBack != null)
                        callBack.onSuccess();
                } catch (EaseMobException e) {
                    if (callBack != null)
                        callBack.onError(e.getErrorCode(), e.getMessage());
                }
            }
        });
        thread.start();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        activityInstance = null;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // 点击notification bar进入聊天页面，保证只有一个聊天页面
        String username = intent.getStringExtra(EaseConstant.EXTRA_USER_ID);
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }

    }

    @Override
    public void onBackPressed() {
        if (chatFragment != null)
            chatFragment.onBackPressed();
        else finish();
    }

    public String getToChatUsername() {
        return toChatUsername;
    }

    @OnClick(R.id.fab)
    public void onClick() {
        DialogUtil.showDialog(this, "下单中！");
        getAddress();
    }

    private void getAddress() {
        if (getAddressPresenter == null)
            getAddressPresenter = new GetAddressPresenter(this);
        getAddressPresenter.getAddress(AppConstants.TABLE_DEFAULT_ADDRESS);
    }

    @Override
    public void showData(Object o) {
        if (o instanceof NewOrderBackBean) {
            DialogUtil.cancelDialog();
            startActivity(new Intent(this, ChatActivity.class)
                    .putExtra(EaseConstant.EXTRA_USER_ID, AppConstants.CUSTOMER_SERVER));
            return;
        }

        if (o instanceof IBaseModel) {
            if (((IBaseModel) o).getEntity().size() == 0) {
                Intent intent = new Intent(this, AddressActivity.class);
                intent.setAction(AppConstants.ADDRESS_DEFAULT);
                startActivityForResult(intent, 0);
                return;
            }
            addressBean = (BaseAddress) ((IBaseModel) o).getEntity().get(0);
            if (createOrderPresenter == null)
                createOrderPresenter = new NewOrderPresenter(this);
            createOrderPresenter.createNewOrder(generateOrder(addressBean));
        }
    }


    private NewOrderBean generateOrder(BaseAddress addressBean) {
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
}
