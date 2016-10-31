package com.netease.imageSelector.utils;

import android.content.Context;
import android.os.Environment;

import com.netease.imageSelector.ImageSelectorProxy;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by dee on 15/11/20.
 */
public class FileUtils {
    public static final String POSTFIX = ".JPEG";
    public static String APP_NAME = "ImageSelector";
    public static String CAMERA_PATH = "/" + APP_NAME + "/CameraImage/";
    public static String CROP_PATH = "/" + APP_NAME + "/CropImage/";

    public static File createCameraFile(Context context) {
        APP_NAME = ImageSelectorProxy.getInstance().getImageSaveDirectory(context.getResources());
        CAMERA_PATH = "/" + APP_NAME;
        return createMediaFile(context,CAMERA_PATH);
    }
    public static File createCropFile(Context context) {
        APP_NAME = ImageSelectorProxy.getInstance().getImageSaveDirectory(context.getResources());
        CROP_PATH = "/" + APP_NAME + "/CropImage/";
        return createMediaFile(context,CROP_PATH);
    }

    private static File createMediaFile(Context context, String parentPath){
        APP_NAME = ImageSelectorProxy.getInstance().getImageSaveDirectory(context.getResources());
        String state = Environment.getExternalStorageState();
        File rootDir = state.equals(Environment.MEDIA_MOUNTED)? Environment.getExternalStorageDirectory():context.getCacheDir();

        File folderDir = new File(rootDir.getAbsolutePath() + parentPath);
        if (!folderDir.exists() && folderDir.mkdirs()){

        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
        String fileName = APP_NAME + "_" + timeStamp + "";
        File tmpFile = new File(folderDir, fileName + POSTFIX);
        return tmpFile;
    }
}
