package com.rhg.qf.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.rhg.qf.R;
import com.rhg.qf.adapter.viewHolder.BannerImageHolder;
import com.rhg.qf.application.InitApplication;
import com.rhg.qf.bean.AddressUrlBean;
import com.rhg.qf.bean.FoodInfoBean;
import com.rhg.qf.bean.GoodsDetailUrlBean;
import com.rhg.qf.bean.PayModel;
import com.rhg.qf.bean.ShareModel;
import com.rhg.qf.bean.ShoppingCartBean;
import com.rhg.qf.bean.SignInBackBean;
import com.rhg.qf.constants.AppConstants;
import com.rhg.qf.datebase.AccountDao;
import com.rhg.qf.impl.ShareListener;
import com.rhg.qf.impl.SignInListener;
import com.rhg.qf.locationservice.LocationService;
import com.rhg.qf.locationservice.MyLocationListener;
import com.rhg.qf.mvp.presenter.GetAddressPresenter;
import com.rhg.qf.mvp.presenter.GoodsDetailPresenter;
import com.rhg.qf.mvp.presenter.UserSignInPresenter;
import com.rhg.qf.mvp.presenter.UserSignUpPresenter;
import com.rhg.qf.mvp.presenter.contact.GoodsDetailContact;
import com.rhg.qf.runtimepermissions.PermissionsManager;
import com.rhg.qf.third.UmengUtil;
import com.rhg.qf.ui.UIAlertView;
import com.rhg.qf.utils.AccountUtil;
import com.rhg.qf.utils.DecimalUtil;
import com.rhg.qf.utils.ShoppingCartUtil;
import com.rhg.qf.utils.ToastHelper;
import com.rhg.qf.widget.ShoppingCartWithNumber;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * desc:商品详情页面
 * author：remember
 * time：2016/5/28 16:14
 * email：1013773046@qq.com
 */
public class GoodsDetailActivity extends BaseAppcompactActivity<GoodsDetailPresenter> implements GoodsDetailContact.View<GoodsDetailUrlBean.GoodsDetailBean> {

    @Bind(R.id.tb_center_tv)
    TextView tbCenterTv;
    @Bind(R.id.tb_left_iv)
    ImageView tbLeftIv;
    @Bind(R.id.tb_right_iv)
    ImageView tbRightIv;
    @Bind(R.id.tb_right_tv)
    TextView tbRightTv;
    @Bind(R.id.common_refresh)
    ProgressBar commonRefresh;
    @Bind(R.id.iv_banner)
    ConvenientBanner ivBanner;
    @Bind(R.id.tv_sell_number)
    TextView tvSellNumber;
    @Bind(R.id.tv_goods_name)
    TextView tvGoodsName;
    @Bind(R.id.tv_description_content)
    TextView tvDescriptionContent;
    @Bind(R.id.tv_price_number)
    TextView tvPriceNumber;
    @Bind(R.id.etNum)
    EditText etNum;
    @Bind(R.id.ivAddToShoppingCart)
    ShoppingCartWithNumber ivAddToShoppingCart;

    Bundle bundle;

    String foodId;
    String merchantId;
    String merchantName;
    String image;
    String price;
    boolean isExitInDB;

    UserSignInPresenter userSignInPresenter;
    UserSignUpPresenter userSignUpPresenter;
    GetAddressPresenter getAddressPresenter;

    String nickName;
    String openid;
    String unionid;
    String headImageUrl;
    AddressUrlBean.AddressBean addressBean;

    private boolean isNeedLoc;
    private String location;
    private UmengUtil signUtil;


    public GoodsDetailActivity() {
        presenter = new GoodsDetailPresenter();
        location = AccountUtil.getInstance().getLocation();
        if (TextUtils.isEmpty(location)) {
            isNeedLoc = true;
        }
    }

    @Override
    public LocationService GetMapService() {
        if (isNeedLoc) {
            return InitApplication.getInstance().locationService;
        }
        return super.GetMapService();
    }

    @Override
    public void loadingData() {
        presenter.getGoodsInfo(AppConstants.TABLE_FOODMESSAGE, foodId);
    }

    @Override
    public MyLocationListener getLocationListener() {
        if (isNeedLoc)
            return new MyLocationListener(this);
        return super.getLocationListener();
    }


    @Override
    public void dataReceive(Intent intent) {
        if (intent != null) {
            foodId = intent.getStringExtra(AppConstants.KEY_PRODUCT_ID);
            merchantId = intent.getStringExtra(AppConstants.KEY_MERCHANT_ID);
            merchantName = intent.getStringExtra(AppConstants.KEY_MERCHANT_NAME);
        }
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (presenter == null)
            presenter = new GoodsDetailPresenter();
        presenter.attachView(this);
    }

    @Override
    public void onDetachedFromWindow() {
        presenter.detachView();
        super.onDetachedFromWindow();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.goods_detail_layout;
    }

    @Override
    public void keyBoardHide() {
        if (TextUtils.isEmpty(etNum.getText())) {
            etNum.setText("0");
        }
    }


