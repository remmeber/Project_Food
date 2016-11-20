package com.rhg.qf.ui.activity;

import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.rhg.qf.R;
import com.rhg.qf.mvp.presenter.DIYOrderPresenter;
import com.rhg.qf.utils.ToastHelper;

import butterknife.Bind;
import butterknife.OnClick;

/**
<<<<<<< HEAD
 * desc:自主点餐
=======
 * desc:自主点餐 TODO 目前未使用
>>>>>>> 2726e02649fdc377b34571a59019133563595479
 * author：remember
 * time：2016/6/22 14:41
 * email：1013773046@qq.com
 */
public class OrderChatActivity extends BaseAppcompactActivity {

    @Bind(R.id.tb_left_iv)
    ImageView tbLeftIv;
    @Bind(R.id.fl_tab)
    FrameLayout flTab;
    @Bind(R.id.et_user_input)
    EditText etUserInput;
    @Bind(R.id.tb_center_tv)
    TextView tbCenterTv;

    DIYOrderPresenter commitDIYOrderPresenter;

    @Override
    protected int getLayoutResId() {
        return R.layout.personal_order;
    }


    @Override
    protected void initData() {
        flTab.setBackgroundColor(ContextCompat.getColor(this, R.color.colorBlueNormal));
        tbLeftIv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_chevron_left_black));
        tbCenterTv.setText(R.string.personalOrder);
    }

    @Override
    protected void showSuccess(Object s) {
        if (s instanceof String) {
            ToastHelper.getInstance()._toast(s.toString());
            etUserInput.setText("");
        }
    }

    @Override
    protected void showError(Object s) {

    }


    @OnClick({R.id.tb_left_iv, R.id.bt_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tb_left_iv:
                finish();
                break;
            case R.id.bt_submit:
                if (TextUtils.isEmpty(etUserInput.getText().toString().trim())) {
                    ToastHelper.getInstance()._toast("请输入您想要的菜品");
                    break;
                }
                if (commitDIYOrderPresenter == null)
                    commitDIYOrderPresenter = new DIYOrderPresenter(this);
                commitDIYOrderPresenter.commitDIYOrder(etUserInput.getText().toString());
        }
    }

}
