package com.netease.imageSelector.utils;

import android.content.ContentValues;
import android.content.Context;
import android.provider.MediaStore;

/**
 * @author hzzhengrui
 * @Date 16/10/28
 * @Description
 */
public class ImageUtils {
    /**
     * 添加到相册
     * @param filePath 文件路径
     * @param context 上下文
     */
    public static void addImageToGallery(String filePath, Context context) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis());
        values.put(MediaStore.MediaColumns.DATA, filePath);
        context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }
}
