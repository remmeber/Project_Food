package com.rhg.qf.adapter;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.rhg.qf.adapter.viewHolder.BaseVH;
import com.rhg.qf.bean.IBaseModel;
import com.rhg.qf.bean.InflateModel;
import com.rhg.qf.impl.OnItemClickListener;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/*
 *desc adapter包装类
 *author rhg
 *time 2016/12/26 17:49
 *email 1013773046@qq.com
 */

public class WrapperAdapter<T extends IBaseModel> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int BASE_ITEM_TYPE_HEADER = 100000;
    private static final int BASE_ITEM_TYPE_FOOTER = 200000;
    //    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
//    private SparseArrayCompat<View> mFootViews = new SparseArrayCompat<>();
    private SparseArrayCompat<InflateModel> mHeaderViews = new SparseArrayCompat<>();
    private SparseArrayCompat<InflateModel> mFootViews = new SparseArrayCompat<>();
    private SparseArrayCompat<T> mHeaderData = new SparseArrayCompat<>();
    private SparseArrayCompat<T> mFooterData = new SparseArrayCompat<>();

    private RecyclerView.Adapter<RecyclerView.ViewHolder> mInnerAdapter;
    private OnItemClickListener<T> onClick;
//    private RecyclerView.ViewHolder VH;


    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        mInnerAdapter.onViewAttachedToWindow(holder);
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        mInnerAdapter.onAttachedToRecyclerView(recyclerView);
        super.onAttachedToRecyclerView(recyclerView);
    }

    public WrapperAdapter(RecyclerView.Adapter<RecyclerView.ViewHolder> mInnerAdapter) {
        this(mInnerAdapter, null);
    }

    RecyclerView.AdapterDataObserver observer = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            /*for (int i = 0;i<mHeaderViews.size();i++)
                mHeaderViews.get(i+BASE_ITEM_TYPE_HEADER).update()*/
            mInnerAdapter.notifyDataSetChanged();
            super.onChanged();
        }
    };

    public WrapperAdapter(final RecyclerView.Adapter<RecyclerView.ViewHolder> mInnerAdapter, OnItemClickListener<T> onClick) {
        this.mInnerAdapter = mInnerAdapter;
        this.onClick = onClick;
        registerAdapterDataObserver(observer);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Class<?>[] clazz;
        Object[] param;
        try {
            if (mHeaderViews.get(viewType) != null) {
                clazz = new Class[mHeaderViews.get(viewType).getClazz().length - 1];
                System.arraycopy(mHeaderViews.get(viewType).getClazz(), 1, clazz, 0, mHeaderViews.get(viewType).getClazz().length - 1);
                Constructor<?> cons = mHeaderViews.get(viewType).getClazz()[0].getDeclaredConstructor(clazz);
                param = new Object[mHeaderViews.get(viewType).getParam().length];
                param[0] = LayoutInflater.from(parent.getContext()).inflate((int) mHeaderViews.get(viewType).getParam()[0], parent, false);
                System.arraycopy(mHeaderViews.get(viewType).getParam(), 1, param, 1, mHeaderViews.get(viewType).getParam().length - 1);
                return (RecyclerView.ViewHolder) cons.newInstance(param);
            }
            if (mFootViews.get(viewType) != null) {
                clazz = new Class[mFootViews.get(viewType).getClazz().length - 1];
                System.arraycopy(mFootViews.get(viewType).getClazz(), 1, clazz, 0, mFootViews.get(viewType).getClazz().length - 1);
                Constructor<?> cons = mFootViews.get(viewType).getClazz()[0].getDeclaredConstructor(clazz);
                param = new Object[mFootViews.get(viewType).getParam().length];
                param[0] = LayoutInflater.from(parent.getContext()).inflate((int) mFootViews.get(viewType).getParam()[0], parent, false);
                System.arraycopy(mFootViews.get(viewType).getParam(), 1, param, 1, mFootViews.get(viewType).getParam().length - 1);
                return (RecyclerView.ViewHolder) cons.newInstance(param);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isFooterType(position)) {
            ((BaseVH) holder).setOnClick(onClick);
            ((BaseVH) holder).convert(holder, position - mHeaderViews.size() - mInnerAdapter.getItemCount(), mFooterData.get(getItemViewType(position)));
        } else if (isHeaderType(position)) {
            ((BaseVH) holder).setOnClick(onClick);
            ((BaseVH) holder).convert(holder, position, mHeaderData.get(getItemViewType(position)));
        } else
            mInnerAdapter.onBindViewHolder(holder, position - mHeaderViews.size());
    }

    @Override
    public int getItemCount() {
        return mInnerAdapter.getItemCount() + mFootViews.size() + mHeaderViews.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderType(position))
            return mHeaderViews.keyAt(position);
        if (isFooterType(position))
            return mFootViews.keyAt(position - mHeaderViews.size() - mInnerAdapter.getItemCount());
        return mInnerAdapter.getItemViewType(position - mHeaderViews.size());
    }


    private boolean isFooterType(int position) {
        return position >= mInnerAdapter.getItemCount() + mHeaderViews.size();
    }

    private boolean isHeaderType(int position) {
        return position < mHeaderViews.size();
    }

    public void addHeaderViews(InflateModel vh) {
        mHeaderViews.put(mHeaderViews.size() + BASE_ITEM_TYPE_HEADER, vh);
        mHeaderData.put(mHeaderData.size() + BASE_ITEM_TYPE_HEADER, null);
//        Log.i("RHG", mHeaderData.toString());
    }

    public void addHeaderViews(InflateModel vh, T t) {
        mHeaderViews.put(mHeaderViews.size() + BASE_ITEM_TYPE_HEADER, vh);
        mHeaderData.put(mHeaderData.size() + BASE_ITEM_TYPE_HEADER, t);
//        Log.i("RHG", mHeaderData.toString());
    }

    public void addHeaderViews(InflateModel vh, List<T> tlist) {
        for (T t : tlist) {
            mHeaderViews.put(mHeaderViews.size() + BASE_ITEM_TYPE_HEADER, vh);
            mHeaderData.put(mHeaderData.size() + BASE_ITEM_TYPE_HEADER, t);
        }
    }

    public void addFooterViews(InflateModel vh) {
        mFootViews.put(mFootViews.size() + BASE_ITEM_TYPE_FOOTER, vh);
        mFooterData.put(mFooterData.size() + BASE_ITEM_TYPE_FOOTER, null);
    }

    public void addFooterViews(InflateModel vh, T tList) {
        mFootViews.put(mFootViews.size() + BASE_ITEM_TYPE_FOOTER, vh);
        mFooterData.put(mFooterData.size() + BASE_ITEM_TYPE_FOOTER, tList);
    }

    public void addFooterViews(InflateModel vh, List<T> tList) {
        for (T t : tList) {
            mFootViews.put(mFootViews.size() + BASE_ITEM_TYPE_FOOTER, vh);
            mFooterData.put(mFooterData.size() + BASE_ITEM_TYPE_FOOTER, t);
        }
    }

    public void clear() {
        onClick = null;
        mInnerAdapter = null;
        mHeaderViews = null;
        mHeaderData = null;
        mFootViews = null;
        mFooterData = null;
        unregisterAdapterDataObserver(observer);
    }
}
