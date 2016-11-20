package com.rhg.qf.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * desc:ViewPager统一适配器
 * author：remember
 * time：2016/5/28 16:20
 * email：1013773046@qq.com
 */
public class QFoodVpAdapter extends FragmentPagerAdapter/* implements ViewPager.OnPageChangeListener*/ {

    private List<Fragment> fragmentList;
    private String[] titles;

    public QFoodVpAdapter(FragmentManager fm, List<Fragment> fragmentList, String[] titles) {
        super(fm);
        this.fragmentList = fragmentList;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return (fragmentList == null || fragmentList.size() == 0) ? null : fragmentList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return fragmentList == null ? 0 : fragmentList.size();
    }

}