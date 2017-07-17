package project.epam.com.cinemawaddle.main.model;


import android.net.Uri;

import project.epam.com.cinemawaddle.util.service.authentication.Account;
import project.epam.com.cinemawaddle.util.base.ModelBaseListener;


public interface IMainModel {

    void fetchAccountDetails(OnFinishedListener listener);

    void createRequestToken(OnFinishedListener listener);

    void createSession(OnFinishedListener listener);

    void disconnectFromTmdb();

    interface OnFinishedListener extends ModelBaseListener {

        void onAccountDetailsFetched(Account details);

        void onTokenCreated(Uri uri);

        void onSessionCreated();
    }
}
