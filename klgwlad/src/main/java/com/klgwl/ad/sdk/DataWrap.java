package com.klgwl.ad.sdk;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/06/25 11:38
 * 修改人员：Robi
 * 修改时间：2018/06/25 11:38
 * 修改备注：
 * Version: 1.0.0
 */
public class DataWrap {

    /**
     * code : 200
     * data : {"impression_company":"miaozhen","creative_id":25,"title":"星期二四五的创意","desc":"这个创意用来在星期二、星期四、星期五投放","weight":0,"interact_type":1,"format":"","range":null,"openUrl":"www.baidu.com","androidUrl":"","iosUrl":"","monitor_urls":["http://service.klgwl.com/adv/feedback?a=1858505698&appid=1858505698&b=1&c=7&d=25&e=2&f=3&h=0&i=0&o=other&random_str=07eceb1a385080dcbe1778a05cbd5670&sign=51623f857bbf6ac1c1b8033385032509&t=m&time_stamp=1529896113"],"click_url":["www.baidu.com","http://service.klgwl.com/adv/feedback?a=1858505698&appid=1858505698&b=1&c=7&d=25&e=2&f=3&h=0&i=0&o=other&random_str=07eceb1a385080dcbe1778a05cbd5670&sign=ab4a781ecd034f27647408356c71cb9d&t=c&time_stamp=1529896113"],"info":[{"url":"http://avatorimg.klgwl.com/13/13595_s_1181x615.png","url_md5":"0cd2cd9919b5f428861c59f11b1a1210","type":1,"length":0}],"ad_h":1334,"ad_w":750}
     */

    private int code;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }
}
