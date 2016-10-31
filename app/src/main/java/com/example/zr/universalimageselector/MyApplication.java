package com.example.zr.universalimageselector;

import android.app.Application;

import com.netease.imageSelector.ImageSelector;
import com.netease.imageSelector.ImageSelectorConfiguration;
import com.netease.imageSelector.ImageSelectorConstant;

/**
 * @author hzzhengrui
 * @Date 16/10/24
 * @Description
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//                ImageSelectorConfiguration configuration = ImageSelectorConfiguration.createDefault(this);

        ImageSelectorConfiguration configuration = new ImageSelectorConfiguration.Builder(this)
                .setImageSaveDirectory(R.string.app_name)
                .setMaxSelectNum(9)
                .setSpanCount(4)
                .setSelectMode(ImageSelectorConstant.MODE_MULTIPLE)
                .setTitleHeight(48)
                .build();
        ImageSelector.getInstance().init(configuration);
    }
}
