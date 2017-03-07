package com.qianmo.common.loginAndRegister.presenter;

import android.content.Context;

import com.qianmo.common.loginAndRegister.connector.LoginContract;

public class LoginPresenter implements LoginContract.Presenter {

    private Context mContext;
    private LoginContract.View mView;

    public LoginPresenter(Context context, LoginContract.View view) {
        this.mView = view;
        this.mContext = context;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void doLogin() {
        mView.loadStart();
        mView.loadComplete();
    }

    @Override
    public void goRegister() {
        mView.showRegister();
    }

    @Override
    public void goFindPwd() {
        mView.showFindPwd();
    }
}
