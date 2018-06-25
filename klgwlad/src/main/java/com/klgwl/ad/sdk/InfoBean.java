package com.klgwl.ad.sdk;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.klgwl.ad.util.DeviceUtils;
import com.klgwl.ad.util.NetworkUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/06/22 17:34
 * 修改人员：Robi
 * 修改时间：2018/06/22 17:34
 * 修改备注：
 * Version: 1.0.0
 */
public class InfoBean {

    /**
     * lng : 113.953856
     * lat : 22.542617
     * imei : 641221321098757
     * idfa : 65A509BA-227C-49AC-91EC-DE6817E63B10
     * anid : c3f3b129ba3eea1f
     * mac : B4B52FBD1780
     * udid : 419as5wwddqqwe125wssa21fvfeewghtgrd12qwe
     * os : android 7.1.0
     * brand : samsung
     * model : GRAXY S7
     * sw : 1440
     * sh : 2560
     * connectiontype : 1
     * carrier : 0
     * version : 2.1.9
     * screen_orientation : 1
     * device_id : aumwnlksjndx
     */

    private String lng = "";
    private String lat = "";
    private String imei = "";
    private String idfa = "";
    private String anid = "";
    private String mac = "";
    private String udid = "";
    private String os = "";
    private String brand = "";
    private String model = "";
    private int sw;
    private int sh;
    private int connectiontype;
    private int carrier;
    private String version = "";
    private int screen_orientation;
    private String device_id = "";

    public InfoBean() {
        try {
            imei = KlgAd.getIMEI();
            device_id = KlgAd.initImei();
            anid = DeviceUtils.getAndroidID();
            mac = DeviceUtils.getMacAddress();
            os = "Android " + Build.VERSION.RELEASE + " " + Build.VERSION.SDK_INT;
            brand = Build.BRAND;
            model = Build.MODEL;
            sw = KlgAd.mApplication.getResources().getDisplayMetrics().widthPixels;
            sh = KlgAd.mApplication.getResources().getDisplayMetrics().heightPixels;

            // 运营商，0-未知，1-中国移动，2-中国联通，3-中国电信，4-互联网电视
            TelephonyManager tm = (TelephonyManager) KlgAd.mApplication.getSystemService(Context.TELEPHONY_SERVICE);
            String simOperator = tm.getSimOperator();
            if (TextUtils.isEmpty(simOperator)) {
                connectiontype = 0;
            } else if (simOperator.startsWith("46001")) {
                connectiontype = 2;
            } else if (simOperator.startsWith("46011")) {
                connectiontype = 3;
            } else if (simOperator.startsWith("46000")) {
                connectiontype = 1;
            } else {
                connectiontype = Integer.valueOf(simOperator);
            }

            // 网络环境，0-wifi，1-mobile，2-no network
            int netType = NetworkUtil.getNetType(KlgAd.mApplication);
            if (netType == -1) {
                carrier = 2;
            } else if (netType == ConnectivityManager.TYPE_MOBILE) {
                carrier = 1;
            } else {
                carrier = 0;
            }

            version = getAppName();

            // 横竖屏状态，0-未知，1-竖屏，2-横屏
            if (KlgAd.mApplication.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                screen_orientation = 1;
            } else {
                screen_orientation = 2;
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    /**
     * 获取APP的名字
     */
    static String getAppName() {
        String appName = KlgAd.mApplication.getPackageName();
        PackageManager packageManager = KlgAd.mApplication.getPackageManager();
        PackageInfo packInfo;
        try {
            packInfo = packageManager.getPackageInfo(appName, 0);
            appName = packInfo.applicationInfo.loadLabel(KlgAd.mApplication.getPackageManager()).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appName;
    }

    public String toJson() {
//        StringBuilder builder = new StringBuilder();
//        builder.append("{");
//
//        Field[] fields = getClass().getDeclaredFields();
//        for (int i = 0; i < fields.length; i++) {
//            Field field = fields[i];
//            try {
//                json(builder, field.getName(), field.get(this));
//                if (i != fields.length - 1) {
//                    builder.append(",");
//                }
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//        }
//        builder.append("}");
//
//        return builder.toString();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("lng", lng);
            jsonObject.put("lat", lat);
            jsonObject.put("imei", imei);
            jsonObject.put("idfa", idfa);
            jsonObject.put("anid", anid);
            jsonObject.put("mac", mac);
            jsonObject.put("udid", udid);
            jsonObject.put("os", os);
            jsonObject.put("brand", brand);
            jsonObject.put("model", model);
            jsonObject.put("sw", sw);
            jsonObject.put("sh", sh);
            jsonObject.put("connectiontype", connectiontype);
            jsonObject.put("carrier", carrier);
            jsonObject.put("version", version);
            jsonObject.put("screen_orientation", screen_orientation);
            jsonObject.put("device_id", device_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String string = jsonObject.toString();

        return string;
    }

    private void json(StringBuilder builder, String name, Object object) {
        builder.append("\"");
        builder.append(name);
        builder.append("\"");
        builder.append(":");
        if (object instanceof String) {
            builder.append("\"");
        }
        if (object == null) {
            builder.append("");
        } else {
            builder.append(object);
        }
        if (object instanceof String) {
            builder.append("\"");
        }
    }

    @Override
    public String toString() {
        return toJson();
    }
}
