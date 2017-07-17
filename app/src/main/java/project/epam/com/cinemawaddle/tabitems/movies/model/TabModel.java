package project.epam.com.cinemawaddle.tabitems.movies.model;


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
import project.epam.com.cinemawaddle.util.movies.IMoviesService;
import project.epam.com.cinemawaddle.util.movies.Movie;
import project.epam.com.cinemawaddle.util.movies.MovieServiceResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TabModel implements ITabModel {

    private IMoviesService client;
    private SharedPreferences preferences;


    public TabModel(Context context) {
        client = ServiceGenerator.createService(IMoviesService.class);
        preferences =
                context.getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public void fetchMovies(int type, String language, int page, String region,
                            OnFinishedListener<MovieServiceResult> listener) {
        Call<MovieServiceResult> call;

        switch (type) {
            case Constants.NOW_PLAYING:
                call = client.getNowPlaying(BuildConfig.TMDB_API_KEY, language, page, region);
                break;
            case Constants.TOP_RATED:
                call = client.getTopRated(BuildConfig.TMDB_API_KEY, language, page, region);
                break;
            case Constants.UPCOMING:
                call = client.getUpcoming(BuildConfig.TMDB_API_KEY, language, page, region);
                break;
            default:
                call = null;
        }

        if (call == null) listener.onFetchingEnd(null);
        else if (!call.isExecuted()) call.enqueue(getCallback(listener));
    }

    @Override
    public void fetchFavorites(OnFinishedListener<MovieServiceResult> listener, int page) {
        String sessionId = preferences.getString(Constants.PREF_SESSION_ID, null);
        int accountId = preferences.getInt(Constants.PREF_ACCOUNT_ID, Constants.DEFAULT_ACCOUNT_ID);

        Call<MovieServiceResult> call = client.getFavourites(accountId,
                BuildConfig.TMDB_API_KEY,
                null,
                sessionId,
                null,
                page);

        if (!call.isExecuted()) call.enqueue(getCallback(listener));
    }

    @Override
    public void fetchWatchlist(OnFinishedListener<MovieServiceResult> listener, int page) {
        String sessionId = preferences.getString(Constants.PREF_SESSION_ID, null);
        int accountId = preferences.getInt(Constants.PREF_ACCOUNT_ID, Constants.DEFAULT_ACCOUNT_ID);

        Call<MovieServiceResult> call = client.getWatchlist(accountId,
                BuildConfig.TMDB_API_KEY,
                null,
                sessionId,
                null,
                page);

        if (!call.isExecuted()) call.enqueue(getCallback(listener));
    }

    @Override
    public void sortMoviesByName(List<Movie> items) {
        Collections.sort(items, (o1, o2) -> o1.getTitle().compareTo(o2.getTitle()));
    }

    @Override
    public void sortMoviesByRating(List<Movie> items) {
        Collections.sort(items, (o1, o2) -> {
            if (o2.getVoteAverage() < o1.getVoteAverage()) return -1;
            else if (o2.getVoteAverage() > o1.getVoteAverage()) return 1;
            else return 0;
        });
    }


    @NonNull
    private Callback<MovieServiceResult> getCallback(final OnFinishedListener<MovieServiceResult> listener) {
        return new Callback<MovieServiceResult>() {
            @Override
            public void onResponse(@NonNull Call<MovieServiceResult> call, @NonNull Response<MovieServiceResult> response) {
                MovieServiceResult movieServiceResult = response.body();
                ResponseBody errorBody = response.errorBody();

                if (movieServiceResult != null) {
                    listener.onFetchingEnd(movieServiceResult);
                } else if (errorBody != null) {
                    ResponseHelper.onResponseError(
                            errorBody,
                            Constants.ERROR_MESSAGE_FETCHING_MOVIES,
                            Constants.TAG_TAB_MODEL,
                            listener);
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieServiceResult> call, @NonNull Throwable t) {
                // the network call was a failure
                listener.onFailed(Constants.ERROR_MESSAGE_NETWORK);
            }
        };
    }
}
