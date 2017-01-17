package com.rhg.qf.application;

import android.app.Service;
import android.graphics.Bitmap;
import android.os.Vibrator;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.nostra13.universalimageloader.cache.disc.impl.LimitedAgeDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.MemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.LimitedAgeMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.rhg.qf.R;
import com.rhg.qf.locationservice.LocationService;
import com.rhg.qf.mvp.api.QFoodApiMamager;
import com.rhg.qf.mvp.api.QFoodApiService;
import com.rhg.qf.ui.activity.BaseAppcompactActivity;
import com.rhg.qf.unexpected.UnExpected;
import com.rhg.qf.update.LibUpgradeInitializer;
import com.rhg.qf.update.Updater;
import com.rhg.qf.update.UpdaterConfiguration;
import com.rhg.qf.update.callback.UpdateCheckCallback;
import com.rhg.qf.update.interfacedef.UpdateChecker;
import com.rhg.qf.update.model.UpdateInfo;
import com.rhg.qf.update.utils.MD5Utils;
import com.rhg.qf.utils.AccountUtil;
import com.rhg.qf.utils.CustomerHelper;
import com.rhg.qf.utils.DataUtil;
import com.rhg.qf.utils.ToastHelper;
import com.umeng.socialize.PlatformConfig;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;

import javax.net.ssl.SSLHandshakeException;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * desc:APP的入口，定义全局变量,继承MultiDexApplication，解决方法数超过65536
 * author：remember
 * time：2016/5/28 16:22
 * email：1013773046@qq.com
 */
public class InitApplication extends MultiDexApplication implements Runnable {
    public final static String QQID = "1105497604";
    public final static String QQKEY = "MdCq3ttlP0xlAPIg";
    public final static String WXID = "wxb066167618e700e6";/*已签名*/
    public final static String WXKEY = "1673bb821a7bd0e1aac602d1f5f85ed7";/*已签名*/

    public final static String fileName = "Log.txt";
    private static InitApplication initApplication;
    public LocationService locationService;
    public Vibrator mVibrator;
    private HashMap<String, WeakReference<BaseAppcompactActivity>> activityList = new HashMap<>();

    public static InitApplication getInstance() {
        if (initApplication == null)
            initApplication = new InitApplication();
        return initApplication;
    }

    public void removeActivity(BaseAppcompactActivity activity) {
        if (null != activity) {
            Log.i("RHG", "********* remove Activity " + activity.getClass().getName());
            activityList.remove(activity.getClass().getName());
        }
    }

    public int getAcitivityCount() {
        return activityList.size();
    }

    public void exit() {
        for (String key : activityList.keySet()) {
            WeakReference<BaseAppcompactActivity> activity = activityList.get(key);
            if (activity != null && activity.get() != null) {
                Log.i("RHG", "********* Exit " + activity.get().getClass().getSimpleName());
                activity.get().finish();
            }
        }
        System.exit(0);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initApplication = this;
        initBDMap();
        new Thread(this).run();
    }

    /**
     * desc:第三方配置
     * author：remember
     * time：2016/6/15 22:06
     * email：1013773046@qq.com
     */
    private void thirdConfig() {
        PlatformConfig.setWeixin(WXID, WXKEY);
        PlatformConfig.setQQZone(QQID, QQKEY);
    }

    /***
     * 初始化定位sdk，建议在Application中创建
     */
    private void initBDMap() {
        locationService = new LocationService(getApplicationContext());
        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        SDKInitializer.initialize(getApplicationContext());
    }

    private void initAccountUtil() {
        AccountUtil.getInstance().init(getApplicationContext());
    }

    private void initToast() {
        ToastHelper.getInstance().init(getApplicationContext());
    }

