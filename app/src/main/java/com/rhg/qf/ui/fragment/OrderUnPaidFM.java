package com.rhg.qf.ui.fragment;

import com.rhg.qf.constants.AppConstants;

/*
 *desc 待付款FM
 *author rhg
 *time 2016/6/22 21:17
 *email 1013773046@qq.com
 */
public class OrderUnPaidFM extends AbstractOrderFragment {
    @Override
    protected int getFmTag() {
        return AppConstants.USER_ORDER_UNPAID;
    }
}
