package project.epam.com.cinemawaddle.main.presenter;


import android.net.Uri;

import project.epam.com.cinemawaddle.main.model.IMainModel;
import project.epam.com.cinemawaddle.main.view.IMainView;
import project.epam.com.cinemawaddle.util.service.authentication.Account;


public class MainPresenter implements IMainPresenter, IMainModel.OnFinishedListener {

    private IMainView view;
    private IMainModel model;


    public MainPresenter(IMainView view, IMainModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void createSession() {
        model.createSession(this);
    }

    @Override
    public void connectToTmdb() {
        model.createRequestToken(this);
    }

    @Override
    public void disconnectFromTmdb() {
        model.disconnectFromTmdb();
        view.hideAccountDetails();
    }

    @Override
    public void loadAccountDetails() {
        model.fetchAccountDetails(this);
    }

    @Override
    public void onAccountDetailsFetched(Account details) {
        view.showAccountDetails(details);
    }

    @Override
    public void onTokenCreated(Uri uri) {
        view.launchBrowser(uri);
    }

    @Override
    public void onSessionCreated() {
        loadAccountDetails();
    }

    @Override
    public void onFailed(String errorMessage) {
        view.showMessage(errorMessage);
    }

    @Override
    public void onFailure(String errorMessage) {
        view.showMessage(errorMessage);
    }

    @Override
    public void detach() {
        view = null;
        model = null;
    }
}