    //------网络图片例子,结合常用的图片缓存库UIL,你可以根据自己需求自己换其他网络图片库
    private void initImageLoader() {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().
                showImageForEmptyUri(R.drawable.ic_pic_failed)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .build();

        MemoryCache memoryCache = new LimitedAgeMemoryCache(new LruMemoryCache(4 * 1024 * 1024), 15 * 60);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCacheExtraOptions(320, 480)
                .memoryCache(memoryCache)
                .diskCacheFileCount(100)
                .diskCacheSize(50 * 1024 * 1024)
                .diskCache(new LimitedAgeDiskCache(getCacheDir(), 15 * 60))
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .writeDebugLogs()
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();

        ImageLoader.getInstance().init(config);
    }


    @Override
    public void run() {
        initAccountUtil();
        initImageLoader();
        initToast();
        thirdConfig();
//        EaseUI.getInstance().init(this);
        CustomerHelper.getInstance().init(this);
        File file = new File(getFilesDir().getPath() + "/" + fileName);
        if (file.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                ByteArrayOutputStream bo = new ByteArrayOutputStream();
                int len;
                byte[] b = new byte[1024];
                while ((len = fileInputStream.read(b)) != -1) {
                    bo.write(b, 0, len);
                }
                //TODO 将异常信息传到服务端
                Log.i("RHG", bo.toString());

                fileInputStream.close();
                bo.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            file.delete();
        }
        new UnExpected(getApplicationContext());
        initUpdateConfig();
    }

    private void initUpdateConfig() {
        LibUpgradeInitializer.init(this);
        final UpdaterConfiguration updaterConfiguration = new UpdaterConfiguration();
        updaterConfiguration.updateChecker(new UpdateChecker() {
            @Override
            public void check(final UpdateCheckCallback callback) {/*为DefaultUpdateCheckCallback对象*/
                //TODO 获取服务端的版本号以及更新信息
                QFoodApiMamager.getInstant().getQFoodApiService().getUpdateInfo("")
                        .onErrorReturn(new Func1<Throwable, UpdateInfo>() {
                            @Override
                            public UpdateInfo call(Throwable throwable) {
                                if (throwable instanceof RuntimeException) {
                                    ToastHelper.getInstance().displayToastWithQuickClose("网络出错啦！请检查网络");
                                } else if (throwable instanceof SSLHandshakeException) {
                                    ToastHelper.getInstance().displayToastWithQuickClose("网络认证失败！");
                                }
                                return null;
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Subscriber<UpdateInfo>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(UpdateInfo updateInfo) {
                                if (updateInfo != null) {
                                    updaterConfiguration.updateInfo(updateInfo);
                                    callback.onCheckSuccess();
                                } else {
                                    callback.onCheckFail("");
                                }
                                unsubscribe();
                            }
                        });
                /*UpdateInfo updateInfo = new UpdateInfo();
                updateInfo.setInstallType(UpdateInfo.InstallType.NOTIFY_INSTALL);
                updateInfo.setVersionCode(10204);
                updateInfo.setVersionName("v1.2.3");
                updateInfo.setUpdateType(UpdateInfo.UpdateType.INCREMENTAL_UPDATE);
                updateInfo.setUpdateTime(DataUtil.getCurrentTime());
                updateInfo.setUpdateSize(1024);
                updateInfo.setUpdateInfo("修复若干bug");
                updateInfo.setIsForceInstall(false);
                UpdateInfo.IncrementalUpdateInfo incrementalUpdateInfo = new UpdateInfo.IncrementalUpdateInfo();
                incrementalUpdateInfo.setFullApkMD5(MD5Utils.get32BitsMD5("e7eec01baac70f8a3688570439b9b467"));
                updateInfo.setIncrementalUpdateInfo(incrementalUpdateInfo);
                if (updateInfo != null) {
                    updaterConfiguration.updateInfo(updateInfo);
                    callback.onCheckSuccess();
                } else {
                    callback.onCheckFail("");
                }*/
            }
        });
        Updater.getInstance().init(updaterConfiguration);
    }
}
