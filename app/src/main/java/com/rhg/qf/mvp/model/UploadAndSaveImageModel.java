package com.rhg.qf.mvp.model;

import com.rhg.qf.bean.BaseBean;
import com.rhg.qf.mvp.api.QFoodApiMamager;
import com.rhg.qf.utils.AccountUtil;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/*
 *desc 上传图片Model
 *author rhg
 *time 2016/7/7 20:25
 *email 1013773046@qq.com
 */
public class UploadAndSaveImageModel {

    public Observable<String> UploadAndSaveImage(File file) {
        String userName = AccountUtil.getInstance().getUserName();
        String pwd = AccountUtil.getInstance().getPwd();
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        RequestBody desc_userName =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), userName);
        RequestBody desc_pwd =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), pwd);
        MultipartBody.Part part = MultipartBody.Part.createFormData("Pic", file.getName(), requestBody);
        return QFoodApiMamager.getInstant().getQFoodApiService().UploadHeadImage(part, desc_userName, desc_pwd)
                .flatMap(new Func1<BaseBean, Observable<String>>() {
                    @Override
                    public Observable<String> call(final BaseBean baseBean) {
                        return Observable.create(new Observable.OnSubscribe<String>() {
                            @Override
                            public void call(Subscriber<? super String> subscriber) {
                                //Log.i("RHG", "DONE");
                                subscriber.onNext("" + baseBean.getMsg());
                            }
                        });
                    }
                });
    }
}
