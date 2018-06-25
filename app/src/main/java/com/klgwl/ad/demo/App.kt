package com.klgwl.ad.demo

import android.app.Application
import com.klgwl.ad.sdk.KlgAd

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/06/25 15:05
 * 修改人员：Robi
 * 修改时间：2018/06/25 15:05
 * 修改备注：
 * Version: 1.0.0
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        KlgAd.init(this, 1858505698, "7f27dfbe2a1d3891d6e08ceed2e61806", BuildConfig.DEBUG)
    }
}