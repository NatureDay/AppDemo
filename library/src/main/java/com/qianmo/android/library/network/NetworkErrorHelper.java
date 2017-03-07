package com.qianmo.android.library.network;

import com.qianmo.android.library.R;

import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;

import retrofit2.adapter.rxjava.HttpException;

/**
 * 请求异常处理
 */
public class NetworkErrorHelper {

    private static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int METHOD_NOT_ALLOWED = 405;
    private static final int NOT_ACCEPTABLE = 406;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int NOT_IMPLEMENTED = 501;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static int getMessage(Throwable e) {
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            switch (httpException.code()) {
                case BAD_REQUEST:
                    return R.string.http_error_msg_bad_request;
                case UNAUTHORIZED:
                    return R.string.http_error_msg_unauthorized;
                case FORBIDDEN:
                    return R.string.http_error_msg_forbidden;
                case NOT_FOUND:
                    return R.string.http_error_msg_not_found;
                case METHOD_NOT_ALLOWED:
                    return R.string.http_error_msg_method_not_allowed;
                case NOT_ACCEPTABLE:
                    return R.string.http_error_msg_not_acceptable;
                case INTERNAL_SERVER_ERROR:
                    return R.string.http_error_msg_internal_server_error;
                case NOT_IMPLEMENTED:
                    return R.string.http_error_msg_not_implemented;
                case BAD_GATEWAY:
                    return R.string.http_error_msg_bad_gateway;
                case SERVICE_UNAVAILABLE:
                    return R.string.http_error_msg_service_unavailable;
                case GATEWAY_TIMEOUT:
                    return R.string.http_error_msg_gateway_timeout;
                default:
                    return R.string.http_error_msg_unknown;
            }
        } else if (e instanceof IOException) {
            return R.string.error_msg_server_fail;
        } else if (e instanceof JSONException || e instanceof ParseException) {
            return R.string.error_msg_exception_parse;
        } else {
            return R.string.error_msg_exception_unknown;
        }
    }

}
