package com.klgwl.ad.util;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.klgwl.ad.sdk.KlgAd;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：网络回调的封装
 * 创建人员：Robi
 * 创建时间：2018/06/22 16:30
 * 修改人员：Robi
 * 修改时间：2018/06/22 16:30
 * 修改备注：
 * Version: 1.0.0
 */
public class RHttp {

    private static final int HTTP_MESSAGE = 100;

    ConcurrentHashMap<String, OnHttpResult> listener = new ConcurrentHashMap<>();
    RWorkThread mWorkThread = new RWorkThread();

    private Handler mHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (message.what == HTTP_MESSAGE) {
                HttpMessage httpMessage = (HttpMessage) message.obj;
                httpMessage.httpResult.onResult(httpMessage.result);
                return true;
            }
            return false;
        }
    });

    private RHttp() {
        mWorkThread.start();
    }

    public static RHttp instance() {
        return Holder.instance;
    }

    public static void get(final String urlStr, final OnHttpResult httpResult) {
        instance().mWorkThread.addTask(new TaskRunnable() {
            @Override
            public void run() {
                HttpMessage<String> httpMessage = new HttpMessage<>();
                RResult<String> stringRResult = RHttpClient.get(urlStr);

                httpMessage.httpResult = httpResult;
                httpMessage.result = stringRResult;
                httpMessage.url = urlStr;

                instance().mHandler.obtainMessage(HTTP_MESSAGE, httpMessage).sendToTarget();
            }
        });
    }

    public static void post(final String urlStr, final String body, final OnHttpResult httpResult) {
        instance().mWorkThread.addTask(new TaskRunnable() {
            @Override
            public void run() {

                KlgFileUtils.saveToSDCard("请求:" + urlStr);
                KlgFileUtils.saveToSDCard("body:" + body);
                HttpMessage<String> httpMessage = new HttpMessage<>();
                RResult<String> stringRResult = RHttpClient.post(urlStr, body);
                KlgFileUtils.saveToSDCard("返回:" + stringRResult);

                httpMessage.httpResult = httpResult;
                httpMessage.result = stringRResult;
                httpMessage.url = urlStr;

                instance().mHandler.obtainMessage(HTTP_MESSAGE, httpMessage).sendToTarget();
            }
        });
    }

    public static String getDownFilePath(final String urlStr) {
        return KlgAd.getAppInternalDir("klg_down") + "/" + MD5.getStringMD5(urlStr);
    }

    public static void down(final String urlStr, final OnHttpResult httpResult) {
        instance().mWorkThread.addTask(new TaskRunnable() {
            @Override
            public void run() {

                KlgFileUtils.saveToSDCard("下载:" + urlStr);
                String filePath = getDownFilePath(urlStr);
                KlgFileUtils.saveToSDCard("to:" + filePath);
                HttpMessage<String> httpMessage = new HttpMessage<>();
                RResult<String> stringRResult = RHttpClient.down(urlStr, filePath);
                KlgFileUtils.saveToSDCard("返回:" + stringRResult);

                httpMessage.httpResult = httpResult;
                httpMessage.result = stringRResult;
                httpMessage.url = urlStr;

                instance().mHandler.obtainMessage(HTTP_MESSAGE, httpMessage).sendToTarget();
            }
        });
    }

    public interface OnHttpResult {
        void onResult(RResult<String> result);
    }

    private static class Holder {
        static RHttp instance = new RHttp();
    }

    private static class HttpMessage<T> {
        String url;
        OnHttpResult httpResult;
        RResult<T> result;
    }
}
