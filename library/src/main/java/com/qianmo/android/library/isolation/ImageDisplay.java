package com.qianmo.android.library.isolation;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.ImageView;

import com.qianmo.android.library.R;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * 图片展示封装类
 */
public class ImageDisplay {

    /**
     * load Context,default
     */
    public static void displayImage(Context context, ImageView view, String url) {
        if (view == null) return;
        if (TextUtils.isEmpty(url)) return;

        Picasso.with(context)
                .load(new File(url))
                .placeholder(R.drawable.ic_crop_original_black_24dp)
                .error(R.drawable.ic_crop_original_black_24dp)
                .config(Bitmap.Config.RGB_565)
                .into(view);
    }

    public static void displayImage(Context context, ImageView view, int resId) {
        if (view == null) return;
        if (resId == 0) return;
        Picasso.with(context)
                .load(resId)
                .placeholder(R.drawable.ic_crop_original_black_24dp)
                .error(R.drawable.ic_crop_original_black_24dp)
                .into(view);
    }

    public static void displayImage(Context context, ImageView view, String url, int defaultId) {
        if (view == null) return;
        if (TextUtils.isEmpty(url)) return;
        Picasso.with(context)
                .load(url)
                .config(Bitmap.Config.RGB_565)
                .placeholder(defaultId)
                .error(defaultId)
                .into(view);
    }

    /**
     * 定义加载图片的大小，优化性能
     *
     * @param context
     * @param view
     * @param url
     * @param width
     * @param height
     */
    public static void displayImage(Context context, ImageView view, String url, int width, int height) {
        if (view == null) return;
        if (TextUtils.isEmpty(url)) return;
        Picasso.with(context)
                .load(new File(url))
                .resize(width, height)
                .placeholder(R.drawable.ic_crop_original_black_24dp)
                .error(R.drawable.ic_crop_original_black_24dp)
                .config(Bitmap.Config.RGB_565)
                .into(view);
    }

}
