package project.epam.com.cinemawaddle.main.view;

import android.net.Uri;

import project.epam.com.cinemawaddle.util.authentication.Account;

public interface IMainView {

    void showAccountDetails(Account account);

    void hideAccountDetails();

    void showMessage(String message);

    void launchBrowser(Uri uri);
}
