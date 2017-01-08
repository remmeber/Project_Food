package com.rhg.qf.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.rhg.qf.adapter.viewHolder.BaseVH;
import com.rhg.qf.bean.IBaseModel;
import com.rhg.qf.bean.InflateModel;
import com.rhg.qf.impl.OnItemClickListener;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

/*
 *desc 列表的主体适配器，与之对应的是包裹适配器
 *author rhg
 *time 2016/12/27 16:38
 *email 1013773046@qq.com
 */

public class MainAdapter<T extends IBaseModel> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private InflateModel VH;
    private T mData;
    private OnItemClickListener<T> onItemClickListener;
    private Class<?>[] clazz;
    private Object[] param;


    public MainAdapter(InflateModel VH, T mData, OnItemClickListener<T> onItemClickListener) {
        this.VH = VH;
        this.mData = mData;
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        if (param == null)
            param = new Object[VH.getParam().length];
        if (clazz == null)
            clazz = new Class[VH.getClazz().length - 1];
        super.onAttachedToRecyclerView(recyclerView);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        try {
            System.arraycopy(VH.getClazz(), 1, clazz, 0, VH.getClazz().length - 1);
            Constructor<?> cons = VH.getClazz()[0].getDeclaredConstructor(clazz);
            cons.setAccessible(true);
            param[0] = LayoutInflater.from(parent.getContext()).inflate((int) VH.getParam()[0], parent, false);
            System.arraycopy(VH.getParam(), 1, param, 1, VH.getParam().length - 1);
            return (RecyclerView.ViewHolder) cons.newInstance(param);
        } catch (NoSuchMethodException e) {
            Log.i("RHG", "message" + e.getMessage());
        } catch (IllegalAccessException e) {
            Log.i("RHG", "message" + e.getMessage());
        } catch (InstantiationException e) {
            Log.i("RHG", "message" + e.getMessage());
        } catch (InvocationTargetException e) {
            Log.i("RHG", "message" + e.getMessage());
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final BaseVH vh = (BaseVH) holder;
        if (mData.getEntity() != null) {
            vh.setOnClick(onItemClickListener);
            vh.convert(vh, position, mData);
        }

    }

    @Override
    public int getItemCount() {
        return mData.getEntity() == null ? 0 : mData.getEntity().size();
    }
}
