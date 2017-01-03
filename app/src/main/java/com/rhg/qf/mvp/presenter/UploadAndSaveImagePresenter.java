package com.rhg.qf.mvp.presenter;

import android.util.Log;

import com.rhg.qf.mvp.model.UploadAndSaveImageModel;
import com.rhg.qf.mvp.view.BaseView;
import com.rhg.qf.utils.ToastHelper;

import java.io.File;

import javax.net.ssl.SSLHandshakeException;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 作者：rememberon 2016/5/31
 * 邮箱：1013773046@qq.com
 */
public class UploadAndSaveImagePresenter {

    BaseView baseView;
    UploadAndSaveImageModel uploadAndSaveImageModel;

    public UploadAndSaveImagePresenter(BaseView baseView) {
        this.baseView = baseView;
        uploadAndSaveImageModel = new UploadAndSaveImageModel();
    }

    public void UploadAndSaveImage(File file) {
        uploadAndSaveImageModel.UploadAndSaveImage(file).observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(new Func1<Throwable, String>() {
                    @Override
                    public String call(Throwable throwable) {
                        if (throwable instanceof RuntimeException) {
                            ToastHelper.getInstance().displayToastWithQuickClose("网络出错啦！请检查网络");
                        } else if (throwable instanceof SSLHandshakeException) {
                            ToastHelper.getInstance().displayToastWithQuickClose("网络认证失败！");
                        }
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("RHG", "error: " + e.getMessage() + "," + e.getCause());
                    }

                    @Override
                    public void onNext(String message) {
                        Log.i("RHG", message);
                        baseView.showData(message);
                    }
                });
    }

}
