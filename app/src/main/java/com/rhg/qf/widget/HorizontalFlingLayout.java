package com.rhg.qf.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * 可以左右拉伸的Layout
 */
public class HorizontalFlingLayout extends LinearLayout {
    boolean isWipeValid = false;
    private Scroller mScroller;
    private View mLeftView;
    private View mRightView;
    private float mInitX, mInitY;
    private float mOffsetX, mOffsetY;

    public HorizontalFlingLayout(Context context) {
        this(context, null);
    }

    public HorizontalFlingLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalFlingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.setOrientation(LinearLayout.HORIZONTAL);

        mScroller = new Scroller(getContext(), null, true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (getChildCount() != 2) {
            throw new RuntimeException("Only need two child view! Please check you xml file!");
        }

        mLeftView = getChildAt(0);
        mRightView = getChildAt(1);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        float validNum = (float) mLeftView.getWidth() * 1 / 3;
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mInitX = ev.getX();
                mInitY = ev.getY();
                Log.i("RHG", "X: " + mInitX + " Y: " + mInitY);
//                if (mInitX > validNum) {
//                    isWipeValid = true;
//                    return true;
//                } else
//                    isWipeValid = false;
                break;
            case MotionEvent.ACTION_MOVE:
                //>0为手势向右下
                mOffsetX = ev.getX() - mInitX;
                mOffsetY = ev.getY() - mInitY;
                float absOffset = Math.abs(mOffsetX);
                //横向手势跟随移动
                Log.i("RHG", "offset is :" + absOffset);
                if (absOffset > 1)
                    isWipeValid = true;
                else isWipeValid = false;
                if (isWipeValid && absOffset > ViewConfiguration.getTouchSlop()) {
                    int offset = (int) -mOffsetX;
                    if (getScrollX() + offset > mRightView.getWidth() || getScrollX() + offset < 0) {
                        return true;
                    }
                    this.scrollBy(offset, 0);
                    mInitX = ev.getX();
                    mInitY = ev.getY();
                    return true;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                //松手时刻滑动
                if (isWipeValid) {
                    int offset = ((getScrollX() / (float) mRightView.getWidth()) > 0.5) ? mRightView.getWidth() : 0;
                    //Log.i("RHG1", "mRightView.getWidth(): " + mRightView.getWidth() + "; up_offset: " + offset + "; getScrollX(): " + this.getScrollX());
//                this.scrollTo(offset, 0);
                    mScroller.startScroll(this.getScrollX(), this.getScrollY(), offset - this.getScrollX(), 0);
                    invalidate();
                    mInitX = 0;
                    mInitY = 0;
                    mOffsetX = 0;
                    mOffsetY = 0;
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            this.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
//            Log.i("RHG2", "mScroller.getCurrX(): " + mScroller.getCurrX()+ "; mScroller.getCurrY(): " + mScroller.getCurrY());
            postInvalidate();
        }
    }
}
