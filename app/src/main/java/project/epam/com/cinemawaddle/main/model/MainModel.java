package project.epam.com.cinemawaddle.main.model;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;

import okhttp3.ResponseBody;
import project.epam.com.cinemawaddle.BuildConfig;
import project.epam.com.cinemawaddle.util.Constants;
import project.epam.com.cinemawaddle.util.ResponseHelper;
import project.epam.com.cinemawaddle.util.ServiceGenerator;
import project.epam.com.cinemawaddle.util.authentication.Account;
import project.epam.com.cinemawaddle.util.authentication.IAuthenticationService;
import project.epam.com.cinemawaddle.util.authentication.Session;
import project.epam.com.cinemawaddle.util.authentication.Token;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainModel implements IMainModel {
    private Context context;
    private IAuthenticationService service;
    private String requestToken;


    public MainModel(Context context) {
        this.context = context;
        service = ServiceGenerator.createService(IAuthenticationService.class);
    }

    @Override
    public void fetchAccountDetails(OnFinishedListener listener) {
        String sessionId = getSessionIdFromPreferences();

        // fixme: handle null sessionId case.
        // fixme: get data from preferences, if it already exits.
        Call<Account> call = service.getDetails(BuildConfig.TMDB_API_KEY, sessionId);

        call.enqueue(new Callback<Account>() {
            @Override
            public void onResponse(@NonNull Call<Account> call, @NonNull Response<Account> response) {
                Account account = response.body();
                ResponseBody errorBody = response.errorBody();

                if (account != null) {
                    SharedPreferences.Editor editor = context
                            .getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_PRIVATE)
                            .edit();
                    editor.putInt(Constants.PREF_ACCOUNT_ID, account.getId())
                            .apply();

                    listener.onFetchAccountDetailsFinished(account);
                } else if (errorBody != null) {
                    ResponseHelper.onResponseError(errorBody,
                            Constants.ERROR_MESSAGE_FETCHING_ACCOUNT_DETAILS, Constants.TAG_MAIN_MODEL,
                            listener);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Account> call, @NonNull Throwable t) {
                listener.onFailed(Constants.ERROR_MESSAGE_NETWORK);
            }
        });
    }

    @Override
    public void createNewRequestToken(OnFinishedListener listener) {
        String sessionId = getSessionIdFromPreferences();
        // fixme: handle already authorized case.
        if (sessionId != null) return;

        Call<Token> call = service.createToken(BuildConfig.TMDB_API_KEY);

        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(@NonNull Call<Token> call, @NonNull Response<Token> response) {
                Token token = response.body();
                ResponseBody errorBody = response.errorBody();

                if (token != null) {
                    requestToken = token.getRequestToken();

                    Uri uri = Uri.parse(Constants.URL_AUTH + token.getRequestToken());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);

                    listener.onCreateNewTokenFinished(intent);
                } else if (errorBody != null) {
                    ResponseHelper.onResponseError(errorBody,
                            Constants.ERROR_MESSAGE_TOKEN, Constants.TAG_MAIN_MODEL,
                            listener);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Token> call, @NonNull Throwable t) {
                listener.onFailed(Constants.ERROR_MESSAGE_NETWORK);
            }
        });
    }

    @Override
    public void createNewSession(OnFinishedListener listener) {
        Call<Session> call = service.createSession(BuildConfig.TMDB_API_KEY, requestToken);

        call.enqueue(new Callback<Session>() {
            @Override
            public void onResponse(@NonNull Call<Session> call, @NonNull Response<Session> response) {
                Session session = response.body();
                ResponseBody errorBody = response.errorBody();

                if (session != null) {
                    SharedPreferences.Editor editor = context
                            .getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_PRIVATE)
                            .edit();
                    editor.putString(Constants.PREF_SESSION_ID, session.getSessionId())
                            .apply();

                    listener.onSessionCreated();
                } else if (errorBody != null) {
                    ResponseHelper.onResponseError(errorBody,
                            Constants.ERROR_MESSAGE_SESSION, Constants.TAG_MAIN_MODEL,
                            listener);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Session> call, @NonNull Throwable t) {
                listener.onFailed(Constants.ERROR_MESSAGE_NETWORK);
            }
        });
    }

    private String getSessionIdFromPreferences() {
        SharedPreferences preferences =
                context.getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_PRIVATE);
        return preferences.getString(Constants.PREF_SESSION_ID, null);
    }
}
