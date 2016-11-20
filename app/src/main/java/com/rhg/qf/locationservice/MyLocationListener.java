package com.rhg.qf.locationservice;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.rhg.qf.mvp.model.LocModel;
import com.rhg.qf.mvp.view.BaseView;
import com.rhg.qf.utils.AccountUtil;

/**
 * desc:定位结果回调，重写onReceiveLocation方法
 * author：remember
 * time：2016/5/28 16:52
 * email：1013773046@qq.com
 */
public class MyLocationListener implements BDLocationListener {
    BaseView baseView;
    LocModel locModel;

    public MyLocationListener(BaseView baseView) {
        this.baseView = baseView;
        locModel = new LocModel();
    }

    public void getLocation(LocationService locationService) {
        locModel.getLocation(locationService);
    }

    @Override
    public void onReceiveLocation(BDLocation location) {
        if (null != location && location.getLocType() != BDLocation.TypeServerError) {
            String sb = "location,";
            /*sb.append("time : ");
            *//**
             * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
             * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
             *//*
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            sb.append("\nCountryCode : ");
            sb.append(location.getCountryCode());
            sb.append("\nCountry : ");
            sb.append(location.getCountry());
            sb.append("\ncitycode : ");
            sb.append(location.getCityCode());
            sb.append("\ncity : ");*/
            /*sb.append(location.getStreet());
            sb.append("\naddr : ");
            sb.append(location.getAddrStr());
            sb.append("\nDescribe: ");
            sb.append(location.getLocationDescribe());
            sb.append("\nDirection(not all devices have value): ");
            sb.append(location.getDirection());
            sb.append("\nPoi: ");*/
            /*if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
                for (int i = 0; i < location.getPoiList().size(); i++) {
                    Poi poi = (Poi) location.getPoiList().get(i);
                    sb.append(poi.getName() + ";");
                }
            }*/
            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb += location.getProvince() + ",";
                sb += location.getCity() + ",";
                sb += location.getDistrict() + ",";
                sb += location.getLatitude() + ",";
                sb += location.getLongitude();
                AccountUtil.getInstance().setLatitude(String.valueOf(location.getLatitude()));
                AccountUtil.getInstance().setLongitude(String.valueOf(location.getLongitude()));
                /*sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：km/h
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");*/
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                sb += location.getProvince() + ",";
                sb += location.getCity() + ",";
                sb += location.getDistrict() + ",";
                AccountUtil.getInstance().setLatitude(String.valueOf(location.getLatitude()));
                AccountUtil.getInstance().setLongitude(String.valueOf(location.getLongitude()));
                // 运营商信息
                /*sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");*/
            } /*else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } *//*else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            }*/ else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb += "error,";
                sb += "请检查网络是否通畅";
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb += "error,";
                sb += "无法获取有效定位";
            } else {
                sb += location.getProvince() + ",";
                sb += location.getCity() + ",";
                sb += location.getDistrict() + ",";
                sb += location.getLatitude() + ",";
                sb += location.getLongitude();
                AccountUtil.getInstance().setLatitude(String.valueOf(location.getLatitude()));
                AccountUtil.getInstance().setLongitude(String.valueOf(location.getLongitude()));
            }
            if (baseView != null)
                baseView.showData(sb);
        }
    }
}
