package com.qianmo.android.library.network.converter;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * @Auther: Administrator
 * @Date: 2016/7/12 14:57
 */
final class JsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
    static final JsonRequestBodyConverter<Object> INSTANCE = new JsonRequestBodyConverter<>();
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");

    private JsonRequestBodyConverter() {
    }

    @Override
    public RequestBody convert(T value) throws IOException {
        return RequestBody.create(MEDIA_TYPE, String.valueOf(value));
    }
}
