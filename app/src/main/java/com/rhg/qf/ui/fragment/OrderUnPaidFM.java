package com.rhg.qf.ui.fragment;

import android.view.View;

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

    @Override
    protected int getFmTag() {
        return AppConstants.USER_ORDER_UNPAID;
    }

    @Override
    public void onItemLongClickListener(View view, int position, OrderUrlBean.OrderBean item) {
        showDelDialog(item.getID(), "订单将被删除!");
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

    private void showDelDialog(final String orderId, final String content) {
        final UIAlertView delDialog = new UIAlertView(getContext(), "温馨提示", content,
                "取消", "确定");
        delDialog.show();
        delDialog.setClicklistener(new UIAlertView.ClickListenerInterface() {
                                       @Override
                                       public void doLeft() {
                                           delDialog.dismiss();
                                       }

                                       @Override
                                       public void doRight() {
                                           delDialog.dismiss();
                                           drawBack(orderId);
                                       }
                                   }
        );
    }
}
