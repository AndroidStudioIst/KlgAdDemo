package com.klgwl.ad.util;

import android.os.Environment;

import com.klgwl.ad.sdk.KlgAd;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

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
public class FileUtils {
    public static void saveToSDCard(final String data) {
        try {
            String saveFolder = Environment.getExternalStorageDirectory().getAbsoluteFile() +
                    File.separator + KlgAd.mApplication.getPackageName() + File.separator + "klgwl";
            File folder = new File(saveFolder);
            if (!folder.exists()) {
                if (!folder.mkdirs()) {
                    return;
                }
            }
            String dataTime = getDataTime("yyyy-MM-dd_HH-mm-ss-SSS");
            File file = new File(saveFolder, "ad.log");
            boolean append = true;
            if (file.length() > 1024 * 1024 * 1 /*大于10MB重写*/) {
                append = false;
            }
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file, append)));
            pw.println(dataTime);
            pw.println(data);
            //换行
            pw.println();
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static String getDataTime(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(new Date());
    }
}
