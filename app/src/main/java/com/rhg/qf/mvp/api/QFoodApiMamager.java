package com.rhg.qf.mvp.api;

import com.rhg.qf.application.InitApplication;
import com.rhg.qf.utils.NetUtil;
import com.rhg.qf.utils.ToastHelper;

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * desc:service管理类
 * author：remember
 * time：2016/5/28 16:53
 * email：1013773046@qq.com
 */
public class QFoodApiMamager {
    private static QFoodApiMamager mInstant;
    private QFoodApiService QFoodApiService;

    private QFoodApiMamager() {
        /*OkHttpClient okHttpClient = new OkHttpClient();
        OkHttpClient.Builder builder= okHttpClient.newBuilder();
        File cacheFile = new File(InitApplication.getInstance().getExternalCacheDir(), "QFood_Cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb
        builder.cache(cache);*/
       /* HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(signingInterceptor)
                .addInterceptor(loggingInterceptor)
                .build();*/
        final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                if (!NetUtil.isConnected(InitApplication.getInstance())) {
                    ToastHelper.getInstance()._toast("no network");
                    return null;
                }
                Request request = chain.request().newBuilder().addHeader("Cache-Control", String.format(Locale.ENGLISH,"max-age=%d", 60)).build();
                return chain.proceed(request);
            }
        };

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .readTimeout(5000, TimeUnit.MILLISECONDS)
                .connectTimeout(5000, TimeUnit.MILLISECONDS)
                .writeTimeout(5000, TimeUnit.MILLISECONDS).build();
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(QFoodApi.BASE_URL)
                .client(okHttpClient)
                .build();
        this.QFoodApiService = retrofit.create(QFoodApiService.class);
    }

    public static QFoodApiMamager getInstant() {
        if (mInstant == null)
            mInstant = new QFoodApiMamager();
        return mInstant;
    }

    public QFoodApiService getQFoodApiService() {
        return QFoodApiService;
    }
}
