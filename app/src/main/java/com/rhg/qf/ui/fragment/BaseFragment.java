package com.rhg.qf.ui.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rhg.qf.constants.AppConstants;
import com.rhg.qf.locationservice.LocationService;
import com.rhg.qf.locationservice.MyLocationListener;
import com.rhg.qf.mvp.view.BaseView;
import com.rhg.qf.utils.NetUtil;
import com.rhg.qf.utils.ToastHelper;

import butterknife.ButterKnife;


/**
 * desc:所有fm的基类
 * author：remember
 * time：2016/5/28 16:45
 * email：1013773046@qq.com
 */
public abstract class BaseFragment extends Fragment implements BaseView {
    private boolean isViewPrepare = false;
    boolean hasFetchData = false;
    //TODO 百度地图
    private LocationService locationService;
    private MyLocationListener mLocationListener;
    protected View view;
    protected Activity mActivity;
//    private Unbinder bind;

    public BaseFragment() {
    }

    // TODO: 子类重写该方法，获取数据的统一入口
    public void receiveData(Bundle arguments) {
        if (AppConstants.DEBUG)
            Log.i("RHG", "...........receiveData");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (AppConstants.DEBUG)
            Log.i("RHG", "...........onCreateView");
        receiveData(getArguments());
        view = inflater.inflate(getLayoutResId(), container, false);
        ButterKnife.bind(this, view);
        initView(view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (AppConstants.DEBUG)
            Log.i("RHG", "...........onViewCreated");
        isViewPrepare = true;
        loadDataIfPrepared();
        fillData();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (AppConstants.DEBUG)
            Log.i("RHG", "...........onActivityCreated");
        initData();
    }

    /**
     * UI从后台回显示出来后会调用该生命周期
     */
    @Override
    public void onStart() {
        super.onStart();
        if (AppConstants.DEBUG)
            Log.i("RHG", "...........onStart");
        if (getUserVisibleHint() && hasFetchData) {
            refresh();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (AppConstants.DEBUG)
            Log.i("RHG", "...........onResume");
    }

    protected void refresh() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    public int getLayoutResId() {
        return 0;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            loadDataIfPrepared();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                final String permission = permissions[i];
                onDeny(permission);
                return;
            }
        }
        onGrant();
    }

    public void onGrant() {

    }

    public void onDeny(String permission) {
    }

    private void loadDataIfPrepared() {
        if (getUserVisibleHint() && !hasFetchData && isViewPrepare) {
            if (!NetUtil.isConnected(getContext())) {
                ToastHelper.getInstance()._toast("网络未连接");
            } else
                loadData();
            hasFetchData = true;
        }
    }

    protected void startLoc() {
        if ((locationService = GetMapService()) != null) {
            if ((mLocationListener = getLocationListener()) != null) {
                locationService.registerListener(mLocationListener);
                locationService.setLocationOption(locationService.getDefaultLocationClientOption());
                mLocationListener.getLocation(locationService);
            }

        }
    }

    public void reStartLocation() {
        if (locationService == null) {
            locationService = GetMapService();
        }
        if (mLocationListener == null) {
            mLocationListener = getLocationListener();
        }
        locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        locationService.registerListener(mLocationListener);
//        getLocation(locationService, mLocationListener);
        mLocationListener.getLocation(locationService);
    }

    public MyLocationListener getLocationListener() {
        return null;
    }

    public void getLocation(LocationService locationService, MyLocationListener mLocationListener) {
    }

    /*默认不定位，如果需要定位，子类需要重写该方法*/
    public LocationService GetMapService() {
        return null;
    }

    /**
     * 将数据填充
     */
    public void fillData() {
    }

    /**
     * 从网络获取数据，在new的时候只加载一次，后期都需要refresh才能更新
     */
    public void loadData() {
        if (AppConstants.DEBUG)
            Log.i("RHG", "----------------loadData");
    }

    protected abstract void initData();

    protected abstract void initView(View view);

    @Override
    public void showData(Object o) {
        if (o instanceof String) {
            String _str = (String) o;
            if (_str.contains("location")) {
                String[] location_str = _str.split(",");
                if (location_str[1].equals("error"))
                    showLocFailed(location_str[2]);
                else
                    showLocSuccess(location_str[3]);
                if (locationService != null) {
                    locationService.stop();
                }
            } else {
                showSuccess(_str);
            }
            return;
        }
        showSuccess(o);
    }

    public void showLocSuccess(String s) {
    }

    public void showLocFailed(String s) {
    }

    /*@Override
    public void onStop() {
        super.onStop();
        if (locationService != null) {
            locationService.unregisterListener(mLocationListener); //注销掉监听
            locationService.stop(); //停止定位服务
        }
    }*/

    @Override
    public void onPause() {
        super.onPause();
        if (locationService != null) {
            locationService.unregisterListener(mLocationListener); //注销掉监听
            locationService.stop(); //停止定位服务
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isViewPrepare = false;
        hasFetchData = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    protected abstract void showFailed();


    public abstract void showSuccess(Object o);



}
