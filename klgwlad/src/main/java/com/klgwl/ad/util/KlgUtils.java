package com.klgwl.ad.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;

import com.klgwl.ad.sdk.KlgAd;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/06/25 08:53
 * 修改人员：Robi
 * 修改时间：2018/06/25 08:53
 * 修改备注：
 * Version: 1.0.0
 */
public class KlgUtils {
    public static void saveToSDCard(final String data) {
        try {
            String dataTime = getDataTime("yyyy-MM-dd_HH-mm-ss-SSS");
            PrintWriter pw = createPrintWrite();
            pw.println(dataTime);
            pw.println(data);
            //换行
            pw.println();
            pw.close();
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    public static void saveToSDCard(Exception exception) {
        try {
            PrintWriter pw = createPrintWrite();
            exception.printStackTrace(pw);
            pw.println();
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static PrintWriter createPrintWrite() {
        try {
            String saveFolder = Environment.getExternalStorageDirectory().getAbsoluteFile() +
                    File.separator + KlgAd.mApplication.getPackageName() + File.separator + "klgwl";
            File folder = new File(saveFolder);
            if (!folder.exists()) {
                if (!folder.mkdirs()) {
                    return null;
                }
            }
            File file = new File(saveFolder, "ad.log");
            boolean append = true;
            if (file.length() > 1024 * 1024 * 1 /*大于10MB重写*/) {
                append = false;
            }
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file, append)));
            return pw;
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return null;
    }

    static String getDataTime(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(new Date());
    }

    public static boolean isListEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }

    /**
     * 打开网页,调用系统应用
     *
     * @param context the context
     * @param url     the url
     */
    public static void openUrl(Context context, String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }

        if (!url.toLowerCase().startsWith("http:") && !url.toLowerCase().startsWith("https:")) {
            url = "http:".concat(url);
        }

        Uri webPage = Uri.parse(url);
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webPage);
        webIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(webIntent);
    }
}
