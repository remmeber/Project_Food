package com.rhg.qf.update.callback;


import com.rhg.qf.R;
import com.rhg.qf.update.utils.UIHandleUtils;

/**
 * Caiyuan Huang
 * <p>2016/10/27</p>
 * <p>默认的App安装回调</p>
 */
public class DefaultAppInstallCallback implements AppInstallCallback {
    @Override
    public void onInstallSuccess() {
        UIHandleUtils.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                UIHandleUtils.showToast("安装成功");
            }
        });
    }

    @Override
    public void onInstallFail() {
        UIHandleUtils.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                UIHandleUtils.showToast(R.string.install_error);
            }
        });
    }
}
