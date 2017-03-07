package com.qianmo.common.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 通用分页加载RecyclerView，配合AppHeaderFooterCommonAdapter使用
 */

public class AppCommonLoadRecyclerView extends AppCommonRecyclerView {

    /**
     * 分页加载时显示view
     */
    private View mLoadView;
    /**
     * 回调监听，用于加载更多
     */
    protected RecyclerLoadListener mRecyclerLoadListener;
    /**
     * 加载更多功能是否启用
     */
    protected boolean mIsLoadEnable = false;
    /**
     * 是否正在加载
     */
    protected boolean mIsLoading = false;

    private AppCommonOnScrollListener mAppCommonOnScrollListener;

    public AppCommonLoadRecyclerView(Context context) {
        this(context, null);
    }

    public AppCommonLoadRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AppCommonLoadRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        if (mAppCommonOnScrollListener == null) {
            mAppCommonOnScrollListener = new AppCommonOnScrollListener();
        }
        addOnScrollListener(mAppCommonOnScrollListener);
    }

    private class AppCommonOnScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                int visibleItemCount = linearLayoutManager.getChildCount();
                int firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
                int totalItemCount = linearLayoutManager.getItemCount();
                if (mIsLoading || !mIsLoadEnable) return;
                if (totalItemCount <= visibleItemCount) return;
                int lastItemIndex = firstVisibleItem + visibleItemCount;
                if (lastItemIndex >= totalItemCount && mRecyclerLoadListener != null) {
                    mIsLoading = true;
                    updateLoadViewVisibility(true);
                    mRecyclerLoadListener.loadData();
                }
            }
        }
    }

    private void updateLoadViewVisibility(boolean visible) {
        Log.e("fff", "------updateLoadViewVisibility======" + visible);
        if (mLoadView == null) return;
        if (mIsLoadEnable) {
            if (visible) {
                mLoadView.setVisibility(View.VISIBLE);
            } else {
                mLoadView.setVisibility(View.GONE);
            }
        } else {
            mLoadView.setVisibility(View.GONE);
        }
    }

    /**
     * 设置正在加载view
     *
     * @param loadView
     */
    public void setLoadView(View loadView) {
        this.mLoadView = loadView;
        setFooterView(mLoadView);
    }

    /**
     * 数据加载结束后调用此方法
     */
    public void onLoadComplete() {
        mIsLoading = false;
        updateLoadViewVisibility(false);
    }

    /**
     * 判断是否正在加载中
     *
     * @return
     */
    public boolean isLoading() {
        return mIsLoading;
    }

    /**
     * 判断是否可以自动加载
     */
    public boolean isLoadEnable() {
        return mIsLoadEnable;
    }

    /**
     * 设置是否启用自动加载功能
     *
     * @param isEnable
     */
    public void setLoadEnable(boolean isEnable) {
        mIsLoadEnable = isEnable;
    }

    public void setRecyclerLoadListener(RecyclerLoadListener recyclerLoadListener) {
        this.mRecyclerLoadListener = recyclerLoadListener;
    }
}
