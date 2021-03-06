package com.rhg.qf.update.callback;


import com.rhg.qf.update.LibUpgradeInitializer;
import com.rhg.qf.update.UpdaterConfiguration;
import com.rhg.qf.update.utils.AppInfoUtils;
import com.rhg.qf.update.utils.UIHandleUtils;

/**
 * Caiyuan Huang
 * <p>2016/10/27</p>
 * <p>默认的更新检测回调实现</p>
 */
public final class DefaultUpdateCheckCallback implements UpdateCheckCallback {
    private UpdaterConfiguration mConfig;

    public DefaultUpdateCheckCallback(UpdaterConfiguration config) {
        this.mConfig = config;
    }

    @Override
    public void onCheckSuccess() {
        UIHandleUtils.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                int curVersionCode = AppInfoUtils.getApkVersionCode(LibUpgradeInitializer.getContext());
                boolean hasUpdate = curVersionCode <Integer.valueOf( mConfig.getUpdateInfo().getUpdateVersionCode());
                if (mConfig.getUpdateUIHandler() != null) {
                    if (hasUpdate) {
                        mConfig.getUpdateUIHandler().hasUpdate();
                    } else {
                        mConfig.getUpdateUIHandler().noUpdate();
                    }
                }
            }
        });

    }

    @Override
    public void onCheckFail(String error) {
        if (mConfig.getUpdateUIHandler() != null) {
            mConfig.getUpdateUIHandler().checkError(error);
        }
    }
}
