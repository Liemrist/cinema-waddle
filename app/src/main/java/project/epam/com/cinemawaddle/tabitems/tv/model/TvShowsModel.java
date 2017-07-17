package project.epam.com.cinemawaddle.tabitems.tv.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.List;

import okhttp3.ResponseBody;
import project.epam.com.cinemawaddle.BuildConfig;
import project.epam.com.cinemawaddle.util.Constants;
import project.epam.com.cinemawaddle.util.ResponseHelper;
import project.epam.com.cinemawaddle.util.ServiceGenerator;
import project.epam.com.cinemawaddle.util.tvshows.ITvShowService;
import project.epam.com.cinemawaddle.util.tvshows.TvListResult;
import project.epam.com.cinemawaddle.util.tvshows.TvServiceResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowsModel implements ITvShowModel {

    private ITvShowService client;
    private SharedPreferences preferences;


    public TvShowsModel(Context context) {
        client = ServiceGenerator.createService(ITvShowService.class);
        preferences =
                context.getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public void fetchTvShows(int type, String language, int page,
                             OnFinishedListener<TvServiceResult> listener) {
        Call<TvServiceResult> call;

        switch (type) {
            case Constants.NOW_PLAYING:
                call = client.getOnTheAir(BuildConfig.TMDB_API_KEY, language, page);
                break;
            case Constants.TOP_RATED:
                call = client.getTopRated(BuildConfig.TMDB_API_KEY, language, page);
                break;
            default:
                call = null;
        }

        if (call == null) listener.onFetchingEnd(null);
        else if (!call.isExecuted()) call.enqueue(getCallback(listener));
    }

    @Override
    public void fetchFavorites(OnFinishedListener<TvServiceResult> listener) {
        String sessionId = preferences.getString(Constants.PREF_SESSION_ID, null);
        int accountId = preferences.getInt(Constants.PREF_ACCOUNT_ID, Constants.DEFAULT_ACCOUNT_ID);

        Call<TvServiceResult> call = client.getFavourites(accountId,
                BuildConfig.TMDB_API_KEY,
                null,
                sessionId,
                null,
                Constants.DEFAULT_PAGE);

        if (!call.isExecuted()) call.enqueue(getCallback(listener));
    }

    @Override
    public void fetchWatchlist(OnFinishedListener<TvServiceResult> listener) {
        String sessionId = preferences.getString(Constants.PREF_SESSION_ID, null);
        int accountId = preferences.getInt(Constants.PREF_ACCOUNT_ID, Constants.DEFAULT_ACCOUNT_ID);

        Call<TvServiceResult> call = client.getWatchlist(accountId,
                BuildConfig.TMDB_API_KEY,
                null,
                sessionId,
                null,
                Constants.DEFAULT_PAGE);

        if (!call.isExecuted()) call.enqueue(getCallback(listener));
    }

    @Override
    public void sortMoviesByName(List<TvListResult> items) {
        Collections.sort(items, (o1, o2) -> o1.getName().compareTo(o2.getName()));
    }

    @Override
    public void sortMoviesByRating(List<TvListResult> items) {
        Collections.sort(items, (o1, o2) -> {
            if (o2.getVoteAverage() < o1.getVoteAverage()) return -1;
            else if (o2.getVoteAverage() > o1.getVoteAverage()) return 1;
            else return 0;
        });
    }


    @NonNull
    private Callback<TvServiceResult> getCallback(final OnFinishedListener<TvServiceResult> listener) {
        return new Callback<TvServiceResult>() {
            @Override
            public void onResponse(@NonNull Call<TvServiceResult> call, @NonNull Response<TvServiceResult> response) {
                TvServiceResult movies = response.body();
                ResponseBody errorBody = response.errorBody();

                if (movies != null) {
                    listener.onFetchingEnd(movies);
                } else if (errorBody != null) {
                    ResponseHelper.onResponseError(
                            errorBody,
                            Constants.ERROR_MESSAGE_FETCHING_MOVIES,
                            Constants.TAG_TAB_MODEL,
                            listener);
                }
            }

            @Override
            public void onFailure(@NonNull Call<TvServiceResult> call, @NonNull Throwable t) {
                // the network call was a failure
                listener.onFailed(Constants.ERROR_MESSAGE_NETWORK);
            }
        };
    }
}
