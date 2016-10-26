package com.netease.imageSelector;

import android.app.Activity;
import android.util.Log;

import com.netease.imageSelector.view.ImagePreviewActivity;
import com.netease.imageSelector.view.ImageSelectorActivity;

import java.util.ArrayList;


/**
 * @author hzzhengrui
 * @Date 16/10/20
 * @Description
 */
public class ImageSelector {

    private static final String TAG = ImageSelector.class.getSimpleName();

    private static final String WARNING_RE_INIT_CONFIG = "Try to initialize ImageSelector which had already been initialized before. " + "To re-init ImageSelector with new configuration call ImageSelector.destroy() at first.";
    private static final String ERROR_INIT_CONFIG_WITH_NULL = "ImageSelector configuration can not be initialized with null";
    private static final String ERROR_NOT_INIT = "ImageSelector must be init with configuration before using";

    private static ImageSelector sInstance;

    private ImageSelectorConfiguration configuration;

    public static ImageSelector getInstance(){
        if (sInstance == null) {
            synchronized (ImageSelector.class) {
                if (sInstance == null) {
                    sInstance = new ImageSelector();
                }
            }
        }
        return sInstance;
    }

    // TODO: 16/10/20 实现config设置
    public void init(ImageSelectorConfiguration configuration){
        if(configuration == null){
            throw new IllegalArgumentException(ERROR_INIT_CONFIG_WITH_NULL);
        }

        if(this.configuration == null){
            this.configuration = configuration;
            ImageSelectorProxy.getInstance().setConfiguration(configuration);
        }else {
            Log.w(TAG, WARNING_RE_INIT_CONFIG);
        }
    }

    public boolean isInited() {
        return configuration != null;
    }

    private void checkConfiguration() {
        if (configuration == null) {
            throw new IllegalStateException(ERROR_NOT_INIT);
        }
    }

    public void destroy() {
        if (configuration != null) {
            configuration = null;
        }
    }

    /**
     * 开启图片选择页面
     * @param activity
     * @param imageList
     */
    public void launchSelector(Activity activity, ArrayList<String> imageList){
        checkConfiguration();
        ImageSelectorActivity.start(activity, imageList);
    }

    /**
     * 开启图片可删除选中图片的预览界面
     * @param activity
     * @param imageList
     * @param position
     */
    public void launchDeletePreview(Activity activity, ArrayList<String> imageList, int position){
        checkConfiguration();
        ImagePreviewActivity.startDeletePreview(activity, imageList, position);
    }

}
