package com.netease.imageSelector.interfaces;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

/**
 * @author hzzhengrui
 * @Date 16/10/24
 * @Description
 */
public interface IProxy {

    int getMaxSelectNum();

    int getSpanCount();

    int getSelectMode();

    boolean isEnablePreview();

    boolean isShowCamera();

    boolean isEnableCorp();

    Drawable getImageOnLoading(Resources res);
    Drawable getImageOnError(Resources res);

    int getTitleBarColor(Resources res);
    int getStatusBarColor(Resources res);
    float getTitleHeight();

}
