package com.rhg.qf.ui.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;

import com.rhg.qf.application.InitApplication;
import com.rhg.qf.constants.AppConstants;
import com.rhg.qf.locationservice.LocationService;
import com.rhg.qf.locationservice.MyLocationListener;
import com.rhg.qf.mvp.view.BaseView;
import com.rhg.qf.utils.ImageUtils;
import com.rhg.qf.utils.KeyBoardUtil;

import butterknife.ButterKnife;

/**
 * desc:工程的基类，所有的子Activity都要继承它
 * author：remember
 * time：2016/5/28 16:13
 * email：1013773046@qq.com
 */
public abstract class BaseFragmentActivity extends FragmentActivity implements BaseView/*, View.OnClickListener*/ {
    boolean isFirstLoc;
    //TODO 百度地图
    private LocationService locationService;
    private MyLocationListener mLocationListener;
    View decorView = null;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        android.transition.Fade fade = new android.transition.Fade();
        fade.setDuration(500);
        getWindow().setEnterTransition(fade);

        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                // Note that system bars will only be "visible" if none of the
                // LOW_PROFILE, HIDE_NAVIGATION, or FULLSCREEN flags are set.
                if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                    // The system bars are visible. Make any desired
                    // adjustments to your UI, such as showing the action bar or
                    // other navigational controls.
                    hideNavigationBar(decorView);
                } else {
                    // The system bars are NOT visible. Make any desired
                    // adjustments to your UI, such as hiding the action bar or
                    // other navigational controls.
                }
            }
        });

        ButterKnife.bind(this);
        dataReceive(getIntent());
        initView(getRootView(this));
        initData(savedInstanceState);
    }


    public void hideNavigationBar(View decorView) {

    }

    /*获取根布局*/
    View getRootView(Activity context) {
        return ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);
    }


    private void startLoc() {
        if ((locationService = GetMapService()) != null) {
            if ((mLocationListener = getLocationListener()) != null) {
                locationService.registerListener(mLocationListener);
                locationService.setLocationOption(locationService.getDefaultLocationClientOption());
                mLocationListener.getLocation(locationService);
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        hideNavigationBar(decorView);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View focusView = getCurrentFocus();
            if (isShouldHideKeyBoard(focusView, ev)) {
                KeyBoardUtil.closeKeybord((EditText) focusView, this);
                focusView.clearFocus();
                keyBoardHide();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public void keyBoardHide() {
    }

    private boolean isShouldHideKeyBoard(View focusView, MotionEvent ev) {
        if (focusView != null && focusView instanceof EditText) {
            int[] p = {0, 0};
            focusView.getLocationInWindow(p);
            int left = p[0];
            int top = p[1];
            int bottom = top + focusView.getHeight();
            int right = left + focusView.getWidth();
            return ev.getX() < left || ev.getX() > right || ev.getY() < top || ev.getY() > bottom;
        } else return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus() && this.getCurrentFocus() instanceof EditText) {

            return KeyBoardUtil.closeKeybord((EditText) this.getCurrentFocus(), this);
        }
        return super.onTouchEvent(event);
    }

    public void reStartLocation() {
        if (isFirstLoc) {
            startLoc();
            isFirstLoc = false;
            return;
        }
        if (locationService == null)
            locationService = GetMapService();
        if (mLocationListener == null) {
            Log.d("RHG", "Location listener is null");
            mLocationListener = getLocationListener();
            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        }
        locationService.registerListener(mLocationListener);
        mLocationListener.getLocation(locationService);
        if (AppConstants.DEBUG)
            Log.i("RHG", "重启定位");
    }

    public MyLocationListener getLocationListener() {
        return new MyLocationListener(this);
    }

    /*public void getLocation(LocationService locationService, MyLocationListener mLocationListener) {
    }*/


    /*默认不定位，如果需要定位，子类需要重写该方法*/
    public LocationService GetMapService() {
        return InitApplication.getInstance().locationService;
    }

    public void dataReceive(Intent intent) {
    }

    @Override
    protected void onStop() {
        if (locationService != null) {
            locationService.unregisterListener(mLocationListener); //注销掉监听
            locationService.stop(); //停止定位服务
        }
        super.onStop();
    }

    //onPause中不做复杂操作
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        ImageUtils.clearCache();
        super.onDestroy();
        InitApplication.getInstance().removeActivity(this);
        ButterKnife.bind(this);

    }

    protected int getLayoutResId() {
        return 0;
    }

    protected abstract void initView(View view);

    protected abstract void initData(Bundle savedInstanceState);


    /*当Activity启动模式(LaunchMode)为SingleTop或者SingleTask的时候，多次调用此Activity可能会调用此方法，而不
    * 调用Activity的onCreate、onStart方法
    */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    public void onBackPressed() {
        if (InitApplication.getInstance().getAcitivityCount() == 1) {
            InitApplication.getInstance().exit();
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            finishAfterTransition();
        else finish();
    }

    @Override
    public void showData(Object o) {
        if (o instanceof String) {
            String _str = (String) o;
            if (_str.contains("location")) {
                String[] location_str = _str.split(",");
                if (location_str[1].equals("error"))
                    showLocFailed(location_str[2]);
                else {
                    if (locationService != null) {
                        locationService.stop();
                    }
                    showLocSuccess(location_str[1].concat(",").concat(location_str[2])
                            .concat(",").concat(location_str[3]));
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

    protected abstract void showSuccess(Object s);

    protected abstract void showError(Object s);


}
