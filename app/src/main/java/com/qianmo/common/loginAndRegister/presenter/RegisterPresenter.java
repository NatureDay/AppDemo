package com.qianmo.common.loginAndRegister.presenter;

import android.content.Context;

import com.qianmo.common.loginAndRegister.connector.RegisterContract;

public class RegisterPresenter implements RegisterContract.Presenter {

    private Context mContext;
    private RegisterContract.View mView;

    public RegisterPresenter(Context context, RegisterContract.View view) {
        this.mContext = context;
        this.mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void getCode() {
        mView.startGetCode();
        mView.completeGetCode();
    }

    @Override
    public void next() {
        mView.startVerifyCode();
        mView.completeVerifyCode();
    }

    @Override
    public void commit() {
        mView.startCommit();
        mView.completeCommit();
    }
}
