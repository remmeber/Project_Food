package com.rhg.qf.ui.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.rhg.qf.R;
import com.rhg.qf.utils.RegexUtils;
import com.rhg.qf.utils.ToastHelper;
import com.rhg.qf.widget.MyCountDownTimer;

import butterknife.Bind;
import butterknife.OnClick;

/*
 *desc 跑腿员注册
 *author rhg
 *time 2016/7/6 22:04
 *email 1013773046@qq.com
 */
public class DeliverRegisterActivity extends BaseFragmentActivity {

    @Bind(R.id.tb_center_tv)
    TextView tbCenterTv;
    @Bind(R.id.tb_left_iv)
    ImageView tbLeftIv;
    @Bind(R.id.fl_tab)
    FrameLayout flTab;
    @Bind(R.id.et_phone_number)
    EditText etPhoneNumber;
    @Bind(R.id.et_check_number)
    EditText etCheckNumber;
    @Bind(R.id.bt_check_number)
    TextView btCheckNumber;
    @Bind(R.id.tv_agreement)
    TextView tvAgreement;
    @Bind(R.id.tv_after_read_agreement)
    TextView tvAfterReadAgreement;

    MyCountDownTimer countTimer;
    boolean isChecked = false;

    @Override
    protected int getLayoutResId() {
        return R.layout.deliver_register_layout;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        countTimer = new MyCountDownTimer(60000, 1000, btCheckNumber);
        flTab.setBackgroundColor(ContextCompat.getColor(this, R.color.colorBlueNormal));
        tbLeftIv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_chevron_left_black));
        tbCenterTv.setText(getResources().getString(R.string.register));
        tvAgreement.setMovementMethod(ScrollingMovementMethod.getInstance());
        /*tvAfterReadAgreement.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (tvAfterReadAgreement.getCompoundDrawables()[0] == null)
                    return false;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    return true;
                }
                if (event.getX() > 0 &&
                        event.getX() < tvAfterReadAgreement.getCompoundDrawables()[0].getBounds().width()) {
                    if (!isChecked) {
                        isChecked = true;
                        tvAfterReadAgreement.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_check_green),
                                null, null, null);
                    } else {
                        isChecked = false;
                        tvAfterReadAgreement.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_uncheck_green),
                                null, null, null);
                    }
                    return true;
                }
                return false;
            }
        });*/
    }

    @Override
    protected void showSuccess(Object s) {

    }

    @Override
    protected void showError(Object s) {

    }

    @OnClick({R.id.tb_left_iv, R.id.bt_check_number, R.id.complete_register, R.id.tv_after_read_agreement})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tb_left_iv:
                finish();
                break;
            case R.id.bt_check_number:
                if (TextUtils.isEmpty(etPhoneNumber.getText().toString().trim())) {
                    ToastHelper.getInstance()._toast("手机号码为空");
                    break;
                }
                if (!RegexUtils.isPhoneNumber(etPhoneNumber.getText().toString().trim())) {
                    ToastHelper.getInstance()._toast("手机号码不正确");
                    break;
                }
                countTimer.start();
                btCheckNumber.setClickable(false);
                break;
            case R.id.tv_after_read_agreement:
                if (!isChecked) {
                    isChecked = true;
                    tvAfterReadAgreement.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(this, R.drawable.ic_check_green),
                            null, null, null);
                } else {
                    isChecked = false;
                    tvAfterReadAgreement.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(this, R.drawable.ic_uncheck_green),
                            null, null, null);
                }
                break;
            case R.id.complete_register:
                if (TextUtils.isEmpty(etCheckNumber.getText().toString().trim())) {
                    ToastHelper.getInstance()._toast("验证码为空");
                    break;
                }
                if (!isChecked) {
                    ToastHelper.getInstance()._toast("请阅读协议");
                    break;
                }
                if (countTimer.isRunning()) {
                    countTimer.cancel();
                    countTimer = null;
                }
                register2Server(etCheckNumber.getText().toString());
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if (countTimer.isRunning())
            countTimer.onFinish();
        countTimer.removeView();
        countTimer = null;
        super.onDestroy();
    }

    /*
         *desc 将手机号注册到服务器端
         *author rhg
         *time 2016/7/6 22:02
         *email 1013773046@qq.com
         */
    private void register2Server(String s) {

    }
}
