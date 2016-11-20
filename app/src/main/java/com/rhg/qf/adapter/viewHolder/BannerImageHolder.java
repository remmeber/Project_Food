package com.rhg.qf.adapter.viewHolder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.rhg.qf.utils.ImageUtils;

/**
 * desc:图片轮播页面
 * author：remember
 * time：2016/5/28 16:15
 * email：1013773046@qq.com
 */
public class BannerImageHolder implements Holder<String> {
    ImageView imageView;

    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, String data) {
        ImageUtils.showImage(data, imageView);
    }
}
