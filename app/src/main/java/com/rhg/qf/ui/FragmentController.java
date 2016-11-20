package com.rhg.qf.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.List;

/**
 * desc:fm控制类
 * author：remember
 * time：2016/5/28 17:02
 * email：1013773046@qq.com
 */
public class FragmentController {
    FragmentManager fm;
    int size = 0;
    int resId = 0;
    List<Fragment> fragments;
    private int showMark = 0;

    public FragmentController(FragmentManager fm, List<Fragment> fragments, int id) {
        this.fm = fm;
        this.fragments = fragments;
        this.resId = id;
        initFragment(id, 0);
    }

    public FragmentManager getFm() {
        return fm;
    }

    private void initFragment(int resId, int showMark) {
        if (fragments != null) {
            FragmentTransaction ft = fm.beginTransaction();
            for (int i = 0; i < fragments.size(); i++) {
                if (fragments.get(i) == null)
                    break;
                ft.add(resId, fragments.get(i), fragments.get(i).getClass().getName());
                ft.addToBackStack("");
                if (i == showMark) {
                    ft.show(fragments.get(i));
                    fragments.get(i).setUserVisibleHint(true);
                } else {
                    ft.hide(fragments.get(i));
                    fragments.get(i).setUserVisibleHint(false);
                }
            }
            ft.commitAllowingStateLoss();
        }
    }

    public void showFragment(int position) {
        if (fragments != null) {
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.show(fragments.get(position));
            fragments.get(position).setUserVisibleHint(true);
            transaction.hide(fragments.get(showMark));
            fragments.get(showMark).setUserVisibleHint(false);
            showMark = position;
            transaction.commitAllowingStateLoss();
        }
    }

    public void addFm(Fragment fragment) {
        fragments.add(fragment);
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(resId, fragment, fragment.getClass().getName());
        ft.commitAllowingStateLoss();
        for (int i = 0; i < fragments.size(); i++) {
            if (i == showMark)
                ft.show(fragments.get(i));
            else ft.hide(fragments.get(i));
        }
    }

    /*private void notifyChange(){
        FragmentTransaction ft = fm.beginTransaction();
        for (int i = 0; i < fragments.size(); i++) {
            if (i == showMark)
                ft.show(fragments.get(i));
            else ft.hide(fragments.get(i));
        }
        ft.commitAllowingStateLoss();
    }*/

    public Fragment getCurrentFM() {
        return fragments == null ? null : fragments.get(showMark);
    }

    public int getCurrentInt(){
        return showMark;
    }
}
