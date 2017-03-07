package com.qianmo.common.gallery.ui;

import android.view.View;
import android.widget.CompoundButton;

/**
 * 图片相关操作的回调接口
 */
public interface GalleryViewOperateListener {

    /**
     * image view的点击事件回调接口
     */
    void onNormalViewClick(View view);

//    /**
//     * 选择view的选择状态变化的回调接口
//     */
//    void onSelectChanged(CompoundButton compoundButton, boolean b);

}
