package com.klgwl.ad.sdk;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.klgwl.ad.util.L;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/06/22 17:30
 * 修改人员：Robi
 * 修改时间：2018/06/22 17:30
 * 修改备注：
 * Version: 1.0.0
 */
public class KlgAd {
    static final String TAG = "klg_ad";
    static final String SDK_VERSION = "1.0";
    public static Application mApplication;
    static int appid;
    static String appkey;

    static {
        System.loadLibrary("ads");
    }

    public static void init(Application context, int appid, String appkey, boolean debug) {
        mApplication = context;
        KlgAd.appid = appid;
        KlgAd.appkey = appkey;
        initImei();
        L.init(debug, TAG);
    }


    public static String getAppInternalDir(String folder) {
        if (folder == null) {
            folder = "";
        }
        File file = mApplication.getDir(folder, Context.MODE_PRIVATE);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    @SuppressLint("MissingPermission")
    static String getIMEI() {
        String imei = "";
        try {
            TelephonyManager telephonyManager = ((TelephonyManager) mApplication
                    .getSystemService(Context.TELEPHONY_SERVICE));

            if (telephonyManager != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    imei = telephonyManager.getImei();
                } else {
                    imei = telephonyManager.getDeviceId();
                }
            }
            //L.w("call: getIMEI([])-> " + imei);
        } catch (Exception e) {
            L.e("IMEI获取失败, 请检查权限:" + e.getMessage());
            //e.printStackTrace();
            //L.e("call: getIMEI([])-> " + imei + " " + e.getMessage());
        }
        return imei;
    }

    /**
     * 伪造一个自己的Imei
     */
    static String initImei() {
        String imeiPath = getAppInternalDir("klg_ad_card");
        File imeiFile = new File(imeiPath);

        String uuid = UUID.randomUUID().toString();
        if (imeiFile.isDirectory()) {
            if (imeiFile.list().length > 0) {
                uuid = imeiFile.listFiles()[0].getName();
            } else {
                try {
                    new File(imeiPath + File.separator + uuid).createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return uuid;
    }
}
