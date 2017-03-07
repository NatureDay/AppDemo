package com.qianmo.common.gallery.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appdemo.R;
import com.qianmo.android.library.isolation.ImageDisplay;
import com.qianmo.common.gallery.model.FileEntity;
import com.qianmo.common.gallery.ui.GalleryViewOperateListener;

import java.util.ArrayList;

public class GalleryPickerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private ArrayList<FileEntity> mData;
    private GalleryViewOperateListener mGalleryViewOperateListener;
    private boolean mIsMultiMode = false;

    public void setGalleryViewOperateListener(GalleryViewOperateListener galleryViewOperateListener) {
        this.mGalleryViewOperateListener = galleryViewOperateListener;
    }

    public void setMultiMode(boolean isMultiMode) {
        this.mIsMultiMode = isMultiMode;
    }

    public GalleryPickerAdapter(Context context, ArrayList<FileEntity> data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.common_gallery_picker_list_item, null);
        return new GalleryNormalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FileEntity fileEntity = mData.get(position);
        GalleryNormalViewHolder galleryNormalViewHolder = (GalleryNormalViewHolder) holder;
        galleryNormalViewHolder.imageView.setTag(fileEntity);
        galleryNormalViewHolder.imageView.setOnClickListener(this);
        ImageDisplay.displayImage(mContext, galleryNormalViewHolder.imageView, fileEntity.getPath(), 100, 100);
        if (!mIsMultiMode) {
            galleryNormalViewHolder.select.setVisibility(View.GONE);
        } else {
            galleryNormalViewHolder.select.setVisibility(View.VISIBLE);
            galleryNormalViewHolder.select.setChecked(fileEntity.isSelected());
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.gallery_picker_item_image_view:
                if (mGalleryViewOperateListener != null) {
                    mGalleryViewOperateListener.onNormalViewClick(view);
                }
                break;
        }
    }

    private class GalleryNormalViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView imageView;
        AppCompatCheckBox select;

        public GalleryNormalViewHolder(View itemView) {
            super(itemView);
            imageView = (AppCompatImageView) itemView.findViewById(R.id.gallery_picker_item_image_view);
            select = (AppCompatCheckBox) itemView.findViewById(R.id.gallery_picker_item_select);
        }
    }
}
