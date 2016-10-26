package com.example.zr.universalimageselector;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * author: Chen Wei
 * time: 16/10/19
 * desc: Image工具类
 */

public class ImageUtils {

    private static final String TAG = ImageUtils.class.getCanonicalName();

    private static final int DEFAULT_PHOTO_MAX_WIDTH = 2048;
    private static final int DEFAULT_PHOTO_MAX_HEIGHT = 2048;

    public static int resolveBitmapOrientation(File bitmapFile) throws IOException {
        ExifInterface exif;
        exif = new ExifInterface(bitmapFile.getAbsolutePath());

        return exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
    }

    public static Bitmap applyOrientation(Bitmap bitmap, int orientation) {
        int rotate = 0;
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_270:
                rotate = 270;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                rotate = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                rotate = 90;
                break;
            default:
                return bitmap;
        }

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix mtx = new Matrix();
        mtx.postRotate(rotate);
        return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }


    public static Bitmap decodeSampledBitmapFromFile(String filePath,
                                                     int reqWidth, int reqHeight) {
        File file = new File(filePath);
        int orientation = 0;
        try {
            orientation = resolveBitmapOrientation(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        return applyOrientation(bitmap, orientation);
    }




}
