package com.qianmo.common.gallery.tools;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.qianmo.android.library.utils.ImageUtil;
import com.qianmo.android.library.utils.LogUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 图片处理，包括压缩，旋转等
 */

public class ImageProcessTask extends AsyncTask<ArrayList<String>, Void, ArrayList<String>> {

    private static final String TAG = "ImageProcessTask";

    private static final int DEFAULT_MAX_SIZE = 120;

    private Context mContext;

    public ImageProcessTask(Context context) {
        this.mContext = context;
    }

    private ImageProcssListener mImageProcssListener;

    public void setImageProcssListener(ImageProcssListener imageProcssListener) {
        this.mImageProcssListener = imageProcssListener;
    }

    public interface ImageProcssListener {
        void onProcessBegin();

        void onProcessFinish(ArrayList<String> result);
    }

    @Override
    protected ArrayList<String> doInBackground(ArrayList<String>... params) {
        ArrayList<String> srcPaths = params[0];
        ArrayList<String> result = new ArrayList<String>();
        File file = mContext.getExternalCacheDir();
        if (file == null) return result;
        File dir = new File(file.getAbsolutePath() + File.separator + "common");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        for (String srcPath : srcPaths) {
            if (TextUtils.isEmpty(srcPath)) {
                result.add("");
                continue;
            }
            String targetPath = dir.getAbsolutePath() + srcPath.substring(srcPath.lastIndexOf(File.separator));
            try {
                ImageUtil.compressImageByQuality(srcPath, targetPath, DEFAULT_MAX_SIZE);
                result.add(targetPath);
            } catch (IOException e) {
                LogUtil.e(TAG, "compress image:" + srcPath + "  failure!!!");
                result.add("");
            }
        }
        return result;
    }

    @Override
    protected void onPreExecute() {
        if (mImageProcssListener != null) {
            mImageProcssListener.onProcessBegin();
        }
    }

    @Override
    protected void onPostExecute(ArrayList<String> result) {
        if (mImageProcssListener != null) {
            mImageProcssListener.onProcessFinish(result);
        }
    }
}
