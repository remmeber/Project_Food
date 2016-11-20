package com.rhg.qf.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.rhg.qf.R;
import com.rhg.qf.bean.MerchantInfoDetailUrlBean;
import com.rhg.qf.constants.AppConstants;
import com.rhg.qf.mvp.presenter.MerchantInfoDetailPresenter;
import com.rhg.qf.ui.activity.ShopDetailActivity;

import java.util.Locale;

import butterknife.Bind;

/**
 * desc:店铺详情fm
 * author：remember
 * time：2016/5/28 16:49
 * email：1013773046@qq.com
 */
public class ShopDetailFragment extends BaseFragment {
    @Bind(R.id.tv_shop_phone_num)
    TextView tvPhoneNumber;
    @Bind(R.id.tv_shop_address)
    TextView tvShopAddress;
    @Bind(R.id.tv_seller_note)
    TextView tvSellerNote;
    String merchantId;

    MerchantInfoDetailPresenter getMerchantInfoPresenter;

    @Override
    public void receiveData(Bundle arguments) {
        merchantId = arguments.getString(AppConstants.KEY_MERCHANT_ID);
    }


    @Override
    public void loadData() {
        /*getMerchantInfoPresenter = new MerchantInfoDetailPresenter(this);
        getMerchantInfoPresenter.getMerchantInfo(AppConstants.MERCHANT_INFO, merchantId);*/
    }

    @Override
    public int getLayoutResId() {
        return R.layout.shop_detail_fm2_content;
    }

    @Override
    protected void initData() {
        getMerchantInfoPresenter = new MerchantInfoDetailPresenter(this);
        getMerchantInfoPresenter.getMerchantInfo(AppConstants.MERCHANT_INFO, merchantId);
    }

    @Override
    protected void initView(View view) {
    }

    @Override
    protected void showFailed() {
    }

    @Override
    public void showSuccess(Object o) {
        if (o instanceof MerchantInfoDetailUrlBean.MerchantInfoDetailBean) {
            MerchantInfoDetailUrlBean.MerchantInfoDetailBean _data = (MerchantInfoDetailUrlBean.MerchantInfoDetailBean) o;
            tvPhoneNumber.setText(String.format(Locale.ENGLISH, getContext().getResources().getString(R.string.tvContactPhone),
                    _data.getPhonenumber()));
            tvShopAddress.setText(String.format(Locale.ENGLISH, getContext().getResources().getString(R.string.tvMerchantAddress),
                    _data.getAddress()));
            tvSellerNote.setText(String.format(Locale.ENGLISH, getContext().getResources().getString(R.string.tvMerchantNote),
                    _data.getMessage()));
            if (mActivity != null) {
                ((ShopDetailActivity) mActivity).setMerchantName(_data.getName());
            }
        }

    }

}
