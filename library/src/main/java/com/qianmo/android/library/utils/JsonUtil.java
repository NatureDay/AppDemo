package com.qianmo.android.library.utils;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * json工具类
 */

public class JsonUtil {

    private static final Gson mGson = new Gson();

    /**
     * 对象转json字符串
     *
     * @param src
     * @return
     */
    public static String toJson(Object src) {
        if (src instanceof JSONObject)
            return src.toString();
        return mGson.toJson(src);
    }

    /**
     * json字符串转对象,解析多个实体类集合
     *
     * @param json    字符串
     * @param typeOfT new TypeToken<ArrayList<T>>(){}.getType()
     * @return T
     */
    public static <T> T fromJson(String json, Type typeOfT) {
        try {
            return mGson.fromJson(json, typeOfT);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * json字符串转对象,从json字符串解析为单个实体类
     *
     * @param json  字符串
     * @param clazz 实体类
     * @return T 实体类
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return mGson.fromJson(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
