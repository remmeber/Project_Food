package com.rhg.qf.impl;

import android.view.View;

/**
 * desc:RecycleView点击统一回调
 * author：remember
 * time：2016/6/19 14:36
 * email：1013773046@qq.com
 */
public interface RcvItemClickListener<T> {
    void onItemClickListener(View view, int position, T item);
}
