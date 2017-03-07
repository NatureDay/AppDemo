package com.qianmo.common.view;

/**
 * RecyclerView item点击事件接口标准
 */

public interface OnItemClickListener {
    /**
     * 单击事件
     *
     * @param position
     */
    void onClick(int position);

    /**
     * 长按事件
     *
     * @param position
     */
    void onLongClick(int position);
}