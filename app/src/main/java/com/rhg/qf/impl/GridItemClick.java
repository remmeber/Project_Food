package com.rhg.qf.impl;

import android.view.View;

import com.rhg.qf.bean.FavorableFoodUrlBean;

/**
 * Created by rhg on 2016/12/26.
 */

public interface GridItemClick extends IBase {
    void gridItemClick(View view, FavorableFoodUrlBean.FavorableFoodEntity favorableFoodEntity);
}
