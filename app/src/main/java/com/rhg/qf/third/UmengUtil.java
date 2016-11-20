package com.rhg.qf.third;

import android.app.Activity;
import android.content.Intent;

import com.rhg.qf.bean.ShareModel;
import com.rhg.qf.impl.ShareListener;
import com.rhg.qf.impl.SignInListener;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/**
 * desc: Umeng 分享工具类
 * author：remember
 * time：2016/6/17 15:33
 * email：1013773046@qq.com
 */
public class UmengUtil {

    private UMShareAPI umShareAPI = null;
    private Activity activity;
    private SignInListener signInListener;
    private ShareListener shareListener;
    private UMAuthListener umGetInfoListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
//            Log.i("RHG", "user info" + map.toString());
            signInListener.signSuccess(map);
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            signInListener.signFail("获取用户信息失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {
            signInListener.signFail("取消");
        }
    };
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
//            Log.i("RHG", map.toString());
            umShareAPI.getPlatformInfo(activity, share_media, umGetInfoListener);
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            signInListener.signFail("取消授权");
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {
            signInListener.signFail("取消");
        }
    };
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA share_media) {
            shareListener.shareSuccess("分享成功");
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            shareListener.shareFailed("分享失败", throwable.getMessage());
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            shareListener.shareCancel("分享取消");
        }
    };
    private ShareAction shareAction;

    public UmengUtil(Activity activity) {
        this.activity = activity;
        if (umShareAPI == null)
            umShareAPI = UMShareAPI.get(activity);
    }

    public void SignIn(SHARE_MEDIA share_media, SignInListener signInListener) {
        this.signInListener = signInListener;
        umShareAPI.doOauthVerify(activity, share_media, umAuthListener);
    }

    public void Share(ShareModel shareModel, ShareListener shareListener) {
        this.shareListener = shareListener;
        if (shareAction == null)
            shareAction = new ShareAction(activity);
        shareAction.setDisplayList(SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN,
                SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE).withTitle(shareModel.getTitle())
                .withText(shareModel.getContent())
                .withMedia(shareModel.getImageMedia())
                .setCallback(umShareListener)
                .open();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        umShareAPI.onActivityResult(requestCode, resultCode, data);
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
