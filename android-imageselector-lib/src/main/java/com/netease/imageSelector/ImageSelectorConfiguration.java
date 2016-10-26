package com.netease.imageSelector;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;

/**
 * @author hzzhengrui
 * @Date 16/10/20
 * @Description
 */
public class ImageSelectorConfiguration {

    /** 最多可以选择图片的数目 */
    public int maxSelectNum = -1;
    /** 图片选择界面的列数 */
    public int spanCount = -1;

    public int selectMode = -1;
    public boolean isShowCamera = true;
    public boolean isEnablePreview = true;

    public boolean isEnableCrop = false;

    public Drawable imageOnLoading = null;
    public int imageResOnLoading = 0;

    public Drawable imageOnError = null;
    public int imageResOnError = 0;

    int titleBarColor = -1;
    int statusBarColor = -1;
    float titleHeight = -1;


    public ImageSelectorConfiguration(final Builder builder) {
        maxSelectNum = builder.maxSelectNum;
        spanCount = builder.spanCount;
        selectMode = builder.selectMode;
        isShowCamera = builder.isShowCamera;
        isEnablePreview = builder.isEnablePreview;
        isEnableCrop = builder.isEnableCrop;
        imageOnLoading = builder.imageOnLoading;
        imageResOnLoading = builder.imageResOnLoading;
        imageOnError = builder.imageOnError;
        imageResOnError = builder.imageResOnError;
        titleBarColor = builder.titleBarColor;
        statusBarColor = builder.statusBarColor;
        titleHeight = builder.titleHeight;
    }

    /**
     * 生成默认的图片选择器配置
     * @param context
     * @return
     */
    public static ImageSelectorConfiguration createDefault(Context context){
        return new Builder(context).build();
    }

    public static class Builder{

        public static final int DEFAULT_MAX_SELECT_NUM = 9;
        public static final int DEFAULT_SPAN_COUNT = 4;
        public static final int DEFAULT_SELECT_MODE = ImageSelectorConstant.MODE_MULTIPLE;


        private Context context;

        /** 最多可以选择图片的数目 */
        private int maxSelectNum = DEFAULT_MAX_SELECT_NUM;
        /** 图片选择界面的列数 */
        private int spanCount = DEFAULT_SPAN_COUNT;

        private int selectMode = DEFAULT_SELECT_MODE;
        private boolean isShowCamera = true;
        private boolean isEnablePreview = true;
        private boolean isEnableCrop = false;

        private Drawable imageOnLoading = null;
        private int imageResOnLoading = 0;

        private Drawable imageOnError = null;
        private int imageResOnError = 0;

        private int titleBarColor = -1;
        private int statusBarColor = -1;
        private float titleHeight = -1;

        public Builder(Context context) {
            this.context = context.getApplicationContext();
        }

        /**
         * 设置做多可选图片的数目
         * @param maxSelectNum
         * @return
         */
        public Builder setMaxSelectNum(int maxSelectNum){
            this.maxSelectNum = maxSelectNum;
            return this;
        }

        /**
         * 设置图片选择页面展示的列数
         * @param spanCount
         * @return
         */
        public Builder setSpanCount(int spanCount){
            this.spanCount = spanCount;
            return this;
        }

        /**
         * 设置选择模式,单选或者多选
         * @param selectMode
         * @return
         */
        public Builder setSelectMode(int selectMode) {
            this.selectMode = selectMode;
            return this;
        }

        /**
         * 设置是否可裁剪
         * @param enableCrop
         * @return
         */
        public Builder setEnableCrop(boolean enableCrop) {
            isEnableCrop = enableCrop;
            return this;
        }

        /**
         * 设置是否支持选择时候预览
         * @param enablePreview
         * @return
         */
        public Builder setEnablePreview(boolean enablePreview) {
            isEnablePreview = enablePreview;
            return this;
        }

        /**
         * 设置是否显示拍照按钮
         * @param showCamera
         * @return
         */
        public Builder setShowCamera(boolean showCamera) {
            isShowCamera = showCamera;
            return this;
        }

        /**
         * 设置图片加载时候的默认图
         * @param
         * @return
         */
        public Builder setImageOnLoading(Drawable image) {
            this.imageOnLoading = image;
            return this;
        }

        public Builder setImageOnLoading(int imageRes){
            this.imageResOnLoading = imageRes;
            return this;
        }

        public Builder setImageOnError(Drawable image) {
            this.imageOnError = image;
            return this;
        }

        public Builder setImageOnError(int imageRes){
            this.imageResOnError = imageRes;
            return this;
        }

        /**
         * 设置选择器titlebar的颜色
         * @param colorRes
         * @return
         */
        public Builder setTitleBarColor(int colorRes){
            this.titleBarColor = colorRes;
            return this;
        }

        /**
         * 设置选择器statusbar的颜色
         * @param colorRes
         * @return
         */
        public Builder setStatusBarColor(int colorRes){
            this.statusBarColor = colorRes;
            return this;
        }

        public Builder setTitleHeight(float titleHeight){
            this.titleHeight = titleHeight;
            return this;
        }

        public ImageSelectorConfiguration build(){
            initEmptyFieldsWithDefaultValues();
            return new ImageSelectorConfiguration(this);
        }

        private void initEmptyFieldsWithDefaultValues() {
            if(maxSelectNum <= 0){
                maxSelectNum = DEFAULT_MAX_SELECT_NUM;
            }
            if(spanCount <= 0){
                spanCount = DEFAULT_SPAN_COUNT;
            }
            if(selectMode <= 0){
                selectMode = DEFAULT_SELECT_MODE;
            }

            if(imageResOnLoading == 0 && imageOnLoading == null){
                imageResOnLoading = R.drawable.uis_ic_placeholder;
            }

            if(imageResOnError == 0 && imageOnError == null){
                imageResOnError = R.drawable.uis_ic_placeholder;
            }

            if(titleBarColor == -1){
                titleBarColor = R.color.uis_black;
            }

            if(statusBarColor == -1){
                statusBarColor = R.color.uis_black;
            }

            if(titleHeight <= 0){
                titleHeight = 48;
            }
        }
    }

}
