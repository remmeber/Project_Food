package com.rhg.qf.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rhg.qf.bean.IBaseModel;
import com.rhg.qf.impl.OnItemClickListener;

/**
 * 扩展的ViewHolder的抽象类
 *
 * @param <T> 继承于IBaseModel
 */

public abstract class BaseVH<T extends IBaseModel> extends RecyclerView.ViewHolder {
    public BaseVH(View itemView) {
        super(itemView);
    }

    protected OnItemClickListener<T> onClick;

    public void setOnClick(OnItemClickListener<T> onClick) {
        this.onClick = onClick;
    }

    public abstract void convert(RecyclerView.ViewHolder VH,int position, T t);

}
