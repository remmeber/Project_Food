package com.rhg.qf.ui.fragment;

import com.rhg.qf.constants.AppConstants;


/**
 * desc:所有店铺的按评价fm
 * author：remember
 * time：2016/5/28 16:43
 * email：1013773046@qq.com
 */
public class ByRateFm extends AbstractMerchantsFragment {
    @Override
    protected int getMerchantsFmType() {
        return AppConstants.MERCHANT_RATE;
    }
}