    @Override
    protected void initData() {
        tbRightIv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_location_green));
        tbLeftIv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_chevron_left_black));
        if (isNeedLoc) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                checkPermissionAndSetIfNecessary(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE});
            else startLoc();
        } else
            tbRightTv.setText(location);
        tbCenterTv.setText(getResources().getString(R.string.goodsDetail));
        // 获取本地数据库的购物车数量
        AccountDao accountDao = AccountDao.getInstance();
        if (accountDao.isExist(ShoppingCartBean.KEY_MERCHANT_ID, merchantId) &&
                accountDao.isExist(ShoppingCartBean.KEY_FOOD_ID, foodId)) {
            isExitInDB = true;
            String _num = accountDao.getNumByFoodID(foodId);
            etNum.setText(_num);
            ivAddToShoppingCart.setNum(_num);
        } else {
            etNum.setText("0");
            ivAddToShoppingCart.setNum("0");
        }
        ivBanner.startTurning(2000);
        ivBanner.setPageIndicator(AppConstants.IMAGE_INDICTORS);
    }

    @Override
    public void showLocSuccess(String s) {
        String[] _str = s.split(",");
        tbRightTv.setText(_str[2]);
        AccountUtil.getInstance().setLocation(_str[2]);
    }

    @Override
    public void showLocFailed(String s) {

    }

    @Override
    public void onGrant() {
        startLoc();
    }

    @Override
    public void onDeny(final String permission) {
        Snackbar.make(decorView, "授权失败，将影响您的体验", Snackbar.LENGTH_LONG).setAction("重新授权", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(GoodsDetailActivity.this,
                        new String[]{permission}, null);
            }
        }).show();
    }

    @Override
    protected void showSuccess(Object o) {
        if (o instanceof GoodsDetailUrlBean.GoodsDetailBean) {
            GoodsDetailUrlBean.GoodsDetailBean _bean = (GoodsDetailUrlBean.GoodsDetailBean) o;
            bindData(_bean);
            if (commonRefresh.getVisibility() == View.VISIBLE)
                commonRefresh.setVisibility(View.GONE);
            return;
        }
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
            ToastHelper.getInstance().displayToastWithQuickClose("登录成功");
            SignInBackBean.UserInfoBean _data = (SignInBackBean.UserInfoBean) o;
            AccountUtil.getInstance().setUserID(_data.getID());
            AccountUtil.getInstance().setHeadImageUrl(_data.getPic());
            AccountUtil.getInstance().setPhoneNumber(_data.getPhonenumber());
            AccountUtil.getInstance().setUserName(_data.getCName());
            AccountUtil.getInstance().setNickName(nickName);
            AccountUtil.getInstance().setPwd(_data.getPwd());
            if (getAddressPresenter == null)
                getAddressPresenter = new GetAddressPresenter(this);
            getAddressPresenter.getAddress(AppConstants.TABLE_DEFAULT_ADDRESS);
            return;
        }
        if (o instanceof AddressUrlBean.AddressBean) {
            addressBean = (AddressUrlBean.AddressBean) o;
            createOrderAndToPay(addressBean);
        }
        if (addressBean == null)
            startActivityForResult(new Intent(this, AddOrNewAddressActivity.class), 0);

    }

    @Override
    protected void showError(Object s) {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) {
            if (data == null) {
                ToastHelper.getInstance().displayToastWithQuickClose("未能生成订单");
            } else {
                addressBean = data.getParcelableExtra("return");
                createOrderAndToPay(addressBean);
            }
        }
    }


    private void bindData(GoodsDetailUrlBean.GoodsDetailBean _bean) {
        image = _bean.getPicsrc().get(0);
        ivBanner.setPages(new CBViewHolderCreator<BannerImageHolder>() {

            @Override
            public BannerImageHolder createHolder() {
                return new BannerImageHolder();
            }
        }, _bean.getPicsrc());
        tvGoodsName.setText(_bean.getName());
        tvSellNumber.setText(String.format(Locale.ENGLISH, getResources().getString(R.string.sellNumber),
                _bean.getMonthlySales()));
        tvDescriptionContent.setText(_bean.getMessage());
        price = _bean.getPrice();
        tvPriceNumber.setText(String.format(Locale.ENGLISH, getResources().getString(R.string.countMoney),
                price));
    }

    @OnClick({R.id.tb_left_iv, R.id.tb_right_ll/*, R.id.ivAddToShoppingCart*/, R.id.ivShare,
            R.id.ivReduce, R.id.ivAdd, R.id.bt_buy})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tb_left_iv:
                bundle = null;
                setResult(AppConstants.BACK_WITHOUT_DATA);
                finish();
                break;
            case R.id.tb_right_ll:
                startLoc();
                break;
            case R.id.ivReduce:
                String Num = "0".equals(etNum.getText().toString()) ? "0" : DecimalUtil.subtract(etNum.getText().toString(), "1");
                etNum.setText(Num);
                ivAddToShoppingCart.setNum(Num);
                if (isExitInDB) {
                    if ("0".equals(Num)) {
                        ShoppingCartUtil.delGood(merchantId, foodId);
                        return;
                    }
                    ShoppingCartUtil.updateGoodsNumber(merchantId, foodId, Num);
                } else {
                    FoodInfoBean foodInfoBean = new FoodInfoBean(foodId, Num, merchantName, tvGoodsName.getText().toString(), image,
                            price, merchantId);
                    ShoppingCartUtil.addGoodToCart(foodInfoBean);
                    isExitInDB = true;
                }
                break;
            case R.id.ivAdd:
                String foodNum = DecimalUtil.add(etNum.getText().toString(), "1");
                etNum.setText(foodNum);
                ivAddToShoppingCart.setNum(foodNum);
                //TODO 需要更新购物车数据库的数据
                if (isExitInDB) {
                    ShoppingCartUtil.updateGoodsNumber(merchantId, foodId, foodNum);
                } else {
                    FoodInfoBean foodInfoBean = new FoodInfoBean(foodId, foodNum, merchantName, tvGoodsName.getText().toString(), image,
                            price, merchantId);
                    ShoppingCartUtil.addGoodToCart(foodInfoBean);
                    isExitInDB = true;
                }

                break;
            case R.id.ivAddToShoppingCart:
               /* LikeDao likeDao = LikeDao.getInstance();
                if (goodsDetailBean.isLike()) {
                    ivLike.setImageDrawable(drawable_not_like);
                    goodsDetailBean.setLike(false);
                    likeDao.updateLike(goodsDetailBean.getProductId(), 0);
                } else {
                    ivLike.setImageDrawable(drawable_like);
                    goodsDetailBean.setLike(true);
                    likeDao.updateLike(goodsDetailBean.getProductId(), 1);
                }*/
                break;
            case R.id.ivShare:
                UmengUtil umengUtil = new UmengUtil(this);
                UMImage imageMedia = new UMImage(this, image);
                ShareModel shareModel = new ShareModel("黄焖鸡米饭", "好吃", imageMedia);
                umengUtil.Share(shareModel, new ShareListener() {
                    @Override
                    public void shareSuccess(String message) {
                        ToastHelper.getInstance()._toast(message);
                    }

                    @Override
                    public void shareFailed(String message, String content) {
                        ToastHelper.getInstance()._toast(message);
                    }

                    @Override
                    public void shareCancel(String message) {
                        ToastHelper.getInstance()._toast(message);
                    }
                });
                break;
            /*case R.id.ivAddToShoppingCart:
                dialogShow();
                break;*/
            case R.id.bt_buy:
                if (Integer.valueOf(etNum.getText().toString()) == 0) {
                    ToastHelper.getInstance().displayToastWithQuickClose("未选择商品数量");
                    return;
                }
                if (!AccountUtil.getInstance().hasAccount()) {
                    signInDialogShow("当前用户未登录，请登录！");
                } else {
                    /*todo 调用获取默认地址接口*/
                    if (getAddressPresenter == null)
                        getAddressPresenter = new GetAddressPresenter(this);
                    getAddressPresenter.getAddress(AppConstants.TABLE_DEFAULT_ADDRESS);
                }
                break;
        }
    }

    private void signInDialogShow(String content) {
        final UIAlertView delDialog = new UIAlertView(this, "温馨提示", content,
                "加入购物车", "登录并购买");
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


    /*TODO 登录*/
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
                    userSignInPresenter = new UserSignInPresenter(GoodsDetailActivity.this);
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
        Intent intent = new Intent(GoodsDetailActivity.this,
                PayActivity.class);
        PayModel payModel = new PayModel();
        payModel.setReceiver(addressBean.getName());
        payModel.setPhone(addressBean.getPhone());
        payModel.setAddress(addressBean.getAddress().concat(addressBean.getDetail()));
        ArrayList<PayModel.PayBean> payBeen = new ArrayList<>();
        PayModel.PayBean _pay = new PayModel.PayBean();
        _pay.setMerchantName(merchantName);
        _pay.setProductName(tvGoodsName.getText().toString());
        _pay.setChecked(true);
        _pay.setProductId(foodId);
        _pay.setProductNumber(etNum.getText().toString());
        _pay.setProductPic(image);
        _pay.setProductPrice(price);
        payBeen.add(_pay);
        payModel.setPayBeanList(payBeen);
        intent.putExtra(AppConstants.KEY_PARCELABLE, payModel);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        bundle = null;
        setResult(AppConstants.BACK_WITHOUT_DATA);
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        location = null;
        super.onDestroy();
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void showContent(GoodsDetailUrlBean.GoodsDetailBean data) {
        GoodsDetailUrlBean.GoodsDetailBean _bean = data;
        bindData(_bean);
        if (commonRefresh.getVisibility() == View.VISIBLE)
            commonRefresh.setVisibility(View.GONE);
    }


    /* @Override
    public void showGoodsDetail(GoodsDetailUrlBean.GoodsDetailBean goodsDetail) {

    }

    @Override
    public void setPresenter(GoodsDetailContact.Presenter presenter) {

    }

    @Override
    public void showError(String error) {

    }*/
}
