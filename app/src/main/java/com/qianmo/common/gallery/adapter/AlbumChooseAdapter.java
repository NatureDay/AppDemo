package com.qianmo.common.gallery.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appdemo.R;
import com.qianmo.android.library.isolation.ImageDisplay;
import com.qianmo.common.gallery.model.FolderEntity;

import java.util.ArrayList;

public class AlbumChooseAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private Context mContext;
    private ArrayList<FolderEntity> mData;

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view);
    }

    public AlbumChooseAdapter(Context context, ArrayList<FolderEntity> data) {
        this.mContext = context;
        this.mData = data;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AlbumChooseViewHolder(LayoutInflater.from(mContext).inflate(R.layout.common_album_choose_item, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AlbumChooseViewHolder albumChooseViewHolder = (AlbumChooseAdapter.AlbumChooseViewHolder) holder;
        FolderEntity pf = mData.get(position);
        albumChooseViewHolder.itemView.setTag(pf);
        albumChooseViewHolder.itemView.setOnClickListener(this);
        ImageDisplay.displayImage(mContext, albumChooseViewHolder.icon, pf.getFileEntityList().get(0).getPath(), 100, 100);
        albumChooseViewHolder.name.setText(pf.getName());
        if (pf.isSelected()) {
            albumChooseViewHolder.choose.setVisibility(View.VISIBLE);
        } else {
            albumChooseViewHolder.choose.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(view);
        }
    }

    private class AlbumChooseViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView icon;
        AppCompatTextView name;
        AppCompatImageView choose;

        AlbumChooseViewHolder(View itemView) {
            super(itemView);
            icon = (AppCompatImageView) itemView.findViewById(R.id.album_chooss_item_icon);
            name = (AppCompatTextView) itemView.findViewById(R.id.album_chooss_item_name);
            choose = (AppCompatImageView) itemView.findViewById(R.id.album_chooss_item_choose);
        }
    }

}
