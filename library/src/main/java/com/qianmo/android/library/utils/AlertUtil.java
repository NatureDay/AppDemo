package com.qianmo.android.library.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * 提示工具类
 */

public class AlertUtil {

    /**
     * Toast相关
     */
    private static Toast mToast;
    private static Toast mToastLong;

    public static void showToast(Context context, int resId) {
        if (resId == 0) return;
        showToast(context, context.getString(resId));
    }

    public static void showToast(Context context, String msg) {
        if (TextUtils.isEmpty(msg)) return;
        if (mToast == null) {
            mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        }
        mToast.setText(msg);
        mToast.show();
    }

    public static void showToastLong(Context context, int resId) {
        if (resId == 0) return;
        showToastLong(context, context.getString(resId));
    }

    public static void showToastLong(Context context, String msg) {
        if (TextUtils.isEmpty(msg)) return;
        if (mToastLong == null) {
            mToastLong = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        }
        mToastLong.setText(msg);
        mToastLong.show();
    }

    /**
     * AlertDialog相关
     */
    private static AlertDialog mAlertDialog;

    public static void showAlert(Context context, int messageId, DialogInterface.OnClickListener onClickListener) {
        showAlert(context, "", context.getString(messageId), onClickListener);
    }

    public static void showAlert(Context context, String message, DialogInterface.OnClickListener onClickListener) {
        showAlert(context, "", message, onClickListener);
    }

    public static void showAlert(Context context, int titleId, int messageId, DialogInterface.OnClickListener onClickListener) {
        if (messageId == 0 || onClickListener == null) return;
        showAlert(context, context.getString(titleId), context.getString(messageId), onClickListener);
    }

    public static void showAlert(Context context, String title, String message, DialogInterface.OnClickListener onClickListener) {
        if (TextUtils.isEmpty(message) || onClickListener == null) return;
        try {
            if (mAlertDialog == null) {
                mAlertDialog = new AlertDialog.Builder(context)
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton(android.R.string.yes, onClickListener).create();
            }
            if (!TextUtils.isEmpty(title)) {
                mAlertDialog.setTitle(title);
            }
            mAlertDialog.setMessage(message);
            mAlertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void clear() {
        mAlertDialog = null;
        mToast = null;
    }

}
