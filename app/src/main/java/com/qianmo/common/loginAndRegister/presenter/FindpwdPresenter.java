package com.qianmo.common.loginAndRegister.presenter;

import android.content.Context;

import com.qianmo.common.loginAndRegister.connector.FindpwdContract;

public class FindpwdPresenter implements FindpwdContract.Presenter {

    private Context mContext;
    private FindpwdContract.View mView;

    public FindpwdPresenter(Context context, FindpwdContract.View view) {
        this.mContext = context;
        this.mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void getCode() {

    }

    @Override
    public void next() {

    }

    @Override
    public void commit() {

    }

    @Override
    public void start() {

    }
}
