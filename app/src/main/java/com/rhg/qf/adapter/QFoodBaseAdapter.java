package com.rhg.qf.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.rhg.qf.adapter.viewHolder.QFoodGridAdapterViewHolder;

import java.util.List;


/**
 * desc:非recycleView的统一适配器
 * author：remember
 * time：2016/5/28 16:19
 * email：1013773046@qq.com
 */
public abstract class QFoodBaseAdapter<T> extends BaseAdapter {

    protected List<T> mDataList;
    protected Context mContext;
    protected int mLayoutId;

//    protected DPOnItemChildLongClickListener mOnItemChildLongClickListener;
//	protected DPOnItemChildClickListener mOnItemChildClickListener;


    public QFoodBaseAdapter(Context mContext, int mLayoutId) {
        this.mContext = mContext;
        this.mLayoutId = mLayoutId;
    }

    public QFoodBaseAdapter(Context context, List<T> list, int layoutId) {
        mContext = context;
        mDataList = list;
        mLayoutId = layoutId;
    }

    public void setmDataList(List<T> mDataList) {
        this.mDataList = mDataList;
    }

    @Override
    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public T getItem(int position) {
        return mDataList == null ? null : mDataList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final QFoodGridAdapterViewHolder holder = QFoodGridAdapterViewHolder.get(mContext, convertView, parent, mLayoutId, position);
//        holder.setOnItemChildClickListener(mOnItemChildClickListener);
//        holder.setOnItemChildLongClickListener(mOnItemChildLongClickListener);
        convert(holder, getItem(position), position);
        return holder.getConvertView();
    }

//    public void setOnItemChildLongClickListener(DPOnItemChildLongClickListener l) {
//        this.mOnItemChildLongClickListener = l;
//    }
//
//	public void setOnItemChildClickListener(DPOnItemChildClickListener l){
//		this.mOnItemChildClickListener = l;
//	}

    public void removeItem(int position) {
        this.mDataList.remove(position);
        this.notifyDataSetChanged();
    }

    private void addItem(int position, T model) {
        this.mDataList.add(position, model);
        this.notifyDataSetChanged();
    }

    public void addFirstItem(T model) {
        this.addItem(0, model);
    }

    public void addLastItem(T model) {
        this.addItem(this.mDataList.size(), model);
    }

    public abstract void convert(QFoodGridAdapterViewHolder holder, T t, int position);
}
