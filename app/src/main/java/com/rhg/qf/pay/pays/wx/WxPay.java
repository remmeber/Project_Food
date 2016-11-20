package com.rhg.qf.pay.pays.wx;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.rhg.qf.mvp.api.QFoodApi;
import com.rhg.qf.pay.model.KeyLibs;
import com.rhg.qf.pay.model.OrderInfo;
import com.rhg.qf.pay.pays.IPayable;
import com.rhg.qf.pay.security.wx.MD5;
import com.rhg.qf.utils.DecimalUtil;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * desc:
 * author：remember
 * time：2016/6/24 15:20
 * email：1013773046@qq.com
 */
public class WxPay implements IPayable {
    OkHttpClient client = null;
    //微信sdk对象
    private IWXAPI msgApi;
    //生成预付单需要的参数
    private Map<String, String> paramsForPrepay = null;

    public WxPay() {
    }

    @Override
    public String Pay(Activity activity, OrderInfo orderInfo, String prepayId) {
        boolean isSuccess = msgApi.sendReq(BuildCallAppParams());
        return String.valueOf(isSuccess);
    }

    @Override
    public OrderInfo BuildOrderInfo(String body, String invalidTime,
                                    String notifyUrl, String tradeNo,
                                    String subject, String totalFee, String spbillCreateIp) {
        try {
//            String nonceStr = GetNonceStr();
            /*List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
            packageParams.add(new BasicNameValuePair("appid", KeyLibs.weixin_appId));
            packageParams.add(new BasicNameValuePair("body", subject));//和支付宝的subject类似
            packageParams.add(new BasicNameValuePair("mch_id", KeyLibs.weixin_mchId));
            packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
            packageParams.add(new BasicNameValuePair("notify_url", notifyUrl));
            packageParams.add(new BasicNameValuePair("out_trade_no", genOutTradNo(tradeNo)));
            packageParams.add(new BasicNameValuePair("spbill_create_ip", spbillCreateIp));
            packageParams.add(new BasicNameValuePair("total_fee", Integer.valueOf()));
            packageParams.add(new BasicNameValuePair("trade_type", "APP"));*/
            Map<String, String> packageParams = new LinkedHashMap<>();//不能用HashMap();因为HashMap无序
//            packageParams.put("appid", KeyLibs.weixin_appId);/*微信开放平台审核通过的应用APPID*/
//            packageParams.put("body", subject);/*商品或支付单简要描述，可以多个商品一起打包*/
//            packageParams.put("mch_id", KeyLibs.weixin_mchId);/*微信支付分配的商户号*/
//            packageParams.put("nonce_str", nonceStr);/*随机字符串*/
//            packageParams.put("notify_url", notifyUrl);/*接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。*/
            packageParams.put("out_trade_no", tradeNo);/*商户系统内部的订单号,32个字符内、可包含字母, */
            packageParams.put("spbill_create_ip", spbillCreateIp);/*用户端实际ip*/
            packageParams.put("total_fee", DecimalUtil.multiplyWithScale(totalFee, "100", 0));/*总金额*/
//            packageParams.put("trade_type", "APP");/*支付类型*/
            paramsForPrepay = packageParams;//将参数保存一份，待调用支付时使用
//            String sign = Sign(packageParams);
//            packageParams.put("sign", sign);/*签名*/
//            String xmlString = XmlUtil.MapToXml(packageParams);
//            String json = new Gson().toJson(packageParams);
//            Log.i("RHG", "Build Order Info:" + xmlString);

            return null;

        } catch (Exception e) {
            Log.i("RHG", e.getMessage());
            return null;
        }
    }

    public void RegisterApp(Context context, String appId) {
        msgApi = WXAPIFactory.createWXAPI(context, null);
        msgApi.registerApp(appId);
    }

    public void unRegisterApp() {
        if (msgApi != null) {
            msgApi.unregisterApp();
        }
    }

    public void GenParam(final OrderInfo orderInfo) {
        String url = QFoodApi.BASE_URL + "Table/JsonSQL/weixinpay/prepay.php";
//        String json = new Gson().toJson(orderInfo);
//        Log.i("RHG", json);
        try {
            byte[] response = post(url, null);
            String content = new String(response);
        /*content = content.replace("<![CDATA[", "");
        content = content.replace("]]>", "");*/
//            Log.i("RHG", "response: " + content);
//        return null;
//        Map<String, String> map = XmlUtil.DecodeXmlToMap(content);
            JSONObject jsonObject = new JSONObject(content);
            if ("SUCCESS".equals(jsonObject.get("return_code"))) {
                paramsForPrepay.put("appid", (String) jsonObject.get("appid"));
                paramsForPrepay.put("mch_id", (String) jsonObject.get("mch_id"));
                paramsForPrepay.put("nonce_str", (String) jsonObject.get("nonce_str"));
                paramsForPrepay.put("trade_type", (String) jsonObject.get("trade_type"));
                paramsForPrepay.put("sign", (String) jsonObject.get("sign"));
                paramsForPrepay.put("prepay_id", (String) jsonObject.get("prepay_id"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private PayReq BuildCallAppParams() {

        PayReq req = new PayReq();
        req.appId = paramsForPrepay.get("appid");
        req.partnerId = paramsForPrepay.get("mch_id");
        req.prepayId = paramsForPrepay.get("prepay_id");
        req.packageValue = "Sign=WXPay";
        req.nonceStr = paramsForPrepay.get("nonce_str");
        req.timeStamp = String.valueOf(GetTimeStamp());
        Map<String, String> signParams = new LinkedHashMap<>();
        signParams.put("appid", req.appId);
        signParams.put("noncestr", req.nonceStr);
        signParams.put("package", req.packageValue);
        signParams.put("partnerid", req.partnerId);
        signParams.put("prepayid", req.prepayId);
        signParams.put("timestamp", req.timeStamp);
        req.sign = Sign(signParams);
        Log.i("RHG", req.sign);
        return req;
    }

    byte[] post(String url, String json) {
//        RequestBody formBody = RequestBody.create(MediaType.parse("text/xml;charset=UTF-8"), json);
//        Map<String,String> parm = XmlUtil.DecodeXmlToMap(json);
        RequestBody body = new FormBody.Builder().add("out_trade_no", paramsForPrepay.get("out_trade_no"))
                .add("spbill_create_ip", paramsForPrepay.get("spbill_create_ip"))
                .add("total_fee", paramsForPrepay.get("total_fee")).build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        if (client == null)
            client = new OkHttpClient.Builder().readTimeout(5000, TimeUnit.MILLISECONDS)
                    .connectTimeout(5000, TimeUnit.MILLISECONDS)
                    .writeTimeout(5000, TimeUnit.MILLISECONDS).build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            return response.body().bytes();
        } catch (IOException e) {
            return null;/*处理超时等异常*/
        }
    }

    private String GetNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    private String genOutTradNo(String orderNos) {
        // Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(orderNos).getBytes());
    }


    private String Sign(Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append(entry.getKey());
            sb.append('=');
            sb.append(entry.getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(KeyLibs.weixin_privateKey);
        String sign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        return sign;
    }

    private long GetTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

}
