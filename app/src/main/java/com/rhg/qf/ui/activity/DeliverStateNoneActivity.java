package com.rhg.qf.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;

import com.rhg.qf.R;
import com.rhg.qf.constants.AppConstants;
import com.rhg.qf.mvp.presenter.DeliverStatePresenter;
import com.rhg.qf.mvp.presenter.ModifyOrderPresenter;
import com.rhg.qf.ui.UIAlertView;
import com.rhg.qf.utils.ToastHelper;
import com.rhg.qf.widget.LineProgress;
import com.rhg.qf.widget.MyRatingBar;

import butterknife.Bind;
import butterknife.OnClick;

/*
 *desc
 *author rhg
 *time 2016/7/6 21:37
 *email 1013773046@qq.com
 */
public class DeliverStateNoneActivity extends BaseAppcompactActivity {

    @Bind(R.id.tb_left_iv)
    ImageView tbLeftIv;
    @Bind(R.id.food_receive_progress)
    LineProgress foodDeliverProgress;
    @Bind(R.id.rb_mouth_feel)
    MyRatingBar rbMouthFeel;
    @Bind(R.id.rb_deliver_service)
    MyRatingBar rbDeliverService;

    String orderId;
    DeliverStatePresenter getDeliverStatePresenter;
    ModifyOrderPresenter modifyOrderPresenter;

    @Override
    public void dataReceive(Intent intent) {
        orderId = intent.getStringExtra(AppConstants.KEY_ORDER_ID);
    }

    @Override
    public void loadingData() {
        if (getDeliverStatePresenter == null)
            getDeliverStatePresenter = new DeliverStatePresenter(this);
        getDeliverStatePresenter.getDeliverState(AppConstants.ORDER_STYLE, orderId);

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.food_deliver_layout;
    }


    @Override
    protected void initData() {
        tbLeftIv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_chevron_left_black));
        rbDeliverService.setIsIndicator(false);
        rbMouthFeel.setIsIndicator(false);
        modifyOrderPresenter = new ModifyOrderPresenter(this);
    }

    @Override
    public void showSuccess(Object s) {
        if (s instanceof String) {
//            ToastHelper.getInstance()._toast(s.toString());
            if (AppConstants.DELIVER_ORDER_UNACCEPT.equals(s)) {
                foodDeliverProgress.setState(LineProgress.STATE_NONE);
                return;
            }
            if (AppConstants.DELIVER_ORDER_ACCEPT.equals(s)) {
                foodDeliverProgress.setState(LineProgress.STATE_LEFT);
                return;
            }
            if (AppConstants.DELIVER_ORDER_DELIVERING.equals(s)) {
                foodDeliverProgress.setState(LineProgress.STATE_CENTER);
                return;
            }
            if (AppConstants.DELIVER_ORDER_COMPLETE.equals(s)) {
                foodDeliverProgress.setState(LineProgress.STATE_RIGHT);
                return;
            }
            if (((String) s).contains("order")) {
                finish();
            }

        }
    }

    @Override
    public void showError(Object s) {

    }


    @OnClick({R.id.tb_left_iv, R.id.bt_conform_receive, R.id.bt_finish})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tb_left_iv:
                finish();
                break;
            case R.id.bt_conform_receive:
                if (foodDeliverProgress.getState() == LineProgress.STATE_NONE) {
                    ToastHelper.getInstance().displayToastWithQuickClose("商品正在等待接单！");
                    break;
                }
                if (foodDeliverProgress.getState() == LineProgress.STATE_LEFT) {
                    ToastHelper.getInstance().displayToastWithQuickClose("商家已经接单，请耐心等待！");
                }
                if (foodDeliverProgress.getState() == LineProgress.STATE_CENTER) {
                    ToastHelper.getInstance().displayToastWithQuickClose("跑腿员努力为您送货中！");
                }
                DialogShow("温馨提示", "请在收到货后再确认收货!", "未收到货", "已收货");
                break;
            case R.id.bt_finish:
//                final String mouthFeelRate = String.format(Locale.ENGLISH, "%.2f", getRate(rbMouthFeel));
//                final String deliverServiceRate = String.format(Locale.ENGLISH, "%.2f", getRate(rbDeliverService));
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ToastHelper.getInstance()._toast("感谢您的评论"/*"口感评分：" + mouthFeelRate +
                                ",送货服务：" + deliverServiceRate*/);
                    }
                }, 1000);
                break;
        }
    }


    private float getRate(MyRatingBar rateBar) {
        return rateBar.getStarRating();
    }


    @Override
    public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
        if (which == DialogInterface.BUTTON_POSITIVE) {
            modifyOrderPresenter.modifyUserOrDeliverOrderState(orderId,
                    AppConstants.ORDER_FINISH);/*1表示已完成*/
        }
    }
}
