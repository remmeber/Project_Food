package com.rhg.qf.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.rhg.qf.adapter.viewHolder.QFoodGridAdapterViewHolder;

import java.util.List;


/*
 *desc
 *author rhg
 *time 2016/10/9 10:37
 *email 1013773046@qq.com
 */
public abstract class DPBasePageAdapter<T> extends PagerAdapter {

    protected List<T> mDataList;
    protected Context mContext;
    protected int mLayoutId;
    protected boolean mIsInfiniteLoop;

    /**
     * @param context  context
     * @param list     data
     * @param layoutId layout id
     */
    public DPBasePageAdapter(Context context, List<T> list, int layoutId) {
        mContext = context;
        mDataList = list;
        mLayoutId = layoutId;
    }

    /**
     * @param context        context
     * @param list           data
     * @param layoutId       layout id
     * @param isInfiniteLoop is loop
     */
    public DPBasePageAdapter(Context context, List<T> list, int layoutId, boolean isInfiniteLoop) {
        this(context, list, layoutId);
        mIsInfiniteLoop = isInfiniteLoop;
    }

    @Override
    public int getCount() {
        return mIsInfiniteLoop ? Integer.MAX_VALUE : mDataList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = null;
        view = getView(position, view, container);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public T getItem(int position) {
        return mDataList.get(position);
    }

    private int getPosition(int position) {
        return mIsInfiniteLoop ? position % mDataList.size() : position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final QFoodGridAdapterViewHolder holder = QFoodGridAdapterViewHolder.get(mContext, convertView, parent, mLayoutId, position);
        convert(holder, getItem(getPosition(position)));
        return holder.getConvertView();
    }

    public abstract void convert(QFoodGridAdapterViewHolder holder, T t);

}
