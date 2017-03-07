package com.qianmo.common.basis;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.qianmo.common.view.OnItemClickListener;

import java.util.ArrayList;

/**
 * 抽象类，用于封装Adapter一些基本操作
 */
public abstract class AppCommonAdapter<T> extends RecyclerView.Adapter {

    protected Context mContext;
    protected ArrayList<T> mData;

    /**
     * 回调监听，RecyclerView item点击事件
     */
    public OnItemClickListener mOnItemClickListener;

    public AppCommonAdapter(Context context, ArrayList<T> data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return createViewHolderImpl(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindViewHolderImpl(holder, position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    protected abstract RecyclerView.ViewHolder createViewHolderImpl(ViewGroup parent, int viewType);

    protected abstract void bindViewHolderImpl(RecyclerView.ViewHolder holder, int position);

}
