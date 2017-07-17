package project.epam.com.cinemawaddle.main.presenter;


import project.epam.com.cinemawaddle.util.base.BasePresenter;


interface IMainPresenter extends BasePresenter {

    void loadAccountDetails();

    void createSession();

    void connectToTmdb();

    void disconnectFromTmdb();
}
