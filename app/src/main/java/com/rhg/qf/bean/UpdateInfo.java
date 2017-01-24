package com.rhg.qf.bean;


/**
 * Caiyuan Huang
 * <p>2016/10/27</p>
 * <p>更新信息</p>
 */
public class UpdateInfo {
    /**
     * 更新方式(全量更新,增量更新)
     */
    public static final String TOTAL_UPDATE = "0";
    public static final String INCREMENTAL_UPDATE = "1";

    private boolean isForceInstall;//是否是强制更新
    private String updateVersionCode;//版本号
    private String updateVersionName;//版本名
    private String updateInfo;//更新信息
    private String updateSize;//更新大小
    private String updateTime;//更新时间
    private String updateType;//更新类型
    private String apkUrl;//apk下载地址

    private String fullApkMD5;//完整apk的MD5值
    private String patchUrl;//增量包的下载地址
    private InstallType installType = InstallType.NOTIFY_INSTALL;//安装方式

    /**
     * 安装方式(静默安,提示安装)
     */
    public enum InstallType {
        SILENT_INSTALL, NOTIFY_INSTALL
    }

    public String getUpdateVersionCode() {
        return updateVersionCode;
    }

    public void setUpdateVersionCode(String updateVersionCode) {
        this.updateVersionCode = updateVersionCode;
    }

    public String getUpdateVersionName() {
        return updateVersionName;
    }

    public void setUpdateVersionName(String updateVersionName) {
        this.updateVersionName = updateVersionName;
    }

    public String getUpdateInfo() {
        return updateInfo;
    }

    public void setUpdateInfo(String updateInfo) {
        this.updateInfo = updateInfo;
    }

    public String getUpdateType() {
        return updateType;
    }

    public void setUpdateType(String updateType) {
        this.updateType = updateType;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    public String getFullApkMD5() {
        return fullApkMD5;
    }

    public void setFullApkMD5(String fullApkMD5) {
        this.fullApkMD5 = fullApkMD5;
    }

    public String getPatchUrl() {
        return patchUrl;
    }

    public void setPatchUrl(String patchUrl) {
        this.patchUrl = patchUrl;
    }

    public InstallType getInstallType() {
        return installType;
    }

    public void setInstallType(InstallType installType) {
        this.installType = installType;
    }

    public boolean isForceInstall() {
        return isForceInstall;
    }

    public void setIsForceInstall(boolean isForceInstall) {
        this.isForceInstall = isForceInstall;
    }

    public String getUpdateSize() {
        return updateSize;
    }

    public void setUpdateSize(String updateSize) {
        this.updateSize = updateSize;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "UpdateInfo{" +
                "isForceInstall=" + isForceInstall +
                ", updateVersionCode=" + updateVersionCode +
                ", updateVersionName='" + updateVersionName + '\'' +
                ", updateInfo='" + updateInfo + '\'' +
                ", updateSize=" + updateSize +
                ", updateTime='" + updateTime + '\'' +
                ", updateType=" + updateType +
                ", apkUrl='" + apkUrl + '\'' +
                ", fullApkMD5='" + fullApkMD5 + '\'' +
                ", patchUrl='" + patchUrl + '\'' +
                ", installType=" + installType +
                '}';
    }
}
