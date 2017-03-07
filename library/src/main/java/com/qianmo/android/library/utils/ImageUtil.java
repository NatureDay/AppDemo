package com.qianmo.android.library.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.media.ExifInterface;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 图片处理相关工具类
 */

public class ImageUtil {

    /**
     * 判断照片角度
     *
     * @param path
     * @return
     */
    public static int readPictureDegree(String path) {
        int rotation = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotation = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotation = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotation = 270;
                    break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotation;
    }

    /**
     * 旋转照片
     *
     * @param bitmap
     * @param degress
     * @return
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, int degress) {
        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(degress);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), m, true);
            return bitmap;
        }
        return bitmap;
    }

    /**
     * Compress by quality,  and generate image to the path specified（质量压缩）
     *
     * @param srcPath
     * @param targetPath
     * @param maxSize
     * @throws IOException
     */
    public static void compressImageByQuality(String srcPath, String targetPath, int maxSize) throws IOException {
        compressImageByQuality(srcPath, targetPath, maxSize, false);
    }

    /**
     * Compress by quality,  and generate image to the path specified
     *
     * @param srcPath
     * @param targetPath
     * @param maxSize     target will be compressed to be smaller than this size.(kb)
     * @param needsDelete Whether delete original file after compress
     * @throws IOException
     */
    public static void compressImageByQuality(String srcPath, String targetPath, int maxSize, boolean needsDelete) throws IOException {
        compressImageByQuality(getBitmap(srcPath), targetPath, maxSize);
        if (needsDelete) {
            File file = new File(srcPath);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    /**
     * Compress by quality,  and generate image to the path specified，
     *
     * @param image
     * @param targetPath
     * @param maxSize    target will be compressed to be smaller than this size.(kb)
     * @throws IOException
     */
    public static void compressImageByQuality(Bitmap image, String targetPath, int maxSize) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        int options = 100;
        image.compress(Bitmap.CompressFormat.JPEG, options, os);
        while (os.toByteArray().length / 1024 > maxSize) {
            os.reset();
            options -= 10;
            image.compress(Bitmap.CompressFormat.JPEG, options, os);
        }
        FileOutputStream fos = new FileOutputStream(targetPath);
        fos.write(os.toByteArray());
        fos.flush();
        fos.close();
    }

    /**
     * Ratio and generate thumb to the path specified（尺寸压缩）
     *
     * @param srcPath
     * @param targetPath
     * @param pixelW
     * @param pixelH
     * @throws FileNotFoundException
     */
    public void compressImageBySize(String srcPath, String targetPath, float pixelW, float pixelH) throws FileNotFoundException {
        compressImageBySize(srcPath, targetPath, pixelW, pixelH, false);
    }

    /**
     * Ratio and generate thumb to the path specified
     *
     * @param targetPath
     * @param pixelW      target pixel of width
     * @param pixelH      target pixel of height
     * @param needsDelete Whether delete original file after compress
     * @throws FileNotFoundException
     */
    public void compressImageBySize(String srcPath, String targetPath, float pixelW, float pixelH, boolean needsDelete) throws FileNotFoundException {
        Bitmap bitmap = ratio(srcPath, pixelW, pixelH);
        storeImage(bitmap, targetPath);
        if (needsDelete) {
            File file = new File(srcPath);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    /**
     * Ratio and generate thumb to the path specified
     *
     * @param image
     * @param targetPath
     * @param pixelW     target pixel of width
     * @param pixelH     target pixel of height
     * @throws FileNotFoundException
     */
    public void compressImageBySize(Bitmap image, String targetPath, float pixelW, float pixelH) throws FileNotFoundException {
        Bitmap bitmap = ratio(image, pixelW, pixelH);
        storeImage(bitmap, targetPath);
    }

    /**
     * Get bitmap from specified image path
     *
     * @param srcPath
     * @return
     */
    public static Bitmap getBitmap(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = false;
        newOpts.inPurgeable = true;
        newOpts.inInputShareable = true;
        newOpts.inSampleSize = 1;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        return BitmapFactory.decodeFile(srcPath, newOpts);
    }

    /**
     * Store bitmap into specified image path
     *
     * @param bitmap
     * @param targetPath
     * @throws FileNotFoundException
     */
    public void storeImage(Bitmap bitmap, String targetPath) throws FileNotFoundException {
        FileOutputStream os = new FileOutputStream(targetPath);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
    }

    /**
     * Compress image by pixel, this will modify image width/height.
     * Used to get thumbnail
     *
     * @param srcPath image path
     * @param pixelW  target pixel of width
     * @param pixelH  target pixel of height
     * @return
     */
    public Bitmap ratio(String srcPath, float pixelW, float pixelH) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = pixelH;
        float ww = pixelW;
        int be = 1;
        if (w > h && w > ww) {
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0) be = 1;
        newOpts.inSampleSize = be;
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return bitmap;
    }

    /**
     * Compress image by size, this will modify image width/height.
     * Used to get thumbnail
     *
     * @param image
     * @param pixelW target pixel of width
     * @param pixelH target pixel of height
     * @return
     */
    public Bitmap ratio(Bitmap image, float pixelW, float pixelH) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, os);
        if (os.toByteArray().length / 1024 > 1024) {
            os.reset();
            image.compress(Bitmap.CompressFormat.JPEG, 50, os);
        }
        ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = BitmapFactory.decodeStream(is, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = pixelH;
        float ww = pixelW;
        int be = 1;
        if (w > h && w > ww) {
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0) be = 1;
        newOpts.inSampleSize = be;
        is = new ByteArrayInputStream(os.toByteArray());
        bitmap = BitmapFactory.decodeStream(is, null, newOpts);
        return bitmap;
    }

    /**
     * bitmap转成string
     *
     * @param bitmap
     * @return
     */
    public static String convertImageToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] appicon = baos.toByteArray();// 转为byte数组
        return Base64.encodeToString(appicon, Base64.DEFAULT);
    }

    /**
     * string转成bitmap
     *
     * @param st
     */
    public static Bitmap convertStringToImage(String st) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(st, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
            return bitmap;
        } catch (Exception e) {
            return null;
        }
    }

}
