package com.rhg.qf.ui.fragment;

import android.content.DialogInterface;
import android.view.View;

import com.rhg.qf.bean.CommonListModel;
import com.rhg.qf.bean.OrderUrlBean;
import com.rhg.qf.constants.AppConstants;
import com.rhg.qf.mvp.presenter.ModifyOrderPresenter;
import com.rhg.qf.ui.UIAlertView;
import com.rhg.qf.utils.ToastHelper;

/*
 *desc 待付款FM
 *author rhg
 *time 2016/6/22 21:17
 *email 1013773046@qq.com
 */
public class OrderUnPaidFM extends AbstractOrderFragment {
    ModifyOrderPresenter modifyOrderPresenter;
    String orderId;

    @Override
    protected int getFmTag() {
        return AppConstants.USER_ORDER_UNPAID;
    }

    @Override
    public void onItemLongClick(View view, int position, CommonListModel<OrderUrlBean.OrderBean> item) {
        orderId = item.getEntity().get(position).getID();
        mActivity.DialogShow(String.format("订单 %s 将被删除!", item.getEntity().get(position).getID()));
    }

    private void drawBack(String orderId) {
        if (modifyOrderPresenter == null)
            modifyOrderPresenter = new ModifyOrderPresenter(this);
        modifyOrderPresenter.modifyUserOrDeliverOrderState(orderId/*订单号*/,
               /*0:退单，1,：完成*/AppConstants.ORDER_WITHDRAW);
    }

    @Override
    public void showSuccess(Object o) {
        super.showSuccess(o);
        if (o instanceof String) {
            if (!((String) o).contains("success"))
                ToastHelper.getInstance().displayToastWithQuickClose("退单失败!");
            else refresh();
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
        if (which == DialogInterface.BUTTON_NEGATIVE) {
            return;
        }
        if (which == DialogInterface.BUTTON_POSITIVE) {
            drawBack(orderId);
        }
    }
}
