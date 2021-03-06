package com.qianmo.android.library.network.converter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * @Auther: Administrator
 * @Date: 2016/7/12 15:03
 */
final class JsonResponseBodyConverter {

    private JsonResponseBodyConverter() {
    }

    static final class JSONObjectResponseBodyConverter implements Converter<ResponseBody, JSONObject> {
        static final JSONObjectResponseBodyConverter INSTANCE = new JSONObjectResponseBodyConverter();

        @Override
        public JSONObject convert(ResponseBody value) throws IOException {
            try {
                return new JSONObject(value.string());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    static final class JSONArrayResponseBodyConverter implements Converter<ResponseBody, JSONArray> {
        static final JSONArrayResponseBodyConverter INSTANCE = new JSONArrayResponseBodyConverter();

        @Override
        public JSONArray convert(ResponseBody value) throws IOException {
            try {
                return new JSONArray(value.string());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
