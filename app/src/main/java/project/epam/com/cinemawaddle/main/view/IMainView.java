package project.epam.com.cinemawaddle.main.view;

import project.epam.com.cinemawaddle.util.base.BaseView;
import project.epam.com.cinemawaddle.util.authentication.Account;

interface IMainView extends BaseView {
    void showAccountDetails(Account account);
}
