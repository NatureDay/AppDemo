package com.qianmo.common.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.qianmo.common.basis.AppHeaderFooterCommonAdapter;

/**
 * 通用RecyclerView，配合AppCommonAdapter使用，支持emptyview，headerview，footerview
 */
public class AppCommonRecyclerView extends RecyclerView {
    /**
     * empty view
     */
    private View mEmptyView;
    /**
     * header view
     */
    private View mHeaderView;
    /**
     * footer view
     */
    private View mFooterView;

    /**
     * 监听数据变化，显示或隐藏emptyView
     */
    final private RecyclerView.AdapterDataObserver mObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            updateViewStatus();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            updateViewStatus();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            updateViewStatus();
        }
    };

    public AppCommonRecyclerView(Context context) {
        this(context, null);
    }

    public AppCommonRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AppCommonRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 设置header view，需在设置adapter之前设置
     *
     * @param headerView
     */
    public void setHeaderView(View headerView) {
        this.mHeaderView = headerView;
    }

    /**
     * 设置footer view，需在设置adapter之前设置
     *
     * @param footerView
     */
    public void setFooterView(View footerView) {
        this.mFooterView = footerView;
    }

    /**
     * 设置emptyView
     *
     * @param emptyView
     */
    public void setEmptyView(View emptyView) {
        mEmptyView = emptyView;
        updateViewStatus();
    }

    /**
     * 设置adapter
     */
    public void setAdapter(RecyclerView.Adapter adapter) {
        RecyclerView.Adapter oldAdapter = getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(mObserver);
        }
        if (adapter != null) {
            adapter.registerAdapterDataObserver(mObserver);
            if (adapter instanceof AppHeaderFooterCommonAdapter) {
                ((AppHeaderFooterCommonAdapter) adapter).setHeaderView(mHeaderView);
                ((AppHeaderFooterCommonAdapter) adapter).setFooterView(mFooterView);
            }
        }
        super.setAdapter(adapter);
        updateViewStatus();
    }

    private void updateViewStatus() {
        if (mEmptyView != null && getAdapter() != null) {
            final boolean emptyViewVisible = getAdapter().getItemCount() == 0;
            mEmptyView.setVisibility(emptyViewVisible ? VISIBLE : GONE);
            setVisibility(emptyViewVisible ? GONE : VISIBLE);
        }
    }
}