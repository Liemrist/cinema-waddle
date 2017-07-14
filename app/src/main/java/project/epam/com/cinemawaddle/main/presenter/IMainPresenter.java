package project.epam.com.cinemawaddle.main.presenter;

import project.epam.com.cinemawaddle.util.base.BasePresenter;

interface IMainPresenter extends BasePresenter {
    void createSession();

    void onAuthenticateClick();

    void loadAccountDetails();
}
