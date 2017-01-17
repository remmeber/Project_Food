package com.rhg.qf.update.interfaceimpl;


import com.rhg.qf.update.LibUpgradeInitializer;
import com.rhg.qf.update.UpdaterConfiguration;
import com.rhg.qf.update.interfacedef.AppInstaller;
import com.rhg.qf.update.utils.InstallUtils;
import com.rhg.qf.update.utils.UIHandleUtils;
import com.rhg.qf.utils.ToastHelper;

/**
 * Caiyuan Huang
 * <p>2016/10/27</p>
 * <p>App提示安装实现</p>
 */
public class AppNotifyInstaller implements AppInstaller {
    private UpdaterConfiguration mConfig;

    public AppNotifyInstaller(UpdaterConfiguration config) {
        this.mConfig = config;
    }

    @Override
    public void install(final String filePath) {
        UIHandleUtils.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                InstallUtils.notifyInstallApk(LibUpgradeInitializer.getContext(), filePath);
            }
        });
    }
}
