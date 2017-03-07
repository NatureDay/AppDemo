package com.qianmo.common.loginAndRegister.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.example.appdemo.R;
import com.qianmo.android.library.base.BaseActivity;

public class LRActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity_lr);

        findViewById(R.id.content_view);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.replace(R.id.content_view, new LoginFragment());
        ft.commit();
    }
}
