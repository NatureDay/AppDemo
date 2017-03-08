package com.qianmo.android.library;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Log;

import com.qianmo.android.library.utils.JniUtil;

public class BaseApplication extends Application {

    static {
        System.loadLibrary("common-lib");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //JniUtil.verifySignature(this);
        verifySignature();
    }

    private void verifySignature() {
        PackageManager pm = this.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            Signature[] signatures = pi.signatures;
            for (Signature signature : signatures) {
                Log.e("fff", "--------signature====" + signature.toCharsString());
                Log.e("fff", "--------signature---hash====" + signature.toCharsString().hashCode());
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

}
