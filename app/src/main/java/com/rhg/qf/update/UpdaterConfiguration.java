package com.rhg.qf.update;


import com.rhg.qf.update.callback.AppInstallCallback;
import com.rhg.qf.update.callback.DefaultAppInstallCallback;
import com.rhg.qf.update.callback.DefaultDownloadCallback;
import com.rhg.qf.update.callback.DefaultUpdateCheckCallback;
import com.rhg.qf.update.callback.DownloadCallback;
import com.rhg.qf.update.callback.UpdateCheckCallback;
import com.rhg.qf.update.interfacedef.AppInstaller;
import com.rhg.qf.update.interfacedef.DownloadUIHandler;
import com.rhg.qf.update.interfacedef.Downloader;
import com.rhg.qf.update.interfacedef.UpdateCheckUIHandler;
import com.rhg.qf.update.interfacedef.UpdateChecker;
import com.rhg.qf.update.interfaceimpl.AppNotifyInstaller;
import com.rhg.qf.update.interfaceimpl.AppSilentInstaller;
import com.rhg.qf.update.interfaceimpl.DefaultDownloadUIHandler;
import com.rhg.qf.update.interfaceimpl.DefaultDownloader;
import com.rhg.qf.update.interfaceimpl.DefaultUpdateCheckUIHandler;
import com.rhg.qf.update.model.UpdateInfo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Caiyuan Huang
 * <p>2016/10/27</p>
 * <p>更新配置</p>
 */
public final class UpdaterConfiguration {

    private UpdateChecker mUpdateChecker;
    private UpdateCheckCallback mUpdateCheckCallback;
    private UpdateCheckUIHandler mUpdateUIHandler;
    private Downloader mDownloader;
    private DownloadCallback mDownloadCallback;
    private DownloadUIHandler mDownloadUIHandler;
    private ExecutorService mExecutorService;
    private AppInstaller mSilentInstaller;
    private AppInstaller mNotifyInstaller;
    private AppInstallCallback mInstallCallback;
    private UpdateInfo mUpdateInfo;


    public UpdaterConfiguration updateInfo(UpdateInfo info) {
        this.mUpdateInfo = info;
        return this;
    }

    public UpdaterConfiguration updateChecker(UpdateChecker updateChecker) {
        this.mUpdateChecker = updateChecker;
        return this;
    }

    public UpdaterConfiguration updateCheckCallback(UpdateCheckCallback callback) {
        this.mUpdateCheckCallback = callback;
        return this;
    }

    public UpdaterConfiguration updateUIHandler(UpdateCheckUIHandler updateUIHandler) {
        this.mUpdateUIHandler = updateUIHandler;
        return this;
    }

    public UpdaterConfiguration downloader(Downloader downloader) {
        this.mDownloader = downloader;
        return this;
    }

    public UpdaterConfiguration downloadCallback(DownloadCallback callback) {
        this.mDownloadCallback = callback;
        return this;
    }

    public UpdaterConfiguration downloadUIHandler(DownloadUIHandler downloadUIHandler) {
        this.mDownloadUIHandler = downloadUIHandler;
        return this;
    }

    public UpdaterConfiguration executorService(ExecutorService executorService) {
        this.mExecutorService = executorService;
        return this;
    }

    public UpdaterConfiguration silentInstaller(AppSilentInstaller installer) {
        this.mSilentInstaller = installer;
        return this;
    }

    public UpdaterConfiguration notifyInstaller(AppNotifyInstaller installer) {
        this.mNotifyInstaller = installer;
        return this;
    }

    public UpdaterConfiguration intallCallback(AppInstallCallback callback) {
        this.mInstallCallback = callback;
        return this;
    }

    public UpdateChecker getUpdateChecker() {
        if (mUpdateChecker == null) {
            throw new RuntimeException("updateChecker must be not null");
        }
        return mUpdateChecker;
    }

    public UpdateCheckUIHandler getUpdateUIHandler() {
        if (mUpdateUIHandler == null) {
            mUpdateUIHandler = new DefaultUpdateCheckUIHandler(this);
        }
        return mUpdateUIHandler;
    }

    public Downloader getDownloader() {
        if (mDownloader == null) {
            mDownloader = new DefaultDownloader(this);
        }
        return mDownloader;
    }

    public DownloadUIHandler getDownloadUIHandler() {
        if (mDownloadUIHandler == null) {
            mDownloadUIHandler = new DefaultDownloadUIHandler(this);
        }
        return mDownloadUIHandler;
    }

    public UpdateCheckCallback getUpdateCheckCallback() {
        if (mUpdateCheckCallback == null) {
            mUpdateCheckCallback = new DefaultUpdateCheckCallback(this);
        }
        return mUpdateCheckCallback;
    }

    public DownloadCallback getDownloadCallback() {
        if (mDownloadCallback == null) {
            mDownloadCallback = new DefaultDownloadCallback(this);
        }
        return mDownloadCallback;
    }

    public ExecutorService getExecutorService() {
        if (mExecutorService == null) {
            mExecutorService = Executors.newCachedThreadPool();
        }
        return mExecutorService;
    }

    public AppInstaller getNotifyInstaller() {
        if (mNotifyInstaller == null) {
            mNotifyInstaller = new AppNotifyInstaller(this);
        }
        return mNotifyInstaller;
    }

    public AppInstaller getSilentInstaller() {
        if (mSilentInstaller == null) {
            mSilentInstaller = new AppSilentInstaller(this);
        }
        return mSilentInstaller;
    }

    public AppInstallCallback getInstallCallback() {
        if (mInstallCallback == null) {
            mInstallCallback = new DefaultAppInstallCallback();
        }
        return mInstallCallback;
    }

    public UpdateInfo getUpdateInfo() {
        if (mUpdateInfo == null) {
            throw new RuntimeException("update info is null,please invoke UpdaterConfiguration.updateInfo method");
        }
        return mUpdateInfo;
    }
}
