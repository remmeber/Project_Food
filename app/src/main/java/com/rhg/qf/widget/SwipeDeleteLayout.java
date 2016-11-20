package com.rhg.qf.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Scroller;

/*
 *desc 自定义侧滑删除
 *author rhg
 *time 2016/11/15 11:12
 *email 1013773046@qq.com
 */

public class SwipeDeleteLayout extends LinearLayout {

    public static final int EXPAND = 1;
    public static final int EXPANDING = 4;
    public static final int SHRINK = 2;
    int state = SHRINK;

    float startX;
    float startY;
    /**
     * 用来判断当前滑动是否需要拦截,用于onInterceptTouchEvent
     */
    boolean intercept;
    Scroller mScroller;
    View leftView;
    View rightView;
    ViewParent mParent;
    int rightWidth;
    int mTouchSlop;
    static final int animationTime = 100;

    public SwipeDeleteLayout(Context context, AttributeSet attrs) {
        this(context, attrs, null);
    }

    public SwipeDeleteLayout(Context context, AttributeSet attrs, Interpolator anim) {
        super(context, attrs);
        if (anim == null)
            mScroller = new Scroller(context, new AccelerateDecelerateInterpolator());
        else mScroller = new Scroller(context, anim);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = ev.getX();
                startY = ev.getY();
                if (state == SHRINK) {
                    //用来避免一直手指在滑动的时候，另一只手指在别的对象点击或者活动的冲突。(即：不允许多个手指同时操作，类似QQ的侧滑)
                    if (((mParent instanceof ExpandableListView) && ((SwipeDeleteExpandListView) mParent).setExpandedSwipeLayout(this))
                            || ((mParent instanceof RecyclerView) && ((SwipeDeleteRecycleView) mParent).setExpandedSwipeLayout(this))) {
                        intercept = false;//如果条件返回true，则说明当前对象可以进行后续滑动
                        disallowParentsInterceptTouchEvent(getParent());
                    } else {
                        intercept = true;//条件返回false，说明在此对象前面还有一个对象在操作，因此开启当前对象的拦截机制，拦截对象内的时间传递。
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float newX = ev.getX();
                float newY = ev.getY();
                float dx = newX - startX;
                float dy = newY - startY;
                if (Math.abs(dy) > mTouchSlop || Math.abs(dx) > mTouchSlop) {//在侧滑大于滑动间隙的时候有效
                    if (state == SHRINK && dx > 0) {//在侧滑状态收缩、右滑的时候无效
                        allowParentsInterceptTouchEvent(getParent());
                        break;
                    }
                    if (Math.abs(dy) > Math.abs(dx) && state == SHRINK) {//垂直滑动
                        allowParentsInterceptTouchEvent(getParent());
                        Log.i("RHG", "MOVE DONE VERTICAL");
                        intercept = false;
                    } else if (Math.abs(dx) / Math.abs(dy) >= 2) {//水平滑动
                        Log.i("RHG", "MOVE DONE HORIZONTAL");
                        disallowParentsInterceptTouchEvent(getParent());
                        intercept = true;
                        setExpandedView(this);
                    }

                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        Log.i("RHG", this + "DONE....intercept: " + intercept);
        if (state == EXPAND || state == EXPANDING) {
            Rect rect = new Rect();
            getChildAt(1).getHitRect(rect);//获得侧滑视图的点击区域
//            Log.i("RHG", "rect: " + rect.toString() + " ,ev.getX：" + ev.getX() + " ,ev.getY: " + ev.getY());
            if (rect.contains((int) ev.getX() + rightWidth, (int) ev.getY())) {
//                Log.i("RHG", "SHOULD CLICK....");
                mScroller.startScroll(getScrollX(), 0, -getScrollX(), 0, animationTime);//点击完都关闭
                invalidate();
                return false;
            }
            mScroller.startScroll(getScrollX(), 0, -getScrollX(), 0, animationTime);//点击完都关闭
            invalidate();
            return true;
        }

        return intercept || super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                float newX = event.getX();
                float newY = event.getY();
                int dx = (int) (newX - startX);
                int dy = (int) (newY - startY);
                if (getScrollX() + (-dx) <= rightWidth) {
                    scrollTo(getScrollX() + (-dx), 0);
                } else {
                    scrollTo(rightWidth, 0);
                }
                invalidate();
                startX = newX;
                startY = newY;
                break;
            case MotionEvent.ACTION_UP:
                if (getScrollX() < rightWidth / 2) {
                    mScroller.startScroll(getScrollX(), 0, -getScrollX(), 0, animationTime);
//                    Log.i("RHG", this + "UPDONG<1/2");
                } else {
                    mScroller.startScroll(getScrollX(), 0, rightWidth - getScrollX(), 0, animationTime);
//                    Log.i("RHG", this + "UPDONG>1/2");
                }
                invalidate();
                allowParentsInterceptTouchEvent(getParent());
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        switch (mode) {
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.EXACTLY:
            case MeasureSpec.UNSPECIFIED:
                break;
        }
        measureChildren(widthMeasureSpec, heightMeasureSpec);

//        measureChild(getChildAt(1),widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        leftView = getChildAt(0);
        rightView = getChildAt(1);
        rightWidth = rightView.getMeasuredWidth();
        leftView.layout(l, 0, r, b - t);
        mParent = getParent();
//        LayoutParams lp = (LayoutParams) rightView.getLayoutParams();
//        Log.i("RHG", "lp.gravity before: " + lp.gravity);
//        lp.gravity = Gravity.CENTER;
//        rightView.setLayoutParams(lp);
//        Log.i("RHG", "lp.gravity: " + lp.gravity);
        rightView.layout(r, 0, r + rightWidth, b - t);
//        rightView.invalidate();
//        Log.i("RHG", "l: " + l + " ,t: " + t + " ,r: " + r + " ,b: " + b);
    }

    public int getState() {
        return state;
    }

    public void shrink() {
        if (mScroller != null) {
            mScroller.startScroll(getScrollX(), 0, -getScrollX(), 0, animationTime);//点击完都关闭
            invalidate();
        }
    }


    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        } else {
            if (getScrollX() == 0) {
                Log.i("RHG", "shrink.....");
                intercept = false;
                state = SHRINK;
                setExpandedView(null);
            } else if (getScrollX() == rightWidth) {
                Log.i("RHG", "expand.....");
                setExpandedView(this);
                state = EXPAND;
            } else {
                Log.i("RHG", "expanding.....");
                state = EXPANDING;
            }

        }
        super.computeScroll();
    }

    private void setExpandedView(SwipeDeleteLayout mExpandedLayout) {
        if (mParent instanceof RecyclerView) {
//                    Log.i("RHG", "SwipeDeleteRecycleView.....");
            ((SwipeDeleteRecycleView) mParent).setExpandedSwipeLayout(mExpandedLayout);
        } else if (mParent instanceof ExpandableListView) {
//                    Log.i("RHG", "SwipeDeleteExpandListView.....");
            ((SwipeDeleteExpandListView) mParent).setExpandedSwipeLayout(mExpandedLayout);
        }
    }

    private void disallowParentsInterceptTouchEvent(ViewParent parent) {
        Log.i("RHG", "disallow.....");
        if (null == parent) {
            return;
        }
        parent.requestDisallowInterceptTouchEvent(true);
    }

    private void allowParentsInterceptTouchEvent(ViewParent parent) {
        Log.i("RHG", "allow.....");
        if (null == parent) {
            return;
        }
        parent.requestDisallowInterceptTouchEvent(false);
    }
}
