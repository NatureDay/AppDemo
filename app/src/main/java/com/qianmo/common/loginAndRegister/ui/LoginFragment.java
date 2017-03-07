package com.qianmo.common.loginAndRegister.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appdemo.R;
import com.qianmo.android.library.base.BaseFragment;
import com.qianmo.common.loginAndRegister.connector.LoginContract;
import com.qianmo.common.loginAndRegister.presenter.LoginPresenter;
import com.qianmo.common.loginAndRegister.view.TextInputView;

/**
 * 登录模块
 */
public class LoginFragment extends BaseFragment implements LoginContract.View, View.OnClickListener {

    private LoginContract.Presenter mPresenter;

    private TextInputView mAccount;
    private TextInputView mPassword;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        new LoginPresenter(context, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.common_fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mAccount = (TextInputView) view.findViewById(R.id.login_account);
        mPassword = (TextInputView) view.findViewById(R.id.login_password);

        view.findViewById(R.id.login_commit).setOnClickListener(this);
        view.findViewById(R.id.login_register).setOnClickListener(this);
        view.findViewById(R.id.login_findpwd).setOnClickListener(this);
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void loadStart() {

    }

    @Override
    public void loadComplete() {

    }

    @Override
    public void showRegister() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.replace(R.id.content_view, new RegisterFragment());
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void showFindPwd() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.replace(R.id.content_view, new FindpwdFragment());
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_commit:
                mPresenter.doLogin();
                break;
            case R.id.login_register:
                mPresenter.goRegister();
                break;
            case R.id.login_findpwd:
                mPresenter.goFindPwd();
                break;
        }
    }
}
