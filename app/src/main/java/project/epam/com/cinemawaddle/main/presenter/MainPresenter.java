package project.epam.com.cinemawaddle.main.presenter;

import android.content.Intent;

import project.epam.com.cinemawaddle.util.Constants;
import project.epam.com.cinemawaddle.util.authentication.Account;
import project.epam.com.cinemawaddle.main.model.IMainModel;
import project.epam.com.cinemawaddle.main.view.MainActivity;

public class MainPresenter implements IMainPresenter, IMainModel.OnFinishedListener {

    private MainActivity view;
    private IMainModel model;


    public MainPresenter(MainActivity view, IMainModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void createSession() {
        model.createNewSession(this);
    }

    @Override
    public void onAuthenticateClick() {
        model.createNewRequestToken(this);
    }

    @Override
    public void loadAccountDetails() {
        model.fetchAccountDetails(this);
    }

    @Override
    public void onFetchAccountDetailsFinished(Account details) {
        view.showAccountDetails(details);
    }

    @Override
    public void onCreateNewTokenFinished(Intent intent) {
        view.startActivityForResult(intent, Constants.ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void onFailed(String errorMessage) {
        view.showMessage(errorMessage);
    }

    @Override
    public void onSessionCreated() {
        loadAccountDetails();
    }

    @Override
    public void detach() {
        view = null;
    }
}
