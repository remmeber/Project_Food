package com.rhg.qf.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/*
 *desc 自定义GridView 重写onMeasure方法，解决recycleView中，内容显示不全的问题
 *author rhg
 *time 2016/7/9 21:49
 *email 1013773046@qq.com
 */
public class MyGridView extends GridView {

    public MyGridView(Context context) {
        super(context);
    }

    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
