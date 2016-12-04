package com.rhg.qf.widget;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * desc:
 * author：remember
 * time：2016/6/7 15:58
 * email：1013773046@qq.com
 */
public class SwipeDeleteExpandListView extends ExpandableListView{

    SwipeDeleteLayout mExpandedLayout;

    public SwipeDeleteExpandListView(Context context) {
        super(context);
        init();
    }


    public SwipeDeleteExpandListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SwipeDeleteExpandListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        ViewCompat.setNestedScrollingEnabled(this, true);

    }

    @Override
    final public boolean canScrollVertically(int direction) {
        if (hasExpandState()) {
            if (mExpandedLayout.getState() == SwipeDeleteLayout.EXPAND)
                close();
            return false;
        }
        return false;
    }


    public boolean hasExpandState() {
        return mExpandedLayout != null;
    }

    public void close(){
        mExpandedLayout.shrink();
    }


    /**
     * @param mExpandedLayout 滑动的对象
     * @return 当前对象的滑动是否能成功，成功则赋值。
     */
    public boolean setExpandedSwipeLayout(SwipeDeleteLayout mExpandedLayout) {
        if (mExpandedLayout != null && this.mExpandedLayout != null) {
//            Log.i("RHG", mExpandedLayout + "都不空");
            if (mExpandedLayout == this.mExpandedLayout) {
//                Log.i("RHG", mExpandedLayout + "同一对象");
                return false;
            } else {
                if (this.mExpandedLayout.getState() != SwipeDeleteLayout.SHRINK) {
//                    Log.i("RHG", mExpandedLayout + "不同一对象，先关闭");
                    this.mExpandedLayout.shrink();
                    return false;
                }
            }
        }
//        Log.i("RHG", mExpandedLayout + "直接赋值");
        this.mExpandedLayout = mExpandedLayout;
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mExpandedLayout != null) {
            mExpandedLayout = null;
        }
    }


   /* *//**
     * 获取点击的item position
     *
     * @param adapter
     * @param clickPosition
     * @return
     *//*
    private Position getPosition(ExpandableListAdapter adapter, int clickPosition) {
        Position position = new Position();
        int totalCount = 0;
        if (clickPosition <= 0) {
            position.setGroup(-1);
            position.setChild(-1);
            return position;
        }
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            totalCount += adapter.getChildrenCount(i) + 1;
            if (clickPosition + 1 <= totalCount) {
                position.setGroup(i);
                position.setChild(clickPosition - (i == 0 ? 0 : totalCount - adapter.getChildrenCount(i) - 1) - 1);
                break;
            }
        }
        return position;
    }


    public class Position {
        private int group = -1;
        private int child = -1;

        public int getGroup() {
            return group;
        }

        public void setGroup(int group) {
            this.group = group;
        }

        public int getChild() {
            return child;
        }

        public void setChild(int child) {
            this.child = child;
        }

        @Override
        public String toString() {
            return "Group: " + group + " Child: " + child;
        }
    }*/

}
