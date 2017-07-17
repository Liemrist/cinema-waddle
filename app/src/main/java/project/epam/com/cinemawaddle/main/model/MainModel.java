package project.epam.com.cinemawaddle.main.model;


import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;

import project.epam.com.cinemawaddle.BuildConfig;
import project.epam.com.cinemawaddle.util.Constants;
import project.epam.com.cinemawaddle.util.service.ServiceGenerator;
import project.epam.com.cinemawaddle.util.service.authentication.Account;
import project.epam.com.cinemawaddle.util.service.authentication.IAuthenticationService;
import project.epam.com.cinemawaddle.util.service.authentication.Session;
import project.epam.com.cinemawaddle.util.service.authentication.Token;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainModel implements IMainModel {

    private final Context context;
    private String requestToken;
    private IAuthenticationService service;


    public MainModel(Context context) {
        this.context = context;
        service = ServiceGenerator.createService(IAuthenticationService.class);
    }

    @Override
    public void fetchAccountDetails(OnFinishedListener listener) {
        String sessionId = getSessionIdFromPreferences();

        // Access to the account is not approved.
        if (sessionId == null) return;

        // todo: get data from some storage, if it already exits.

        Call<Account> call = service.getDetails(BuildConfig.TMDB_API_KEY, sessionId);

        call.enqueue(new Callback<Account>() {
            @Override
            public void onResponse(@NonNull Call<Account> call, @NonNull Response<Account> response) {
                Account account = response.body();

                if (account != null) {
                    SharedPreferences.Editor editor = context
                            .getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_PRIVATE)
                            .edit();
                    editor.putInt(Constants.PREF_ACCOUNT_ID, account.getId())
                            .apply();

                    listener.onAccountDetailsFetched(account);
                } else if (response.errorBody() != null) {
                    listener.onFailed(Constants.ERROR_MESSAGE_SESSION_DENIED);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Account> call, @NonNull Throwable t) {
                listener.onFailed(Constants.ERROR_MESSAGE_NETWORK);
            }
        });
    }

    @Override
    public void createRequestToken(OnFinishedListener listener) {
        String sessionId = getSessionIdFromPreferences();

        if (sessionId != null) { // Already authorized.
            listener.onFailed(Constants.MESSAGE_ALREADY_CONNECTED);
        }

        Call<Token> call = service.createToken(BuildConfig.TMDB_API_KEY);

        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(@NonNull Call<Token> call, @NonNull Response<Token> response) {
                Token token = response.body();

                if (token != null) {
                    requestToken = token.getRequestToken();

                    Uri uri = Uri.parse(Constants.URL_AUTH + requestToken);
                    listener.onTokenCreated(uri);
                } else if (response.errorBody() != null) {
                    listener.onFailed(Constants.ERROR_MESSAGE_TOKEN);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Token> call, @NonNull Throwable t) {
                listener.onFailed(Constants.ERROR_MESSAGE_NETWORK);
            }
        });
    }

    @Override
    public void createSession(OnFinishedListener listener) {
        Call<Session> call = service.createSession(BuildConfig.TMDB_API_KEY, requestToken);

        call.enqueue(new Callback<Session>() {
            @Override
            public void onResponse(@NonNull Call<Session> call, @NonNull Response<Session> response) {
                Session session = response.body();

                if (session != null) {
                    SharedPreferences.Editor editor = context
                            .getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_PRIVATE)
                            .edit();
                    editor.putString(Constants.PREF_SESSION_ID, session.getSessionId())
                            .apply();

                    listener.onSessionCreated();
                } else if (response.errorBody() != null) {
                    listener.onFailed(Constants.ERROR_MESSAGE_SESSION);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Session> call, @NonNull Throwable t) {
                listener.onFailed(Constants.ERROR_MESSAGE_NETWORK);
            }
        });
    }

    @Override
    public void disconnectFromTmdb() {
        SharedPreferences.Editor editor = context
                .getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_PRIVATE).edit();

        editor.remove(Constants.PREF_SESSION_ID)
                .remove(Constants.PREF_ACCOUNT_ID)
                .apply();
    }

    private String getSessionIdFromPreferences() {
        SharedPreferences preferences =
                context.getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_PRIVATE);
        return preferences.getString(Constants.PREF_SESSION_ID, null);
    }
}
