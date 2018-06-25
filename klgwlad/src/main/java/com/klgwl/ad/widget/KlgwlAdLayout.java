package com.klgwl.ad.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.klgwl.ad.R;
import com.klgwl.ad.sdk.DataBean;
import com.klgwl.ad.sdk.DataWrap;
import com.klgwl.ad.sdk.SdkParam;
import com.klgwl.ad.util.Json;
import com.klgwl.ad.util.KlgUtils;
import com.klgwl.ad.util.L;
import com.klgwl.ad.util.RHttp;
import com.klgwl.ad.util.RResult;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifDrawableBuilder;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/06/21 19:11
 * 修改人员：Robi
 * 修改时间：2018/06/21 19:11
 * 修改备注：
 * Version: 1.0.0
 */
public class KlgwlAdLayout extends FrameLayout implements View.OnLayoutChangeListener, View.OnClickListener {

    private int klgAdId = -1;
    private DataBean adDataBean;
    private RHttp.OnHttpResult mOnHttpResult;
    private boolean isOnAttachedToWindow = false;

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Rect tempRect = new Rect();

    private OnAdListener mOnAdListener;

    public KlgwlAdLayout(Context context) {
        this(context, null);
    }

    public KlgwlAdLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.KlgwlAdLayout);
        klgAdId = typedArray.getInt(R.styleable.KlgwlAdLayout_klg_ad_id, klgAdId);
        typedArray.recycle();
        fetchAd();

        setWillNotDraw(false);
        mPaint.setTextSize(12 * getResources().getDisplayMetrics().density);
        mPaint.setColor(Color.GRAY);
        mPaint.setFilterBitmap(true);

        setOnClickListener(this);
    }

    /**
     * 设置广告位的id
     */
    public void setKlgAdId(int klgAdId) {
        int oldId = this.klgAdId;
        this.klgAdId = klgAdId;

        if (oldId != klgAdId) {
            refreshAd();
        }
    }

    public void refreshAd() {
        mOnHttpResult = null;
        fetchAd();
    }

    /**
     * 获取相应广告位的广告信息
     */
    private void fetchAd() {
        if (klgAdId > 0) {
            if (mOnHttpResult == null) {
                mOnHttpResult = new RHttp.OnHttpResult() {
                    @Override
                    public void onResult(RResult<String> result) {
                        if (result.isSuccess()) {
                            try {
                                adDataBean = Json.from(result.obj, DataWrap.class).getData();
                                startDown();
                            } catch (Exception e) {
                                e.printStackTrace();
                                KlgUtils.saveToSDCard(e);
                            }
                        } else {
                            mOnHttpResult = null;
                            if (isOnAttachedToWindow) {
                                fetchAd();
                            }
                        }
                    }
                };
                RHttp.post("http://service.klgwl.com/adv/adrequest",
                        new SdkParam().setAdid(klgAdId).body(),
                        mOnHttpResult);
            }
        }
    }

    private void initAdLayout() {
        if (mOnAdListener != null &&
                mOnAdListener.onPrepareComplete(adDataBean)) {
            return;
        }

        if (isOnAttachedToWindow) {
            DataBean.InfoBean adInfo = getAdInfo();
            if (adInfo != null) {
                try {
                    String downFilePath = RHttp.getDownFilePath(adInfo.getUrl());

                    //1静态图片 2动态图片 3视频
                    if (adInfo.getType() == 1 || adInfo.getType() == 2) {
                        View view = findViewById(R.id.klg_ad_image_view);
                        if (view == null) {
                            if (getChildCount() > 0) {
                                removeAllViews();
                            }
                            inflate(getContext(), R.layout.klg_ad_image_layout, this);
                        }

                        ImageView imageView = findViewById(R.id.klg_ad_image_view);

                        L.i("加载图片ad:" + downFilePath);
                        if (adInfo.getType() == 2) {
                            GifDrawable gifDrawable = loadGif(downFilePath);
                            imageView.setImageDrawable(gifDrawable);
                        } else {
                            Bitmap bitmap = loadBitmap(downFilePath);
                            imageView.setImageBitmap(bitmap);
                        }

                        imageView.removeOnLayoutChangeListener(this);
                        imageView.addOnLayoutChangeListener(this);
                    } else if (adInfo.getType() == 3) {
                        View view = findViewById(R.id.klg_ad_video_view);
                        if (view == null) {
                            if (getChildCount() > 0) {
                                removeAllViews();
                            }
                            inflate(getContext(), R.layout.klg_ad_video_layout, this);
                        }

                        TextureVideoView videoView = findViewById(R.id.klg_ad_video_view);

                        L.i("加载视频ad:" + downFilePath);
                        videoView.setVideoPath(downFilePath);
                        videoView.start();

                        videoView.removeOnLayoutChangeListener(this);
                        videoView.addOnLayoutChangeListener(this);
                    }
                } catch (Exception e) {
                    //e.printStackTrace();
                    KlgUtils.saveToSDCard(e);
                }
            }
        }
    }

    private GifDrawable loadGif(String path) throws IOException {
        return new GifDrawableBuilder().from(new File(path)).build();
    }

    private Bitmap loadBitmap(String path) {
        return BitmapFactory.decodeFile(path);
    }

    /**
     * 下载所有资源
     */
    private void startDown() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    CountDownLatch countDownLatch = new CountDownLatch(adDataBean.getInfo().size());
                    for (DataBean.InfoBean adInfo : adDataBean.getInfo()) {
                        downAd(adInfo.getUrl(), countDownLatch);
                    }
                    countDownLatch.await();

                    //所有下载完成后
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            initAdLayout();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    KlgUtils.saveToSDCard(e);
                }
            }
        }).start();
    }

    /**
     * 下载具体的资源
     */
    private void downAd(final String url, final CountDownLatch countDownLatch) {
        RHttp.down(url, true, new RHttp.OnHttpResult() {
            @Override
            public void onResult(RResult<String> result) {
                if (result.isSuccess()) {
                    countDownLatch.countDown();
                } else {
                    postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            downAd(url, countDownLatch);
                        }
                    }, 300);
                }
            }
        });
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        DataBean.InfoBean adInfo = getAdInfo();
        if (adInfo != null) {
            int type = adInfo.getType();
            StringBuilder builder = new StringBuilder("广告:");
            if (type == 1) {
                builder.append("png");
            } else if (type == 2) {
                builder.append("gif");
            } else if (type == 3) {
                builder.append("video");
            } else {
                builder.append("unknown");
            }

            if (adDataBean != null) {
                builder.append(" ");
                builder.append(adDataBean.getInteract_type());
            }

            String text = builder.toString();
            float textWidth = mPaint.measureText(text);
            float textHeight = mPaint.descent() - mPaint.ascent();
            canvas.drawText(text, getMeasuredWidth() - textWidth - textHeight / 5, textHeight, mPaint);
        }
    }

    DataBean.InfoBean getAdInfo() {
        if (adDataBean == null) {
            return null;
        }
        if (KlgUtils.isListEmpty(adDataBean.getInfo())) {
            return null;
        }
        return adDataBean.getInfo().get(0);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        isOnAttachedToWindow = true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        isOnAttachedToWindow = false;
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        if (child instanceof AdImageView || child instanceof AdVideoView) {

        } else {
            throw new IllegalStateException("KlgwlAdLayout中, 请不要嵌套其他View");
        }
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        v.getGlobalVisibleRect(tempRect);
        //L.i(v + " " + tempRect);
        monitor_urls();
    }

    public void setOnAdListener(OnAdListener onAdListener) {
        mOnAdListener = onAdListener;
    }

    //上报展示
    private void monitor_urls() {
        if (adDataBean != null && !KlgUtils.isListEmpty(adDataBean.getMonitor_urls())) {
            for (String url : adDataBean.getMonitor_urls()) {
                RHttp.get(url, new RHttp.OnHttpResult() {
                    @Override
                    public void onResult(RResult<String> result) {

                    }
                });
            }
        }
    }

    //上报点击
    private void click_url() {
        if (adDataBean != null && !KlgUtils.isListEmpty(adDataBean.getClick_url())) {
            for (String url : adDataBean.getClick_url()) {
                RHttp.get(url, new RHttp.OnHttpResult() {
                    @Override
                    public void onResult(RResult<String> result) {

                    }
                });
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (adDataBean != null) {
            //交互类型 0无动作、1位正常的网页跳转，2为app应用下载
            int type = adDataBean.getInteract_type();
            if (type == 1) {
                click_url();

                if (mOnAdListener != null && mOnAdListener.onClickAd(adDataBean, adDataBean.getOpenUrl())) {
                } else {
                    KlgUtils.openUrl(getContext(), adDataBean.getOpenUrl());
                }

            } else if (type == 2) {
                click_url();

                if (mOnAdListener != null && mOnAdListener.onClickAd(adDataBean, adDataBean.getAndroidUrl())) {
                } else {
                    KlgUtils.openUrl(getContext(), adDataBean.getAndroidUrl());
                }
            }
        }
    }

    public interface OnAdListener {
        /**
         * 广告资源下载好之后, 回调
         * 返回false, sdk继续操作
         */
        boolean onPrepareComplete(DataBean adDataBean);

        /**
         * 点击广告默认跳转
         * 返回true, 拦截默认处理
         */
        boolean onClickAd(DataBean adDataBean, String url);
    }
}
