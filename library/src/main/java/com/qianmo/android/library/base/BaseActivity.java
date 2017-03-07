package com.qianmo.android.library.base;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import com.qianmo.android.library.R;
import com.qianmo.android.library.utils.ExitUtil;

public class BaseActivity extends AppCompatActivity {

    protected Context mContext;
    protected AlertDialog mLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        ExitUtil.getInstance().addInstance(this);

        Resources res = getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
    }

    @Override
    protected void onPause() {
        closeLoadingDialog();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        ExitUtil.getInstance().finishActivity(this);
        super.onDestroy();
    }

    /**
     * Android6.0需要检查权限
     *
     * @return
     */
    protected boolean hasPermissionsGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 显示加载对话框
     */
    protected void showLoadingDialog() {
        showLoadingDialog("");
    }

    protected void showLoadingDialog(int resId) {
        showLoadingDialog(mContext.getString(resId));
    }

    protected void showLoadingDialog(String msg) {
        try {
            if (!isFinishing()) {
                if (mLoadingDialog == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.loadingDialog);
                    View view = LayoutInflater.from(mContext).inflate(R.layout.loading_dialog, null);
                    AppCompatTextView textView = (AppCompatTextView) view.findViewById(R.id.loading_dialog_msg);
                    if (TextUtils.isEmpty(msg)) {
                        textView.setVisibility(View.GONE);
                    } else {
                        textView.setVisibility(View.VISIBLE);
                        textView.setText(msg);
                    }
                    builder.setView(view);
                    mLoadingDialog = builder.create();
                }
                mLoadingDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 隐藏加载对话框
     */
    public void closeLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            closeLoadingDialog();
        }
        return super.onKeyUp(keyCode, event);
    }
}
