package com.rhg.qf.pay;


import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.rhg.qf.pay.model.KeyLibs;
import com.rhg.qf.pay.model.OrderInfo;
import com.rhg.qf.pay.model.PayType;
import com.rhg.qf.pay.model.ali.PayResult;
import com.rhg.qf.pay.pays.IPayable;
import com.rhg.qf.pay.pays.PaysFactory;
import com.rhg.qf.pay.pays.ali.AliPay;
import com.rhg.qf.pay.pays.wx.WxPay;

import java.lang.ref.WeakReference;

public abstract class BasePayActivity extends Activity {

    private static final int PAY_FLAG = 1;
    private static final int PAY_ALI = 2;
    private static final int PAY_WX = 3;
    /**
     * 默认方式微信支付（微信支付被选中）
     */
    public PayType payType = PayType.WeixinPay;
    WeakReference<BasePayActivity> activityWeakReference =
            new WeakReference<BasePayActivity>(BasePayActivity.this);
    // 支付宝支付完成后，多线程回调主线程handler
    private final MyHandler mHandler = new MyHandler(activityWeakReference);
    /**
     * 支付实体对象。通过该对象调用接口生成规范的订单信息并进行支付
     */
    private IPayable payManager;

    /**
     * 警告（比如：还没有确定支付结果，在等待支付结果确认）回调方法。开发者可根据各自业务override该方法
     */
    protected abstract void Warning(String s);

    protected abstract void showSuccess(String string);

    protected abstract void showError(String string);

    /**
     * 确认支付
     *
     * @param v
     */
    public void Pay(View v) {
        switch (payType) {
            case AliPay:
                PayAli();
                break;
            case WeixinPay:
                PayWeixin();
                break;

            default:
                break;
        }

    }

    private void PayAli() {
        // 构造支付对象，调用支付接口，获取支付结果
        if (payManager == null || payManager instanceof WxPay) {
            if (payManager != null)
                payManager.unRegisterApp();
            payManager = PaysFactory.GetInstance(payType);
        }

        // 1.开发者统一传入订单相关参数，生成规范化的订单（支付宝支付第一步；微信支付第二步）
        // ------调用重写方法
        final OrderInfo orderInfo = OnOrderCreate();

        // 2.调用支付方法
        Thread aliThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String result = payManager.Pay(BasePayActivity.this, orderInfo,
                        null);
                // 回调，通知主线程
                Message _msg = new Message();
                _msg.what = PAY_FLAG;
                _msg.obj = result;
                mHandler.sendMessage(_msg);
            }
        });
        aliThread.start();
    }

    private void PayWeixin() {
        if (payManager == null || payManager instanceof AliPay) {
            payManager = PaysFactory.GetInstance(payType);
            // 1.注册appId
            payManager.RegisterApp(BasePayActivity.this, KeyLibs.weixin_appId);
        }

        // 2.开发者统一传入订单相关参数，生成规范化的订单（支付宝支付第一步；微信支付第二步）
        // ------调用重写方法
        final OrderInfo orderInfo = OnOrderCreate();
        // 3.调用统一下单api生成预付单
        new Thread(new Runnable() {
            @Override
            public void run() {
                payManager.GenParam(orderInfo);
                // 4.调起支付
                payManager.Pay(null, null, null);
            }
        }).start();


    }

    /**
     * 生成订单参数
     *
     * @param body           商品详情。对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body
     * @param invalidTime    未付款交易的超时时间。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
     *                       参数不接受小数点，如1.5h，可转换为90m。
     * @param notifyUrl      服务器异步通知页面路径
     * @param tradeNo        商户唯一订单号（微信限制32字符）
     * @param subject        商品的标题/交易标题/订单标题/订单关键字等。该参数最长为128个汉字。
     * @param totalFee       该笔订单的资金总额，单位为RMB-Yuan。取值范围为[0.01，100000000.00]，精确到小数点后两位。
     * @param spbillCreateIp 终端ip。APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP
     * @return
     */
    public OrderInfo BuildOrderInfo(String body, String invalidTime,
                                    String notifyUrl, String tradeNo, String subject, String totalFee,
                                    String spbillCreateIp) {

//        payManager = PaysFactory.GetInstance(payType);

        return payManager.BuildOrderInfo(body, invalidTime, notifyUrl, tradeNo,
                subject, totalFee, spbillCreateIp);
    }

    /**
     * 初始化支付信息。支付前必须被调用
     *
     * @param weixinAppId      微信平台申请到的AppID
     * @param weixinMchId      微信平台申请到的商户号
     * @param weixinPrivateKey 微信支付秘钥
     */
    public void RegisterBasePay(String aliPartner, String aliSellerId,
                                String aliPrivateKey, String weixinAppId, String weixinMchId,
                                String weixinPrivateKey) {
        KeyLibs.ali_partner = aliPartner;
        KeyLibs.ali_sellerId = aliSellerId;
        KeyLibs.ali_privateKey = aliPrivateKey;
        KeyLibs.weixin_appId = weixinAppId;
        KeyLibs.weixin_mchId = weixinMchId;
        KeyLibs.weixin_privateKey = weixinPrivateKey;
    }

    /**
     * 实现支付的关键方法。必须override该方法，并返回规范的orderInfo（调用BuildOrderInfo来生成orderInfo）。
     *
     * @return --返回标准的订单信息
     */

    protected abstract OrderInfo OnOrderCreate();

    @Override
    protected void onDestroy() {
        if (payManager != null)
            payManager.unRegisterApp();
        mHandler.removeCallbacksAndMessages(null);
//        thread.close();
        super.onDestroy();
    }

    private static class MyHandler extends Handler {
        WeakReference<BasePayActivity> mActivityReference;

        MyHandler(WeakReference<BasePayActivity> activity) {
            mActivityReference = activity;
        }

        @Override
        public void handleMessage(Message msg) {
            final BasePayActivity activity = mActivityReference.get();
            if (activity == null)
                return;
            switch (msg.what) {
                case PAY_FLAG:
                    PayResult payResult = new PayResult((String) msg.obj);
                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.getResult();
                    Log.i("RHG", "result is:" + resultInfo + "\nmemo is :" + payResult.getMemo());
                    String resultStatus = payResult.getResultStatus();

                    if (TextUtils.equals(resultStatus, "9000")) {
                        // ----------调用重写方法
                        activity.showSuccess("支付成功");
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认。
                        if (TextUtils.equals(resultStatus, "8000")) {
                            // ---------调用重写方法
                            activity.Warning("支付结果确认中");
                        } else {
                            // -------调用重写方法
                            activity.showError("支付失败");
                        }
                    }
                    break;
                case PAY_ALI:
                    break;
                case PAY_WX:
                    break;
                default:
                    break;
            }

        }
    }
}
