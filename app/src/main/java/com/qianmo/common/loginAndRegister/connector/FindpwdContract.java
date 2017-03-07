package com.qianmo.common.loginAndRegister.connector;

/**
 * 找回密码
 */

public interface FindpwdContract {

    interface View extends BaseView<Presenter> {

        void startGetCode();

        void completeGetCode();

        void startVerifyCode();

        void completeVerifyCode();

        void startCommit();

        void completeCommit();
    }

    interface Presenter extends BasePresenter {
        void getCode();

        void next();

        void commit();
    }
}
