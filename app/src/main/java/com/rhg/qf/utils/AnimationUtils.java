package com.rhg.qf.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * desc:动画工具类
 * author：remember
 * time：2016/6/5 13:51
 * email：1013773046@qq.com
 */
public class AnimationUtils {
    public static void alphaAnimation(final View view, float start, float end, long time) {
        view.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        ObjectAnimator oa = ObjectAnimator.ofFloat(view, "alpha", start, end);
        oa.setDuration(time);
        oa.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setLayerType(View.LAYER_TYPE_NONE, null);
            }
        });
        oa.start();
    }
}
