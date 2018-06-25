package com.klgwl.ad.sdk;

import java.util.List;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/06/25 10:30
 * 修改人员：Robi
 * 修改时间：2018/06/25 10:30
 * 修改备注：
 * Version: 1.0.0
 */
public class DataBean {

    /**
     * impression_company : miaozhen
     * creative_id : 25
     * title : 星期二四五的创意
     * desc : 这个创意用来在星期二、星期四、星期五投放
     * weight : 0
     * interact_type : 1
     * format :
     * range : null
     * openUrl : www.baidu.com
     * androidUrl :
     * iosUrl :
     * monitor_urls : ["http://service.klgwl.com/adv/feedback?a=1858505698&appid=1858505698&b=1&c=7&d=25&e=2&f=3&h=0&i=0&o=other&random_str=82b379d70a4b166dced60f9ee55ae6b5&sign=f75332b18b12eed922804c3cae916794&t=m&time_stamp=1529893620"]
     * click_url : ["www.baidu.com","http://service.klgwl.com/adv/feedback?a=1858505698&appid=1858505698&b=1&c=7&d=25&e=2&f=3&h=0&i=0&o=other&random_str=82b379d70a4b166dced60f9ee55ae6b5&sign=7654a242c8e7dd87a9a426a77fcffc64&t=c&time_stamp=1529893620"]
     * info : [{"url":"http://avatorimg.klgwl.com/13/13595_s_1181x615.png","url_md5":"0cd2cd9919b5f428861c59f11b1a1210","type":1,"length":0}]
     * ad_h : 1334
     * ad_w : 750
     */

    private String impression_company;
    private int creative_id;
    private String title;
    private String desc;
    private int weight;
    private int interact_type;
    private String format;
    private Object range;
    private String openUrl;
    private String androidUrl;
    private String iosUrl;
    private int ad_h;
    private int ad_w;
    private List<String> monitor_urls;
    private List<String> click_url;
    private List<InfoBean> info;

    public String getImpression_company() {
        return impression_company;
    }

    public void setImpression_company(String impression_company) {
        this.impression_company = impression_company;
    }

    public int getCreative_id() {
        return creative_id;
    }

    public void setCreative_id(int creative_id) {
        this.creative_id = creative_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getInteract_type() {
        return interact_type;
    }

    public void setInteract_type(int interact_type) {
        this.interact_type = interact_type;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Object getRange() {
        return range;
    }

    public void setRange(Object range) {
        this.range = range;
    }

    public String getOpenUrl() {
        return openUrl;
    }

    public void setOpenUrl(String openUrl) {
        this.openUrl = openUrl;
    }

    public String getAndroidUrl() {
        return androidUrl;
    }

    public void setAndroidUrl(String androidUrl) {
        this.androidUrl = androidUrl;
    }

    public String getIosUrl() {
        return iosUrl;
    }

    public void setIosUrl(String iosUrl) {
        this.iosUrl = iosUrl;
    }

    public int getAd_h() {
        return ad_h;
    }

    public void setAd_h(int ad_h) {
        this.ad_h = ad_h;
    }

    public int getAd_w() {
        return ad_w;
    }

    public void setAd_w(int ad_w) {
        this.ad_w = ad_w;
    }

    public List<String> getMonitor_urls() {
        return monitor_urls;
    }

    public void setMonitor_urls(List<String> monitor_urls) {
        this.monitor_urls = monitor_urls;
    }

    public List<String> getClick_url() {
        return click_url;
    }

    public void setClick_url(List<String> click_url) {
        this.click_url = click_url;
    }

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    public static class ClassTest{
        public String tste;
    }

    public static class InfoBean {
        /**
         * url : http://avatorimg.klgwl.com/13/13595_s_1181x615.png
         * url_md5 : 0cd2cd9919b5f428861c59f11b1a1210
         * type : 1
         * length : 0
         */

        private String url;
        private String url_md5;
        private int type;
        private int length;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrl_md5() {
            return url_md5;
        }

        public void setUrl_md5(String url_md5) {
            this.url_md5 = url_md5;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }
    }
}
