package com.qianmo.common.loginAndRegister.connector;

/**
 * 登录
 */

public interface LoginContract {

    interface View extends BaseView<Presenter> {

        void loadStart();

        void loadComplete();

        void showRegister();

        void showFindPwd();
    }

    interface Presenter extends BasePresenter {
        void doLogin();

        void goRegister();

        void goFindPwd();
    }

}
