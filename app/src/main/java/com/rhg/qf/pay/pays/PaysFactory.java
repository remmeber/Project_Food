package com.rhg.qf.pay.pays;


import com.rhg.qf.pay.model.PayType;
import com.rhg.qf.pay.pays.ali.AliPay;
import com.rhg.qf.pay.pays.wx.WxPay;

public class PaysFactory {

    public static IPayable GetInstance(PayType payType) {
        IPayable pay = null;
        switch (payType) {
            case AliPay:
                pay = new AliPay();
                break;
            case WeixinPay:
                pay = new WxPay();
            default:
                break;
        }
        return pay;
    }
}
