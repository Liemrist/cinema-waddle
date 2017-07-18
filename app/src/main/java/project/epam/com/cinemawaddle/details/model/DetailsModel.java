package project.epam.com.cinemawaddle.details.model;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import project.epam.com.cinemawaddle.BuildConfig;
import project.epam.com.cinemawaddle.util.Constants;
import project.epam.com.cinemawaddle.util.service.ServiceGenerator;
import project.epam.com.cinemawaddle.util.service.movies.IMoviesService;
import project.epam.com.cinemawaddle.util.service.movies.Details;
import project.epam.com.cinemawaddle.util.service.movies.PostResponse;
import project.epam.com.cinemawaddle.util.service.tvshows.ITvShowService;
import project.epam.com.cinemawaddle.util.service.tvshows.TvShowDetails;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailsModel implements IDetailsModel {

    private SharedPreferences preferences;


    public DetailsModel(Context context) {
        preferences = context.getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public void fetchMovieDetails(int id, OnFinishedListener listener) {
        String language = preferences.getString(Constants.PREF_LOCALE, Constants.EN);

        IMoviesService client = ServiceGenerator.createService(IMoviesService.class);
        Call<Details> call = client.getDetails(id, BuildConfig.TMDB_API_KEY, language, null);

        call.enqueue(new Callback<Details>() {
            @Override
            public void onResponse(@NonNull Call<Details> call,
                                   @NonNull Response<Details> response) {
                if (response.body() == null) return;
                listener.onFetchDetailsFinished(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<Details> call, @NonNull Throwable t) {
                listener.onFailed(Constants.ERROR_MESSAGE_NETWORK);
            }
        });
    }

    @Override
    public void fetchTvShowDetails(int id, OnFinishedListener listener) {
        String language = preferences.getString(Constants.PREF_LOCALE, Constants.EN);

        ITvShowService client = ServiceGenerator.createService(ITvShowService.class);
        Call<TvShowDetails> call = client.getDetails(id, BuildConfig.TMDB_API_KEY, language, null);
        call.enqueue(new Callback<TvShowDetails>() {
            @Override
            public void onResponse(@NonNull Call<TvShowDetails> call,
                                   @NonNull Response<TvShowDetails> response) {
                if (response.body() == null) return;
                listener.onFetchDetailsFinished(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TvShowDetails> call, @NonNull Throwable t) {
                listener.onFailed(Constants.ERROR_MESSAGE_NETWORK);
            }
        });
    }

    @Override
    public void setFavourite(String mediaType, int mediaId, boolean isFavorite,
                             OnFinishedListener listener) {
        int accountId = preferences.getInt(Constants.PREF_ACCOUNT_ID, Constants.DEFAULT_ACCOUNT_ID);
        String sessionId = preferences.getString(Constants.PREF_SESSION_ID, null);

        IMoviesService client = ServiceGenerator.createService(IMoviesService.class);
        Call<PostResponse> call = client.setFavorite(
                accountId,
                BuildConfig.TMDB_API_KEY,
                sessionId,
                mediaType,
                mediaId,
                isFavorite);

        call.enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(@NonNull Call<PostResponse> call, @NonNull Response<PostResponse> response) {
                if (response.body() == null) return;
                listener.onSetFavoriteFinished(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<PostResponse> call, @NonNull Throwable t) {

            }
        });
    }
}
