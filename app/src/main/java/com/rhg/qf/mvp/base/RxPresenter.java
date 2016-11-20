package com.rhg.qf.mvp.base;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * desc:请求Presenter
 * author：remember
 * time：2016/10/26 14:10
 * email：1013773046@qq.com
 */

public abstract class RxPresenter<V> implements IPresenter<V> {
    private CompositeSubscription subscriptions;
    protected V view;

    protected void addSubscription(Subscription subscription) {
        if (subscriptions == null)
            subscriptions = new CompositeSubscription();
        subscriptions.add(subscription);
    }

    private void unSubscribe() {
        if (subscriptions != null)
            subscriptions.unsubscribe();
    }

    @Override
    public void attachView(V view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        if (view != null)
            view = null;
        unSubscribe();
    }
}
