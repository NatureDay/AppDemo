package com.qianmo.common;

public class ConfigCommon {

    /**
     * 隐式启动Gallery预览页面Action
     */
    public static final String ACTION_COMMON_GALLERY_PREVIEW = "com.qianmo.common.gallery.preview";
    /**
     * 预览的图片地址集合，ArrayList<String>
     */
    public static final String KEY_GALLERY_PREVIEW_IMAGE_URLS = "KEY_GALLERY_PREVIEW_IMAGE_URLS";
    /**
     * 指定打开预览图片的index
     */
    public static final String KEY_GALLERY_PREVIEW_IMAGE_INDEX = "KEY_GALLERY_PREVIEW_IMAGE_INDEX";


    /**
     * 隐式启动Gallery图片选择页面Action
     */
    public static final String ACTION_COMMON_GALLERY_PICKER = "com.qianmo.common.gallery.picker";
    /**
     * 图片选择模式，单选(false)，多选(true)；以boolean值区分
     */
    public static final String KEY_GALLERY_PICKER_MULTI_MODE = "KEY_GALLERY_PICKER_MULTI_MODE";
    /**
     * 图片选择结果，以ArrayList<String>形式返回
     */
    public static final String KEY_GALLERY_PICKER_RESULT = "KEY_GALLERY_PICKER_RESULT";


    public static final String KEY_GALLERY_ALBUM_DATA = "KEY_GALLERY_ALBUM_DATA";


}
