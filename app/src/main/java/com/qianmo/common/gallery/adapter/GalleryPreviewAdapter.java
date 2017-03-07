package com.qianmo.common.gallery.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.qianmo.android.library.isolation.ImageDisplay;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;

public class GalleryPreviewAdapter extends PagerAdapter {

    private Context mContext;

    private ArrayList<String> mData;

    public GalleryPreviewAdapter(Context context, ArrayList<String> data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        String url = mData.get(position);
        if (TextUtils.isEmpty(url)) return null;
        PhotoView photoView = new PhotoView(container.getContext());
        ImageDisplay.displayImage(mContext, photoView, url);
        container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return photoView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
