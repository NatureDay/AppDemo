package com.qianmo.common.basis;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.appdemo.R;
import com.qianmo.android.library.base.BaseActivity;

/**
 * 抽象类，用于封装Activity初始化一些基本操作，减少代码量
 */
public abstract class AppCommonActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initialization();
    }

    protected void initialization() {
        initAppHead();
        initAppView();
    }

    protected void initAppHead() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getAppBarTitle());
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    /**
     * 设定布局layout的id
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 设定ActionBar中标题
     *
     * @return
     */
    protected abstract String getAppBarTitle();

    /**
     * 抽象方法，初始化view
     */
    protected abstract void initAppView();

}
