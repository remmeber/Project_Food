package com.rhg.qf.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.rhg.qf.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

/*
 *desc
 *author rhg
 *time 2016/12/14 16:30
 *email 1013773046@qq.com
 */

public class BannerLoopAdapter extends LoopPagerAdapter {
    List<String> imgSrc = new ArrayList<>();

    public BannerLoopAdapter(RollPagerView viewPager) {
        super(viewPager);
    }

    public void setImgSrc(List<String> imgSrc) {
        this.imgSrc = imgSrc;
        notifyDataSetChanged();
    }

    @Override
    public View getView(ViewGroup container, int position) {
        ImageView imageView = new ImageView(container.getContext());
        ImageUtils.showImage(imgSrc.get(position), imageView);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return imageView;
    }

    @Override
    public int getRealCount() {
        return imgSrc == null ? 0 : imgSrc.size();
    }
}
