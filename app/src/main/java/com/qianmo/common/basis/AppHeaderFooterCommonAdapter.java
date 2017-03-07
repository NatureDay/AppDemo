package com.qianmo.common.basis;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * 用于封装head and footer.
 */

public abstract class AppHeaderFooterCommonAdapter<T> extends AppCommonAdapter<T> {

    public static final int ITEM_VIEW_TYPE_HEADER = -1;
    public static final int ITEM_VIEW_TYPE_FOOTER = -2;

    private View mHeaderView;
    private View mFooterView;

    public AppHeaderFooterCommonAdapter(Context context, ArrayList<T> data) {
        super(context, data);
    }

    /**
     * 设置header view
     *
     * @param headerView
     */
    public void setHeaderView(View headerView) {
        this.mHeaderView = headerView;
    }

    /**
     * 设置footer view
     *
     * @param footerView
     */
    public void setFooterView(View footerView) {
        this.mFooterView = footerView;
    }

    @Override
    public int getItemCount() {
        if (mHeaderView == null && mFooterView == null) {
            return super.getItemCount();
        } else if (mHeaderView != null && mFooterView == null) {
            return super.getItemCount() + 1;
        } else if (mHeaderView == null && mFooterView != null) {
            return super.getItemCount() + 1;
        } else {
            return super.getItemCount() + 2;
        }
    }

    @Override
    public long getItemId(int position) {
        int numHeaders = 0;
        if (mHeaderView != null) {
            numHeaders = 1;
        }
        if (position >= numHeaders) {
            int adjPosition = position - numHeaders;
            int adapterCount = super.getItemCount();
            if (adjPosition < adapterCount) {
                return getItemIdImpl(adjPosition);
            }
        }
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        int numHeaders = 0;
        if (mHeaderView != null) {
            numHeaders = 1;
        }
        if (position < numHeaders) {
            return ITEM_VIEW_TYPE_HEADER;
        }
        if (position >= numHeaders) {
            int adjPosition = position - numHeaders;
            int adapterCount = super.getItemCount();
            if (adjPosition < adapterCount) {
                return getItemViewTypeImpl(adjPosition);
            }
        }
        return ITEM_VIEW_TYPE_FOOTER;
    }

    /**
     * 方法getItemId在增加header或footer之后的实现，如有需要子类应重写该方法
     *
     * @param position
     * @return
     */
    protected long getItemIdImpl(int position) {
        return super.getItemId(position);
    }

    /**
     * 方法getItemViewType在增加header或footer之后的实现，如有需要子类应重写该方法
     *
     * @param position
     * @return
     */
    protected int getItemViewTypeImpl(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_VIEW_TYPE_HEADER) {
            return new HeaderViewHolder(mHeaderView);
        } else if (viewType == ITEM_VIEW_TYPE_FOOTER) {
            return new FooterViewHolder(mFooterView);
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        if (type == ITEM_VIEW_TYPE_HEADER || type == ITEM_VIEW_TYPE_FOOTER) return;
        int numHeaders = 0;
        if (mHeaderView != null) {
            numHeaders = 1;
        }
        if (position >= numHeaders) {
            int adjPosition = position - numHeaders;
            int adapterCount = super.getItemCount();
            if (adjPosition < adapterCount) {
                super.onBindViewHolder(holder, adjPosition);
            }
        }
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class FooterViewHolder extends RecyclerView.ViewHolder {
        FooterViewHolder(View itemView) {
            super(itemView);
        }
    }
}
