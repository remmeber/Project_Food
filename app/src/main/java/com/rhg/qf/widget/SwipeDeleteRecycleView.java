package com.rhg.qf.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/*
 *desc
 *author rhg
 *time 2016/11/15 13:16
 *email 1013773046@qq.com
 */

public class SwipeDeleteRecycleView extends RecyclerView {

    SwipeDeleteLayout mExpandedLayout;

    public SwipeDeleteRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    public boolean canScrollVertically(int direction) {
        if (hasExpandState()) {
            mExpandedLayout.shrink();
            return false;
        }
        return super.canScrollVertically(direction);
    }


    boolean hasExpandState() {
        return mExpandedLayout != null;
    }

    public boolean setExpandedSwipeLayout(SwipeDeleteLayout mExpandedLayout) {
        if (mExpandedLayout != null && this.mExpandedLayout != null) {
            if (isSameObj(mExpandedLayout))
                return false;
            else {
                if (this.mExpandedLayout.getState() != SwipeDeleteLayout.SHRINK) {
                    this.mExpandedLayout.shrink();
                    return false;
                }
            }
        }
        this.mExpandedLayout = mExpandedLayout;
        return true;
    }

    boolean isSameObj(SwipeDeleteLayout mExpandedLayout) {
        return mExpandedLayout == this.mExpandedLayout;
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mExpandedLayout != null)
            mExpandedLayout = null;
    }
}
