package com.rhg.qf.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.rhg.qf.R;

import java.lang.reflect.Field;

public class PointView extends LinearLayout implements ViewPager.OnPageChangeListener {
    private static final String TAG = "PointView";
    private Context mContext;
    private int mCount;
    private int mCurrentPos = -1;
    private ViewPager.OnPageChangeListener mPointViewChangeListener;
    private ViewPager mViewPager;

    public PointView(Context context) {
        super(context);
        init(context);
    }

    public PointView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PointView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
    }

    public void setParams(ViewPager viewPager) {
        mViewPager = viewPager;
        mPointViewChangeListener = getOnPageChangeListener(mViewPager);
        mViewPager.setOnPageChangeListener(this);
        mCount = mViewPager.getAdapter().getCount();
        initView();
    }

    private void initView() {
        setOrientation(HORIZONTAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        setLayoutParams(layoutParams);
        removeAllViews();
        ImageView icon = null;
        if (mCount == 1)
            return;
        for (int i = 0; i < mCount; i++) {
            icon = new ImageView(mContext);
//            icon.setBackgroundColor(0xFF4081);
            icon.setImageResource(R.drawable.bg_global_search_textbox_gray);
            LayoutParams lp = new LayoutParams(20, 20);
            lp.topMargin = 10;
            lp.leftMargin = 10;
            lp.rightMargin = 10;
            addView(icon, lp);
        }
        updatePoint(mViewPager.getCurrentItem());
    }

    private void updatePoint(int position) {
        if (mCurrentPos != position) {
            if (mCurrentPos == -1) {
                ((ImageView) getChildAt(position)).setImageResource(R.drawable.bg_global_search_textbox_home);
                mCurrentPos = position;
                return;
            }
            ((ImageView) getChildAt(mCurrentPos)).setImageResource(R.drawable.bg_global_search_textbox_gray);
            ((ImageView) getChildAt(position)).setImageResource(R.drawable.bg_global_search_textbox_home);
            mCurrentPos = position;
        }
    }

    private ViewPager.OnPageChangeListener getOnPageChangeListener(ViewPager pager) {
        try {
            Field f = pager.getClass().getDeclaredField("mOnPageChangeListener");
            f.setAccessible(true);
            return (ViewPager.OnPageChangeListener) f.get(pager);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mPointViewChangeListener != null)
            mPointViewChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        updatePoint(position);
        if (mPointViewChangeListener != null)
            mPointViewChangeListener.onPageSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mPointViewChangeListener != null)
            mPointViewChangeListener.onPageScrollStateChanged(state);
    }
}
