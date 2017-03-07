package com.qianmo.android.library.utils;

import android.content.Context;

/**
 * JNI tools
 */

public class JniUtil {

    // ndk层进行签名验证, context作为参数传递
    public static native void verifySignature(Context context);

}
