package com.qianmo.common.loginAndRegister.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.appdemo.R;
import com.qianmo.common.loginAndRegister.connector.RegisterContract;
import com.qianmo.common.loginAndRegister.presenter.RegisterPresenter;
import com.qianmo.common.loginAndRegister.view.TextInputView;

public class RegisterFragment extends Fragment implements RegisterContract.View, View.OnClickListener {

    private LinearLayout mCodeArea;
    private TextInputView mPhone;
    private AppCompatTextView mGetCode;
    private AppCompatEditText mCodeInput;
    private AppCompatButton mNext;
    private AppCompatCheckBox mAgree;

    private LinearLayout mPasswordArea;
    private TextInputView mPassword;
    private TextInputView mConfirmPassword;
    private AppCompatButton mCommit;

    private RegisterContract.Presenter mPresenter;
    private boolean mIsCodeMode = false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        new RegisterPresenter(context, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.common_fragment_register, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mCodeArea = (LinearLayout) view.findViewById(R.id.register_code_area);
        mPhone = (TextInputView) view.findViewById(R.id.register_phone);
        mGetCode = (AppCompatTextView) view.findViewById(R.id.register_get_code);
        mCodeInput = (AppCompatEditText) view.findViewById(R.id.register_input_code);
        mNext = (AppCompatButton) view.findViewById(R.id.register_next);
        mAgree = (AppCompatCheckBox) view.findViewById(R.id.register_code_check_agreement);

        mPasswordArea = (LinearLayout) view.findViewById(R.id.register_pwd_area);
        mPassword = (TextInputView) view.findViewById(R.id.register_password);
        mConfirmPassword = (TextInputView) view.findViewById(R.id.register_confirm_password);
        mCommit = (AppCompatButton) view.findViewById(R.id.register_commit);

        mGetCode.setOnClickListener(this);
        mNext.setOnClickListener(this);
        mCommit.setOnClickListener(this);
    }

    private void updateViewStatus() {
        if (mIsCodeMode) {
            mCodeArea.setVisibility(View.VISIBLE);
            mPasswordArea.setVisibility(View.GONE);
        } else {
            mCodeArea.setVisibility(View.GONE);
            mPasswordArea.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void startGetCode() {

    }

    @Override
    public void completeGetCode() {

    }

    @Override
    public void startVerifyCode() {

    }

    @Override
    public void completeVerifyCode() {

    }

    @Override
    public void startCommit() {

    }

    @Override
    public void completeCommit() {

    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_get_code:
                mPresenter.getCode();
                break;
            case R.id.register_next:
                mPresenter.next();
                break;
            case R.id.register_commit:
                mPresenter.commit();
                break;
            default:
                break;
        }
    }
}
