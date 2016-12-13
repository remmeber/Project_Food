package com.rhg.qf.ui.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.rhg.qf.R;
import com.rhg.qf.locationservice.LocationService;
import com.rhg.qf.locationservice.MyLocationListener;
import com.rhg.qf.mvp.base.IView;
import com.rhg.qf.mvp.base.RxPresenter;
import com.rhg.qf.mvp.view.BaseView;
import com.rhg.qf.runtimepermissions.PermissionsManager;
import com.rhg.qf.utils.ImageUtils;
import com.rhg.qf.utils.KeyBoardUtil;

import butterknife.ButterKnife;

/*
 *desc
 *author rhg
 *time 2016/7/7 10:36
 *email 1013773046@qq.com
 */
public abstract class BaseAppcompactActivity<T extends RxPresenter<? extends IView>> extends AppCompatActivity implements BaseView {

    protected T presenter;

    boolean isLocating;
    //TODO 百度地图
    private LocationService locationService;
    private MyLocationListener mLocationListener;
    protected View decorView;
    Toolbar toolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Explode explode = new Explode();
            explode.setDuration(1000);
            getWindow().setExitTransition(explode);
        }
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
        loadingData();
        initData();
    }

    /*创建toolbar中的menu调用的方法，只调用一次*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menuCreated(menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
        this.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beforeFinish();
                finish();
            }
        });
    }

    protected void beforeFinish() {
    }

    /*toolbar 中menu创建的回调方法*/
    public void menuCreated(Menu menu) {

    }

    /*toolbar中点击menu回调该方法*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_click) {
            rightClick();
        }

        return super.onOptionsItemSelected(item);
    }

    /*menu的点击事件回调方法*/
    public void rightClick() {

    }

    protected void checkPermissionAndSetIfNecessary(String[] permissions) {
        if (!PermissionsManager.getInstance().hasAllPermissions(this, permissions)) {
            PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(this,
                    permissions, null);
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

    /*授权成功回调*/
    public void onGrant() {

    }

    /*授权失败回调*/
    public void onDeny(String permission) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        hideNavigationBar(decorView);
    }

    public void hideNavigationBar(View decorView) {

    }

    protected abstract void initData();

    protected abstract int getLayoutResId();

    public void loadingData() {

    }

    protected void startLoc() {
        if (!isLocating) {
            isLocating = true;
            if ((locationService = GetMapService()) != null) {
                if ((mLocationListener = getLocationListener()) != null) {
                    locationService.registerListener(mLocationListener);
                    locationService.setLocationOption(locationService.getDefaultLocationClientOption());
                    mLocationListener.getLocation(locationService);
                }
            }
        }
    }

    public MyLocationListener getLocationListener() {
        return null;
    }


    /*默认不定位，如果需要定位，子类需要重写该方法*/
    public LocationService GetMapService() {
        return null;
    }

    public void dataReceive(Intent intent) {
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
            ((EditText) this.getCurrentFocus()).setCursorVisible(false);
            return KeyBoardUtil.closeKeybord((EditText) this.getCurrentFocus(), this);
        }
        return super.onTouchEvent(event);
    }


    @Override
    protected void onDestroy() {
        ImageUtils.clearCache();
        super.onDestroy();
        ButterKnife.bind(this);
        if (locationService != null) {
            locationService.stop();
            locationService.unregisterListener(mLocationListener);
        }

    }


    @Override
    public void onBackPressed() {
        finish();
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
                isLocating = false;
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

    public void showSuccess(Object s) {
    }

    public void showError(Object s) {
    }

}
