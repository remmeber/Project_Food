package com.rhg.qf.mvp.presenter;

import android.util.Log;

import com.rhg.qf.mvp.model.UploadAndSaveImageModel;
import com.rhg.qf.mvp.view.BaseView;

import java.io.File;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
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
