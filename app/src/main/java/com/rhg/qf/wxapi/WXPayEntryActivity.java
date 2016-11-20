package com.rhg.qf.wxapi;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.rhg.qf.R;
import com.rhg.qf.pay.model.KeyLibs;
import com.rhg.qf.ui.activity.BaseAppcompactActivity;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import butterknife.Bind;

/**
 * desc:
 * author：remember
 * time：2016/6/25 9:33
 * email：1013773046@qq.com
 */
public class WXPayEntryActivity extends BaseAppcompactActivity implements IWXAPIEventHandler {
    @Bind(R.id.iv_pay_result)
    ImageView ivPayResult;
    @Bind(R.id.tv_pay_result)
    TextView tvPayResult;
    private IWXAPI api;

    @Override
    protected void initData() {
        api = WXAPIFactory.createWXAPI(this, KeyLibs.weixin_appId);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.pay_result_success_layout;
    }

    @Override
    protected void showSuccess(Object s) {

    }

    @Override
    protected void showError(Object s) {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }


    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == BaseResp.ErrCode.ERR_OK) {

                ivPayResult.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_right_blue));
                tvPayResult.setText("支付成功");
            } else {
                Log.i("RHG", "微信支付错误码:" + resp.errCode);
                ivPayResult.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_wrong_blue));
                tvPayResult.setText("支付失败");
            }

        }
    }
}
