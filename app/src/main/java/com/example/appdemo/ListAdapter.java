package com.example.appdemo;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qianmo.common.basis.AppHeaderFooterCommonAdapter;

import java.util.ArrayList;

public class ListAdapter extends AppHeaderFooterCommonAdapter<String> {

    public ListAdapter(Context mContext, ArrayList<String> data) {
        super(mContext, data);
    }

    @Override
    protected RecyclerView.ViewHolder createViewHolderImpl(ViewGroup parent, int viewType) {
        return new ListViewHolder(LayoutInflater.from(mContext).inflate(R.layout.support_simple_spinner_dropdown_item, parent, false));
    }

    @Override
    protected void bindViewHolderImpl(RecyclerView.ViewHolder holder, int position) {
        ListViewHolder viewHolder = (ListViewHolder) holder;
        viewHolder.view.setText(mData.get(position));
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView view;

        public ListViewHolder(View itemView) {
            super(itemView);
            view = (AppCompatTextView) itemView.findViewById(android.R.id.text1);
        }
    }
}
