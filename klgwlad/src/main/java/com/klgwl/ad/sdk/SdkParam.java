package com.klgwl.ad.sdk;

import android.text.TextUtils;
import android.util.Log;

import com.ads.core.AdsCore;
import com.klgwl.ad.util.KlgUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/06/22 17:28
 * 修改人员：Robi
 * 修改时间：2018/06/22 17:28
 * 修改备注：
 * Version: 1.0.0
 */
public class SdkParam {
    public int appid;
    public int adid = 1;
    public String sdk_version;
    public String r_info;
    public int time_stamp;
    public String random_str;
    public String sign;

    public SdkParam() {
        appid = KlgAd.appid;
        sdk_version = KlgAd.SDK_VERSION;
        time_stamp = (int) (System.currentTimeMillis() / 1000L);
        random_str = UUID.randomUUID().toString();
        r_info = new InfoBean().toJson();

        KlgUtils.saveToSDCard("r_info:" + r_info);
    }

    public SdkParam setAdid(int adid) {
        this.adid = adid;
        return this;
    }

    public String body() {
        if (TextUtils.isEmpty(KlgAd.appkey)) {
            //throw new IllegalStateException("请先初始化KlgAd.init()");
            Log.e("klg_ad", "请先初始化KlgAd.init()");
            return "";
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("appid", appid);
            jsonObject.put("adid", adid);
            jsonObject.put("sdk_version", sdk_version);
            jsonObject.put("r_info", r_info);
            jsonObject.put("time_stamp", time_stamp);
            jsonObject.put("random_str", random_str);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String string = jsonObject.toString();
        KlgUtils.saveToSDCard("开始签名:" + string);
        String sign = AdsCore.Sign(appid + "", KlgAd.appkey, string);
        KlgUtils.saveToSDCard("结束签名:" + sign);

        return sign;
    }
}
