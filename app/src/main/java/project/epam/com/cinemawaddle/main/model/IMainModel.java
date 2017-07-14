package project.epam.com.cinemawaddle.main.model;

import android.content.Intent;

import project.epam.com.cinemawaddle.util.base.ModelBaseListener;
import project.epam.com.cinemawaddle.util.authentication.Account;

public interface IMainModel {

    void fetchAccountDetails(OnFinishedListener listener);

    void createNewRequestToken(OnFinishedListener listener);

    void createNewSession(OnFinishedListener listener);

    interface OnFinishedListener extends ModelBaseListener {

        void onFetchAccountDetailsFinished(Account details);

        void onCreateNewTokenFinished(Intent intent);

        void onSessionCreated();
    }
}
