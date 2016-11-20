package com.rhg.qf.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.rhg.qf.R;
import com.rhg.qf.utils.SizeUtil;

import java.util.List;

/**
 * desc:
 * author：remember
 * time：2016/7/29 22:44
 * email：1013773046@qq.com
 */
public class VerticalTabLayout extends ScrollView implements View.OnClickListener {
    private final static int DEFAULT_SELECT_TEXT_COLOR = Color.WHITE;
    private final static int DEFAULT_SELECT_BG_COLOR = Color.BLUE;
    private final static int DEFAULT_UNSELECT_TEXT_COLOR = Color.BLACK;
    private final static int DEFAULT_UNSELECT_BG_COLOR = Color.GRAY;
    Context mContext;
    LinearLayout mLayout;
    int tabCount;
    int lastPosition;
    int itemHeight;
    int itemWidth;
    float textSize;
    int selectTextColor;
    int unSelectTextColor;
    int selectBgColor;
    int unSelectBgColor;
    private List<String> titles;
    private VerticalTabClickListener mVerticalTabClickListener;

    public VerticalTabLayout(Context context) {

        this(context, null, 0);

    }

    public VerticalTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        setScrollContainer(true);
        setScrollbarFadingEnabled(true);
        setScrollBarStyle(SCROLLBARS_OUTSIDE_OVERLAY);
        ontainAttr(context, attrs);


        mLayout = new LinearLayout(context);
        mLayout.setOrientation(LinearLayout.VERTICAL);
        /*LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(context, attrs);
        layoutParams.height = LinearLayout.LayoutParams.MATCH_PARENT;
        layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT;*/
        mLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT
                , LinearLayout.LayoutParams.MATCH_PARENT));

        addView(mLayout);

    }

    /*public void setVp(ViewPager vp) {
        if (vp == null || vp.getAdapter() == null) {
            throw new IllegalStateException("ViewPager or ViewPager adapter can not be NULL !");
        }
        this.vp = vp;
        this.vp.removeOnPageChangeListener(this);
        this.vp.addOnPageChangeListener(this);
        notifyDataSetChanged();
    }*/

    private void ontainAttr(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.VerticalTabLayout);
        if (a.hasValue(R.styleable.VerticalTabLayout_item_height)) {
            itemHeight = a.getDimensionPixelSize(R.styleable.VerticalTabLayout_item_height, 0);
        }
        if (a.hasValue(R.styleable.VerticalTabLayout_item_width)) {
            itemWidth = a.getDimensionPixelSize(R.styleable.VerticalTabLayout_item_width, 0);
        }
        if (a.hasValue(R.styleable.VerticalTabLayout_item_text_size)) {
            textSize = a.getDimension(R.styleable.VerticalTabLayout_item_text_size, SizeUtil.sp2px(14));
        }
        if (a.hasValue(R.styleable.VerticalTabLayout_item_click_text_color)) {
            selectTextColor = a.getColor(R.styleable.VerticalTabLayout_item_click_text_color, DEFAULT_SELECT_TEXT_COLOR);
        }
        if (a.hasValue(R.styleable.VerticalTabLayout_item_unclick_text_color)) {
            unSelectTextColor = a.getColor(R.styleable.VerticalTabLayout_item_unclick_text_color, DEFAULT_UNSELECT_TEXT_COLOR);
        }
        if (a.hasValue(R.styleable.VerticalTabLayout_item_click_bg_color)) {
            selectBgColor = a.getColor(R.styleable.VerticalTabLayout_item_click_bg_color, DEFAULT_SELECT_BG_COLOR);
        }
        if (a.hasValue(R.styleable.VerticalTabLayout_item_unclick_bg_color)) {
            unSelectBgColor = a.getColor(R.styleable.VerticalTabLayout_item_unclick_bg_color, DEFAULT_UNSELECT_BG_COLOR);
        }
        a.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    private void notifyDataSetChanged() {
        mLayout.removeAllViews();
        this.tabCount = titles == null ? 0 : titles.size();
        for (int i = 0; i < tabCount; i++) {
            final int position = i;
            TextView textview = new TextView(mContext);
            LinearLayout.LayoutParams _lp = new LinearLayout.LayoutParams(
                    itemWidth == 0 ? LinearLayout.LayoutParams.WRAP_CONTENT : /*dip2px(itemWidth)*/ itemWidth,
                    itemHeight == 0 ? LinearLayout.LayoutParams.WRAP_CONTENT : /*dip2px(itemHeight)*/itemHeight
            );
            _lp.setMargins(SizeUtil.dip2px(5), SizeUtil.dip2px(15), SizeUtil.dip2px(5), SizeUtil.dip2px(5));
            textview.setLayoutParams(_lp);
            textview.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            CharSequence pageTitle = titles.get(i);
            textview.setText(pageTitle);
            if (i == 0) {
                textview.setTextColor(selectTextColor);
                textview.setBackgroundColor(selectBgColor);
            } else {
                textview.setTextColor(unSelectTextColor);
                textview.setBackgroundColor(unSelectBgColor);
            }
            textview.setGravity(Gravity.CENTER);
            textview.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (lastPosition != position) {
                        if (mVerticalTabClickListener != null)
                            mVerticalTabClickListener.onVerticalTabClick(position);
                        changeTabState(position);
                    }
                }
            });
            mLayout.addView(textview, i, _lp);
        }
    }

    public void setOnVerticalTabClickListener(VerticalTabClickListener mVerticalTabClickListener) {
        this.mVerticalTabClickListener = mVerticalTabClickListener;
    }

    public void changeTabState(int position) {
        if (lastPosition != position) {
            View _view = mLayout.getChildAt(position);
            TextView tv = (TextView) _view;
            tv.setTextColor(selectTextColor);
            tv.setBackgroundColor(selectBgColor);
            _view = mLayout.getChildAt(lastPosition);
            tv = (TextView) _view;
            tv.setTextColor(unSelectTextColor);
            tv.setBackgroundColor(unSelectBgColor);
            lastPosition = position;
        }
    }

    /*public int getCurrentPosition() {
        return lastPosition;
    }*/

    @Override
    public void onClick(View v) {

    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
        notifyDataSetChanged();
    }

    public interface VerticalTabClickListener {
        void onVerticalTabClick(int position);
    }
}
