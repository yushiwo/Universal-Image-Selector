package com.netease.imageSelector;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.netease.imageSelector.interfaces.IProxy;

/**
 * @author hzzhengrui
 * @Date 16/10/24
 * @Description
 */
public class ImageSelectorProxy implements IProxy{

    private static final String ERROR_NOT_INIT = "ImageSelector must be init with configuration before using";

    private static ImageSelectorProxy sInstance;

    ImageSelectorConfiguration configuration;

    public static ImageSelectorProxy getInstance(){
        if (sInstance == null) {
            synchronized (ImageSelector.class) {
                if (sInstance == null) {
                    sInstance = new ImageSelectorProxy();
                }
            }
        }
        return sInstance;
    }

    /**
     * 设置图片选择器的配置
     * @param configuration
     */
    public void setConfiguration(ImageSelectorConfiguration configuration){
        this.configuration = configuration;
    }

    private void checkConfiguration() {
        if (configuration == null) {
            throw new IllegalStateException(ERROR_NOT_INIT);
        }
    }

    @Override
    public int getMaxSelectNum() {
        checkConfiguration();
        return configuration.maxSelectNum;
    }

    @Override
    public int getSpanCount() {
        checkConfiguration();
        return configuration.spanCount;
    }

    @Override
    public int getSelectMode() {
        checkConfiguration();
        return configuration.selectMode;
    }

    @Override
    public boolean isEnablePreview() {
        checkConfiguration();
        return configuration.isEnablePreview;
    }

    @Override
    public boolean isShowCamera() {
        checkConfiguration();
        return configuration.isShowCamera;
    }

    @Override
    public boolean isEnableCorp() {
        checkConfiguration();
        return configuration.isEnableCrop;
    }

    @Override
    public Drawable getImageOnLoading(Resources res) {
        checkConfiguration();
        return configuration.imageResOnLoading != 0 ? res.getDrawable(configuration.imageResOnLoading) : configuration.imageOnLoading;
    }

    @Override
    public Drawable getImageOnError(Resources res) {
        checkConfiguration();
        return configuration.imageResOnError != 0 ? res.getDrawable(configuration.imageResOnError) : configuration.imageOnError;
    }

    @Override
    public int getTitleBarColor(Resources res) {
        checkConfiguration();
        return res.getColor(configuration.titleBarColor);
    }

    @Override
    public int getStatusBarColor(Resources res) {
        checkConfiguration();
        return res.getColor(configuration.statusBarColor);
    }

    @Override
    public float getTitleHeight() {
        checkConfiguration();
        return configuration.titleHeight;
    }
}
