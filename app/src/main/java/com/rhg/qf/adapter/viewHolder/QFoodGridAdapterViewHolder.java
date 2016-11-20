package com.rhg.qf.adapter.viewHolder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * desc:主页Grid适配器
 * author：remember
 * time：2016/5/28 16:17
 * email：1013773046@qq.com
 */
public class QFoodGridAdapterViewHolder {
    private final Context mContext;
    private final SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;

    public QFoodGridAdapterViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        mContext = context;
        mPosition = position;
        mViews = new SparseArray<View>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        mConvertView.setTag(this);
    }

    public static QFoodGridAdapterViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new QFoodGridAdapterViewHolder(context, parent, layoutId, position);
        } else {
            QFoodGridAdapterViewHolder holder = (QFoodGridAdapterViewHolder) convertView.getTag();
            holder.mPosition = position;
            return holder;
        }
    }

    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @param <T>
     * @return
     */
    @SuppressWarnings("checked")
    public <T extends View> T getViewByid(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }

    public int getPosition() {
        return mPosition;
    }


    public QFoodGridAdapterViewHolder setOnTouchListener(int viewId, View.OnTouchListener listener) {
        View view = getViewByid(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    public QFoodGridAdapterViewHolder setVisible(int viewId, boolean visible) {
        View view = getViewByid(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
        return this;
    }

    public QFoodGridAdapterViewHolder setText(int viewId, String text) {
        TextView tv = getViewByid(viewId);
        tv.setText(text);
        return this;
    }

    public QFoodGridAdapterViewHolder setImageResource(int viewId, int resId) {
        ImageView icon = getViewByid(viewId);
        icon.setImageResource(resId);
        return this;
    }

    public QFoodGridAdapterViewHolder setImageUrl(int viewId, String imageUrl) {
        ImageView icon = getViewByid(viewId);
        ImageLoader.getInstance().displayImage(imageUrl, icon);
        return this;
    }

    public QFoodGridAdapterViewHolder setHeaderColor(int viewId, int color) {
        View header = getViewByid(viewId);
        header.setBackgroundResource(color);
        return this;
    }

    public QFoodGridAdapterViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView icon = getViewByid(viewId);
        icon.setImageBitmap(bitmap);
        return this;
    }

    public QFoodGridAdapterViewHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView icon = getViewByid(viewId);
        icon.setImageDrawable(drawable);
        return this;
    }
}